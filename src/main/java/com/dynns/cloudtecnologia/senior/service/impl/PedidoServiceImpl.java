package com.dynns.cloudtecnologia.senior.service.impl;

import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.model.enums.SituacaoPedidoEnum;
import com.dynns.cloudtecnologia.senior.model.enums.TipoProdutoServicoEnum;
import com.dynns.cloudtecnologia.senior.model.repository.PedidoRepository;
import com.dynns.cloudtecnologia.senior.rest.dto.itemPedido.ItemPedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.itemPedido.SomaTotalBrutoDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoNewDTO;
import com.dynns.cloudtecnologia.senior.service.PedidoService;
import com.dynns.cloudtecnologia.senior.utils.SeniorErpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoServicoServiceImpl produtoServicoService;
    @Autowired
    private ItemPedidoServiceImpl itemPedidoService;

    @Override
    @Transactional
    public Pedido save(PedidoNewDTO pedidoDto) {
        SomaTotalBrutoDTO somaTotais = retornarTotalBrutoItens(pedidoDto);

//        BigDecimal percentualDescontoDecimal = pedidoDto.getPercentualDesconto().divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
//        BigDecimal totalDescontosProdutos = somaTotais.getTotalBrutoProdutos().multiply(percentualDescontoDecimal);
//        BigDecimal totalLiquido = somaTotais.getTotalBrutoGeral().subtract(totalDescontosProdutos);

        Pedido pedidoSalvo = Pedido.builder()
                .situacao(SituacaoPedidoEnum.ABERTO)
                .descricao(pedidoDto.getDescricao())
                .totalBruto(SeniorErpUtil.ajustarDuasCasasDecimais(somaTotais.getTotalBrutoGeral()))
                .totalLiquido(SeniorErpUtil.ajustarDuasCasasDecimais(somaTotais.getTotalBrutoGeral()))
                .build();

        pedidoRepository.save(pedidoSalvo);
        itemPedidoService.save(pedidoSalvo, pedidoDto.getItensPedido());

        return pedidoSalvo;
    }

    @Override
    public Page<Pedido> show(int page, int size, PedidoFilterDTO filter) {
        UUID idSanitizado;
        if (Objects.isNull(filter.getId())) {
            idSanitizado = null;
        } else {
            idSanitizado = SeniorErpUtil.retornarUUIDSanitizado(filter.getId().trim());
        }

        SituacaoPedidoEnum situacao = null;
        if (Objects.nonNull(filter.getSituacao())) {
            situacao = SituacaoPedidoEnum.fromString(filter.getSituacao().trim());
        }

        Pedido pedidoFilter = Pedido.builder()
                .id(idSanitizado)
                .situacao(situacao)
                .descricao(filter.getDescricao())
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Pedido> example = Example.of(pedidoFilter, matcher);
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.Direction.ASC, "dataCriacao");

        return pedidoRepository.findAll(example, pageRequest);
    }


    private SomaTotalBrutoDTO retornarTotalBrutoItens(PedidoNewDTO dto) {
        BigDecimal somaTotalBrutoGeral = BigDecimal.ZERO;
        BigDecimal somaTotalBrutoProdutos = BigDecimal.ZERO;
        BigDecimal somaTotalBrutoServicos = BigDecimal.ZERO;
        for (ItemPedidoNewDTO item : dto.getItensPedido()) {
            UUID idSanitizado = SeniorErpUtil.retornarUUIDSanitizado(item.getIdProdutoServico().trim());
            ProdutoServico produtoServico = produtoServicoService.findById(idSanitizado)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ProdutoServico com Id UUID não localizado: " + idSanitizado));

            BigDecimal precoProduto = produtoServico.getPreco();
            BigDecimal quantidade = BigDecimal.valueOf(item.getQtde());
            BigDecimal totalBrutoItem = precoProduto.multiply(quantidade);
            somaTotalBrutoGeral = somaTotalBrutoGeral.add(totalBrutoItem);

            if (produtoServico.getTipo().equals(TipoProdutoServicoEnum.PRODUTO)) {
                somaTotalBrutoProdutos = somaTotalBrutoProdutos.add(totalBrutoItem);
            } else if (produtoServico.getTipo().equals(TipoProdutoServicoEnum.SERVICO)) {
                somaTotalBrutoServicos = somaTotalBrutoServicos.add(totalBrutoItem);
            }
        }
        return SomaTotalBrutoDTO.builder()
                .totalBrutoGeral(somaTotalBrutoGeral)
                .totalBrutoProdutos(somaTotalBrutoProdutos)
                .totalBrutoServicos(somaTotalBrutoServicos)
                .build();
    }


}
