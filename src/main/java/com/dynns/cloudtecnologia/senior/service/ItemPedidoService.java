package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.rest.dto.itemPedido.ItemPedidoNewDTO;

import java.util.List;

public interface ItemPedidoService {
    void save(Pedido pedidoSalvo, List<ItemPedidoNewDTO> itensPedido);
}
