package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProdutoServicoService {
    ProdutoServico salvar(ProdutoServicoNewDTO produtoServico);

    Optional<ProdutoServico> findByDescricao(String descricao);

    Page<ProdutoServico> findAllPageFilter(int page, int size, ProdutoServicoFilterDTO filter);

    ProdutoServico update(String id, ProdutoServicoUpdateDTO dtoUpdate);
}
