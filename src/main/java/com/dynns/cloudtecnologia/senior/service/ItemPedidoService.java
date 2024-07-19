package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.rest.dto.itemPedido.ItemPedidoNewDTO;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ItemPedidoService {
    void save(Pedido pedidoSalvo, List<ItemPedidoNewDTO> itensPedido);

    List<ItemPedido> findByPedido(Pedido pedido);

    BigDecimal getSomaValorBrutoItensProdutos(UUID idPedido);
}
