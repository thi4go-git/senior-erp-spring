package com.dynns.cloudtecnologia.senior.service.impl;

import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.model.enums.SituacaoPedidoEnum;
import com.dynns.cloudtecnologia.senior.model.enums.TipoProdutoServicoEnum;
import com.dynns.cloudtecnologia.senior.model.repository.PedidoRepository;
import com.dynns.cloudtecnologia.senior.rest.dto.itemPedido.ItemPedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.itemPedido.SomaTotalBrutoDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoNewDTO;
import com.dynns.cloudtecnologia.senior.service.PedidoService;
import com.dynns.cloudtecnologia.senior.utils.SeniorErpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
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

        BigDecimal percentualDescontoDecimal = pedidoDto.getPercentualDesconto().divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
        BigDecimal totalDescontosProdutos = somaTotais.getTotalBrutoProdutos().multiply(percentualDescontoDecimal);
        BigDecimal totalLiquido = somaTotais.getTotalBrutoGeral().subtract(totalDescontosProdutos);

        Pedido pedidoSalvo = Pedido.builder()
                .situacao(SituacaoPedidoEnum.ABERTO)
                .descricao(pedidoDto.getDescricao())
                .percentualDesconto(SeniorErpUtil.ajustarDuasCasasDecimais(pedidoDto.getPercentualDesconto()))
                .totalBruto(SeniorErpUtil.ajustarDuasCasasDecimais(somaTotais.getTotalBrutoGeral()))
                .totalDescontos(SeniorErpUtil.ajustarDuasCasasDecimais(totalDescontosProdutos))
                .totalLiquido(SeniorErpUtil.ajustarDuasCasasDecimais(totalLiquido))
                .build();

        pedidoRepository.save(pedidoSalvo);
        itemPedidoService.save(pedidoSalvo, pedidoDto.getItensPedido());

        return pedidoSalvo;
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
