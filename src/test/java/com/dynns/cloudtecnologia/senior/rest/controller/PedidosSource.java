package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.rest.dto.item_pedido.ItemPedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoNewDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PedidosSource {
    private PedidosSource() {
    }

    public static PedidoNewDTO getPedidoDadosIncorretos() {
        List<ItemPedidoNewDTO> itensPedido = new ArrayList<>();
        return PedidoNewDTO.builder()
                .descricao("")
                .itensPedido(itensPedido)
                .build();
    }

    public static PedidoNewDTO getPedidoDadosCorretdos(String idServicoSalvo, String idProdutoSalvo, int qtde, String descricaoPedido) {
        List<ItemPedidoNewDTO> itensPedido = new ArrayList<>();
        itensPedido.add(ItemPedidoNewDTO.builder().idProdutoServico(idServicoSalvo)
                .qtde(qtde).build());
        itensPedido.add(ItemPedidoNewDTO.builder().idProdutoServico(idProdutoSalvo)
                .qtde(qtde).build());
        return PedidoNewDTO.builder()
                .descricao(descricaoPedido)
                .itensPedido(itensPedido)
                .build();
    }

    public static ProdutoServicoNewDTO getProdutoValido() {
        return ProdutoServicoNewDTO
                .builder()
                .tipo("PRODUTO")
                .descricao("Corda 9mm - 2")
                .preco(new BigDecimal("12.50"))
                .build();
    }

    public static ProdutoServicoNewDTO getServicoValido() {
        return ProdutoServicoNewDTO
                .builder()
                .tipo("SERVICO")
                .descricao("Serviço cloud AWS - 2")
                .preco(new BigDecimal("1999.12"))
                .build();
    }
}
