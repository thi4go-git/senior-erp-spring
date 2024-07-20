package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.DescontoDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PedidoService {
    Pedido save(PedidoNewDTO dto);

    Page<Pedido> show(int page, int size, PedidoFilterDTO filter);

    Pedido fecharPedido(String id);

    Pedido showById(String id);

    List<ItemPedido> getItensPedido(String idPedido);

    Pedido aplicarDesconto(String idPedido, DescontoDTO descontoDto);

    Pedido update(String id, PedidoUpdateDTO dto);

    void delete(String id);
}
