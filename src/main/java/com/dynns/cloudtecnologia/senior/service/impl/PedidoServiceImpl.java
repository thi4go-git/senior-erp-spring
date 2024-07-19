package com.dynns.cloudtecnologia.senior.service.impl;

import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.model.enums.SituacaoPedidoEnum;
import com.dynns.cloudtecnologia.senior.model.repository.PedidoRepository;
import com.dynns.cloudtecnologia.senior.rest.dto.item_pedido.ItemPedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.DescontoDTO;
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
import java.math.MathContext;
import java.util.List;
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
    private static final String PEDIDO_NOTFOUND = "Pedido com Id UUID não localizado: ";

    @Override
    @Transactional
    public Pedido save(PedidoNewDTO pedidoDto) {
        BigDecimal totalBruto = retornarTotalBrutoItens(pedidoDto);
        Pedido pedidoSalvo = Pedido.builder()
                .situacao(SituacaoPedidoEnum.ABERTO)
                .descricao(pedidoDto.getDescricao())
                .totalBruto(SeniorErpUtil.ajustarDuasCasasDecimais(totalBruto))
                .totalLiquido(SeniorErpUtil.ajustarDuasCasasDecimais(totalBruto))
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

    @Override
    public Pedido fecharPedido(String id) {
        return pedidoRepository.findById(SeniorErpUtil.retornarUUIDSanitizado(id)).map(pedido -> {
            if (pedido.getSituacao().equals(SituacaoPedidoEnum.FECHADO)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O pedido já está fechado.");
            }
            pedido.setSituacao(SituacaoPedidoEnum.FECHADO);
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PEDIDO_NOTFOUND + id));
    }

    private BigDecimal retornarTotalBrutoItens(PedidoNewDTO dto) {
        BigDecimal somaTotalBrutoGeral = BigDecimal.ZERO;
        for (ItemPedidoNewDTO item : dto.getItensPedido()) {
            UUID idSanitizado = SeniorErpUtil.retornarUUIDSanitizado(item.getIdProdutoServico().trim());
            ProdutoServico produtoServico = produtoServicoService.findById(idSanitizado)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ProdutoServico com Id UUID não localizado: " + idSanitizado));

            BigDecimal precoProduto = produtoServico.getPreco();
            BigDecimal quantidade = BigDecimal.valueOf(item.getQtde());
            BigDecimal totalBrutoItem = precoProduto.multiply(quantidade);

            somaTotalBrutoGeral = somaTotalBrutoGeral.add(totalBrutoItem);
        }
        return somaTotalBrutoGeral;
    }

    @Override
    public Pedido showById(String id) {
        return pedidoRepository.findById(SeniorErpUtil.retornarUUIDSanitizado(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PEDIDO_NOTFOUND + id));
    }

    @Override
    public List<ItemPedido> getItensPedido(String idPedido) {
        Pedido pedido = pedidoRepository.findById(SeniorErpUtil.retornarUUIDSanitizado(idPedido))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PEDIDO_NOTFOUND + idPedido));
        return itemPedidoService.findByPedido(pedido);
    }

    @Override
    public Pedido aplicarDesconto(String idPedido, DescontoDTO descontoDto) {
        return pedidoRepository.findById(SeniorErpUtil.retornarUUIDSanitizado(idPedido)).map(pedido -> {
            if (pedido.getSituacao().equals(SituacaoPedidoEnum.FECHADO)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível aplicar desconto. Causa: o pedido já está fechado.");
            }
            if (pedido.getPercentualDesconto() != null && pedido.getPercentualDesconto().compareTo(BigDecimal.ZERO) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível aplicar desconto. Causa: Desconto já aplicado de " + pedido.getPercentualDesconto() + "%");
            }

            BigDecimal percentualDesconto = descontoDto.getPercentualDesconto().divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
            BigDecimal valorBrutoItensProdutos = itemPedidoService.getSomaValorBrutoItensProdutos(pedido.getId());
            BigDecimal totalDescontos = valorBrutoItensProdutos.multiply(percentualDesconto);
            BigDecimal totalLiquido = pedido.getTotalBruto().subtract(totalDescontos);

            pedido.setPercentualDesconto(SeniorErpUtil.ajustarDuasCasasDecimais(descontoDto.getPercentualDesconto()));
            pedido.setTotalDescontos(SeniorErpUtil.ajustarDuasCasasDecimais(totalDescontos));
            pedido.setTotalLiquido(SeniorErpUtil.ajustarDuasCasasDecimais(totalLiquido));

            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PEDIDO_NOTFOUND + idPedido));
    }
}
