package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoResponseDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoUpdateDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.mapper.ProdutoServicoMapper;
import com.dynns.cloudtecnologia.senior.service.impl.ProdutoServicoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@RestController
@RequestMapping("api/produtos-servicos")
public class ProdutoServicoController {

    @Autowired
    private ProdutoServicoServiceImpl produtoServicoService;
    @Autowired
    private ProdutoServicoMapper produtoServicoMapper;

    @PostMapping
    public ResponseEntity<ProdutoServicoResponseDTO> save(
            @RequestBody @Valid ProdutoServicoNewDTO dto
    ) {
        ProdutoServico produtoServicoSalvo = produtoServicoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoServicoMapper.produtoServicoToProdutoServicoResponseDTO(produtoServicoSalvo));
    }

    @PostMapping("/show")
    public ResponseEntity<Page<ProdutoServicoResponseDTO>> show(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestBody ProdutoServicoFilterDTO filtro
    ) {
        Page<ProdutoServico> pageProdutoServico = produtoServicoService.show(page, size, filtro);
        return ResponseEntity.ok().body(produtoServicoMapper.pageProdutoServicoToPageProdutoServicoResponseDTO(pageProdutoServico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoServicoResponseDTO> update(
            @PathVariable("id") @NotBlank(message = "O Campo id é Obrigatório!") final String id,
            @RequestBody @Valid ProdutoServicoUpdateDTO dtoUpdate
    ) {
        ProdutoServico produtoServicoUpdate = produtoServicoService.update(id, dtoUpdate);
        return ResponseEntity.ok().body(produtoServicoMapper.produtoServicoToProdutoServicoResponseDTO(produtoServicoUpdate));
    }

    @PatchMapping("/{id}/ativar-desativar")
    public ResponseEntity<ProdutoServicoResponseDTO> ativarDesativar(
            @PathVariable("id") @NotBlank(message = "O Campo id é Obrigatório!") final String id
    ) {
        ProdutoServico produtoServico = produtoServicoService.ativarDesativar(id);
        return ResponseEntity.ok().body(produtoServicoMapper.produtoServicoToProdutoServicoResponseDTO(produtoServico));
    }
}
