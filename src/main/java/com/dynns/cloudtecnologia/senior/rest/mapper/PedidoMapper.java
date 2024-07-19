package com.dynns.cloudtecnologia.senior.rest.mapper;

import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoResponseDTO pedidoToPedidoResponseDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    public Page<PedidoResponseDTO> pagePedidoToPagePedidoResponseDTO(Page<Pedido> pagePedido) {
        List<PedidoResponseDTO> pedidoList = pagePedido.stream()
                .map(pedido -> modelMapper.map(pedido, PedidoResponseDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(pedidoList, pagePedido.getPageable(), pagePedido.getTotalElements());
    }
}
