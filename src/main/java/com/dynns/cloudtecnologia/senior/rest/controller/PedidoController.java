package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoResponseDTO;
import com.dynns.cloudtecnologia.senior.rest.mapper.PedidoMapper;
import com.dynns.cloudtecnologia.senior.service.impl.PedidoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {
    @Autowired
    private PedidoServiceImpl pedidoService;
    @Autowired
    private PedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<Pedido> save(
            @RequestBody @Valid PedidoNewDTO dto
    ) {
        Pedido pedidoSalvo = pedidoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }
}
