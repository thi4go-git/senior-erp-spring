package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoResponseDTO;
import com.dynns.cloudtecnologia.senior.rest.mapper.ProdutoServicoMapper;
import com.dynns.cloudtecnologia.senior.service.impl.ProdutoServicoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/produto-servico")
public class ProdutoServicoController {

    @Autowired
    private ProdutoServicoServiceImpl produtoServicoService;
    @Autowired
    private ProdutoServicoMapper produtoServicoMapper;

    @PostMapping
    public ResponseEntity<ProdutoServicoResponseDTO> salvar(
            @RequestBody @Valid ProdutoServicoNewDTO dto
    ) {
        ProdutoServico ProdutoServicoSalvo = produtoServicoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoServicoMapper.produtoServicoToProdutoServicoResponseDTO(ProdutoServicoSalvo));
    }

    @PostMapping("/show")
    public ResponseEntity<Page<ProdutoServicoResponseDTO>> findAllPageFilter(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestBody ProdutoServicoFilterDTO filtro
    ) {
        Page<ProdutoServico> pageProdutoServico = produtoServicoService.findAllPageFilter(page, size, filtro);
        return ResponseEntity.ok().body(produtoServicoMapper.pageProdutoServicoToPageProdutoServicoResponseDTO(pageProdutoServico));
    }

}
