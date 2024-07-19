package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.item_pedido.ItemPedidoNewDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ItemPedidoService {
    void save(Pedido pedidoSalvo, List<ItemPedidoNewDTO> itensPedido);

    List<ItemPedido> findByPedido(Pedido pedido);

    BigDecimal getSomaValorBrutoItensProdutos(UUID idPedido);

    List<ItemPedido> findByProdutoServico(ProdutoServico prodServ);
}
