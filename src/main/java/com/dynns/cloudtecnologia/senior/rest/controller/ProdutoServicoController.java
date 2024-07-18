package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoResponseDTO;
import com.dynns.cloudtecnologia.senior.rest.mapper.ProdutoServicoMapper;
import com.dynns.cloudtecnologia.senior.service.impl.ProdutoServicoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("api/produto-servico")
public class ProdutoServicoController {

    @Autowired
    private ProdutoServicoServiceImpl produtoServicoService;
    @Autowired
    private ProdutoServicoMapper produtoServicoMapper;

    @PostMapping
    public ResponseEntity<ProdutoServicoResponseDTO> salvarProdutoServico(
            @RequestBody @Valid ProdutoServicoNewDTO dto
    ) {
        ProdutoServico ProdutoServicoSalvo = produtoServicoService.salvar(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ProdutoServicoSalvo.getId())
                .toUri();
        return ResponseEntity.created(location).body(produtoServicoMapper.produtoServicoToProdutoServicoResponseDTO(ProdutoServicoSalvo));

    }

}
