package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoNewDTO;

public interface PedidoService {
    Pedido save(PedidoNewDTO dto);
}
