package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoResponseDTO;
import com.dynns.cloudtecnologia.senior.rest.mapper.PedidoMapper;
import com.dynns.cloudtecnologia.senior.service.impl.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {
    @Autowired
    private PedidoServiceImpl pedidoService;
    @Autowired
    private PedidoMapper pedidoMapper;

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
}
