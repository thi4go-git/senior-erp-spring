package com.dynns.cloudtecnologia.senior.rest.mapper;

import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.rest.dto.itemPedido.ItemPedidoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemPedidoMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ItemPedidoResponseDTO itemPedidoToItemPedidoResponseDTO(ItemPedido itemPedido) {
        return modelMapper.map(itemPedido, ItemPedidoResponseDTO.class);
    }

    public List<ItemPedidoResponseDTO> listItemPedidoToListItemPedidoResponseDTO(List<ItemPedido> itemPedidoList) {
        return itemPedidoList.stream()
                .map(this::itemPedidoToItemPedidoResponseDTO)
                .collect(Collectors.toList());
    }
}
