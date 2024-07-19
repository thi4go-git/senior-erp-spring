package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoFilterDTO;
import org.springframework.data.domain.Page;

public interface PedidoService {
    Pedido save(PedidoNewDTO dto);

    Page<Pedido> show(int page, int size, PedidoFilterDTO filter);
}
