package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.rest.dto.item_pedido.ItemPedidoResponseDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.*;
import com.dynns.cloudtecnologia.senior.rest.mapper.ItemPedidoMapper;
import com.dynns.cloudtecnologia.senior.rest.mapper.PedidoMapper;
import com.dynns.cloudtecnologia.senior.service.impl.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoServiceImpl pedidoService;
    @Autowired
    private PedidoMapper pedidoMapper;
    @Autowired
    private ItemPedidoMapper itemPedidoMapper;
    private static final String ID_OBRIGATORIO = "O Campo id é Obrigatório!";

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> save(
            @RequestBody @Valid PedidoNewDTO dto
    ) {
        Pedido pedidoSalvo = pedidoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoMapper.pedidoToPedidoResponseDTO(pedidoSalvo));
    }

    @PostMapping("/show")
    public ResponseEntity<Page<PedidoResponseDTO>> show(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestBody PedidoFilterDTO filtro
    ) {
        Page<Pedido> pageProdutoServico = pedidoService.show(page, size, filtro);
        return ResponseEntity.ok().body(pedidoMapper.pagePedidoToPagePedidoResponseDTO(pageProdutoServico));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> fecharPedido(
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id
    ) {
        Pedido pedidoFechado = pedidoService.fecharPedido(id);
        return ResponseEntity.ok().body(pedidoMapper.pedidoToPedidoResponseDTO(pedidoFechado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> showById(
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id
    ) {
        Pedido pedido = pedidoService.showById(id);
        return ResponseEntity.ok().body(pedidoMapper.pedidoToPedidoResponseDTO(pedido));
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemPedidoResponseDTO>> showItens(
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id
    ) {
        List<ItemPedido> itens = pedidoService.getItensPedido(id);
        return ResponseEntity.ok().body(itemPedidoMapper.listItemPedidoToListItemPedidoResponseDTO(itens));
    }

    @PostMapping("/{id}/desconto")
    public ResponseEntity<PedidoResponseDTO> aplicarDesconto(
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id,
            @RequestBody @Valid DescontoDTO descontoDto
    ) {
        Pedido pedido = pedidoService.aplicarDesconto(id, descontoDto);
        return ResponseEntity.ok().body(pedidoMapper.pedidoToPedidoResponseDTO(pedido));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> update(
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id,
            @RequestBody @Valid PedidoUpdateDTO dtoUpdate
    ) {
        Pedido pedidoUpdate = pedidoService.update(id, dtoUpdate);
        return ResponseEntity.ok().body(pedidoMapper.pedidoToPedidoResponseDTO(pedidoUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id
    ) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
