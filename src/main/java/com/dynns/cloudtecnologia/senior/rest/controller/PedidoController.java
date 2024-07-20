package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.rest.dto.item_pedido.ItemPedidoResponseDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.*;
import com.dynns.cloudtecnologia.senior.rest.mapper.ItemPedidoMapper;
import com.dynns.cloudtecnologia.senior.rest.mapper.PedidoMapper;
import com.dynns.cloudtecnologia.senior.service.impl.PedidoServiceImpl;
import io.swagger.annotations.*;
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
@Api("API - Pedidos")
public class PedidoController {
    @Autowired
    private PedidoServiceImpl pedidoService;
    @Autowired
    private PedidoMapper pedidoMapper;
    @Autowired
    private ItemPedidoMapper itemPedidoMapper;
    private static final String ID_OBRIGATORIO = "O Campo id é Obrigatório!";
    private static final String ERRO_INTERNO = "Ocorreu um erro interno no Servidor!";
    private static final String MSG_NOTFOUND = "Pedido não localizado!";


    @PostMapping
    @ApiOperation("Cria um Pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pedido criado com sucesso!"),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<PedidoResponseDTO> save(
            @ApiParam(value = "Dados do novo Pedido", required = true)
            @RequestBody @Valid PedidoNewDTO dto
    ) {
        Pedido pedidoSalvo = pedidoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoMapper.pedidoToPedidoResponseDTO(pedidoSalvo));
    }

    @PostMapping("/show")
    @ApiOperation("Listagem de Pedidos através de Filtros / Com Paginação obs: Informe no filtro apenas os campos necessários, caso contrário deixe o atributo como null")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dados carregados com Sucesso!"),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<Page<PedidoResponseDTO>> show(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @ApiParam(value = "Dados para aplicar Filtro")
            @RequestBody PedidoFilterDTO filtro
    ) {
        Page<Pedido> pageProdutoServico = pedidoService.show(page, size, filtro);
        return ResponseEntity.ok().body(pedidoMapper.pagePedidoToPagePedidoResponseDTO(pageProdutoServico));
    }

    @PatchMapping("/{id}")
    @ApiOperation("Fechar um Pedido através do ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pedido fechado com sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 400, message = "O Pedido já está fechado."),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<PedidoResponseDTO> fecharPedido(
            @ApiParam(value = "ID do Pedido", required = true)
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id
    ) {
        Pedido pedidoFechado = pedidoService.fecharPedido(id);
        return ResponseEntity.ok().body(pedidoMapper.pedidoToPedidoResponseDTO(pedidoFechado));
    }

    @GetMapping("/{id}")
    @ApiOperation("Carregar Pedido pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pedido carregado com sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<PedidoResponseDTO> showById(
            @ApiParam(value = "ID do Pedido", required = true)
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id
    ) {
        Pedido pedido = pedidoService.showById(id);
        return ResponseEntity.ok().body(pedidoMapper.pedidoToPedidoResponseDTO(pedido));
    }

    @GetMapping("/{id}/itens")
    @ApiOperation("Obter Itens de um Pedido através do ID do Pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Itens carregados com sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<List<ItemPedidoResponseDTO>> showItens(
            @ApiParam(value = "ID do Pedido", required = true)
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id
    ) {
        List<ItemPedido> itens = pedidoService.getItensPedido(id);
        return ResponseEntity.ok().body(itemPedidoMapper.listItemPedidoToListItemPedidoResponseDTO(itens));
    }

    @PostMapping("/{id}/desconto")
    @ApiOperation("Aplicar desconto no Pedido pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Desconto concedido com sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 400, message = "Não foi possível aplicar desconto: Pedido fechado ou Desconto já aplicado."),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<PedidoResponseDTO> aplicarDesconto(
            @ApiParam(value = "ID do Pedido", required = true)
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id,
            @RequestBody @Valid DescontoDTO descontoDto
    ) {
        Pedido pedido = pedidoService.aplicarDesconto(id, descontoDto);
        return ResponseEntity.ok().body(pedidoMapper.pedidoToPedidoResponseDTO(pedido));
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar Pedido através do ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pedido Atualizado com Sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<PedidoResponseDTO> update(
            @ApiParam(value = "ID do Pedido", required = true)
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id,
            @ApiParam(value = "Dados atualizados do Pedido", required = true)
            @RequestBody @Valid PedidoUpdateDTO dtoUpdate
    ) {
        Pedido pedidoUpdate = pedidoService.update(id, dtoUpdate);
        return ResponseEntity.ok().body(pedidoMapper.pedidoToPedidoResponseDTO(pedidoUpdate));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deleta o Pedido pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pedido deletado com sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<Void> delete(
            @ApiParam(value = "ID do Pedido", required = true)
            @PathVariable("id") @NotBlank(message = ID_OBRIGATORIO) final String id
    ) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
