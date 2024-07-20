package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoResponseDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoUpdateDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.mapper.ProdutoServicoMapper;
import com.dynns.cloudtecnologia.senior.service.impl.ProdutoServicoServiceImpl;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@RestController
@RequestMapping("api/produtos-servicos")
@Api("API - Produtos/Serviços")
public class ProdutoServicoController {

    @Autowired
    private ProdutoServicoServiceImpl produtoServicoService;
    @Autowired
    private ProdutoServicoMapper produtoServicoMapper;
    private static final String ERRO_INTERNO = "Ocorreu um erro interno no Servidor!";
    private static final String MSG_NOTFOUND = "Produto/Serviço não localizado!";

    @PostMapping
    @ApiOperation("Cria um Produto/Serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Produto/Serviço criado com Sucesso!"),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<ProdutoServicoResponseDTO> save(
            @ApiParam(value = "Dados do novo Produto/Serviço", required = true)
            @RequestBody @Valid ProdutoServicoNewDTO dto
    ) {
        ProdutoServico produtoServicoSalvo = produtoServicoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoServicoMapper.produtoServicoToProdutoServicoResponseDTO(produtoServicoSalvo));
    }

    @PostMapping("/show")
    @ApiOperation("Listagem de Produto/Serviço através de Filtros / Com Paginação obs: Informe no filtro apenas os campos necessários, caso contrário deixe o atributo como null")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dados carregados com Sucesso!"),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<Page<ProdutoServicoResponseDTO>> show(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @ApiParam(value = "Dados para aplicar Filtro")
            @RequestBody ProdutoServicoFilterDTO filtro
    ) {
        Page<ProdutoServico> pageProdutoServico = produtoServicoService.show(page, size, filtro);
        return ResponseEntity.ok().body(produtoServicoMapper.pageProdutoServicoToPageProdutoServicoResponseDTO(pageProdutoServico));
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar um Produto/Serviço através do ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto/Serviço Atualizado com Sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<ProdutoServicoResponseDTO> update(
            @ApiParam(value = "ID do Produto/Serviço", required = true)
            @PathVariable("id") @NotBlank(message = "O Campo id é Obrigatório!") final String id,
            @ApiParam(value = "Dados atualizados do Produto/Serviço", required = true)
            @RequestBody @Valid ProdutoServicoUpdateDTO dtoUpdate
    ) {
        ProdutoServico produtoServicoUpdate = produtoServicoService.update(id, dtoUpdate);
        return ResponseEntity.ok().body(produtoServicoMapper.produtoServicoToProdutoServicoResponseDTO(produtoServicoUpdate));
    }

    @PatchMapping("/{id}/ativar-desativar")
    @ApiOperation("Ativar/Desativar um Produto/Serviço através do ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status da ativação do produto atualizado com sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<ProdutoServicoResponseDTO> ativarDesativar(
            @ApiParam(value = "ID do Produto/Serviço", required = true)
            @PathVariable("id") @NotBlank(message = "O Campo id é Obrigatório!") final String id
    ) {
        ProdutoServico produtoServico = produtoServicoService.ativarDesativar(id);
        return ResponseEntity.ok().body(produtoServicoMapper.produtoServicoToProdutoServicoResponseDTO(produtoServico));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deleta um Produto/Serviço através do ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Produto/Serviço deletado com sucesso!"),
            @ApiResponse(code = 404, message = MSG_NOTFOUND),
            @ApiResponse(code = 500, message = ERRO_INTERNO)
    })
    public ResponseEntity<Void> delete(
            @ApiParam(value = "ID do Produto/Serviço", required = true)
            @PathVariable("id") @NotBlank(message = "O Campo id é Obrigatório!") final String id
    ) {
        produtoServicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
