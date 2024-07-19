package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoUpdateDTO;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoServicoService {
    ProdutoServico save(ProdutoServicoNewDTO produtoServico);

    Optional<ProdutoServico> findByDescricao(String descricao);

    Page<ProdutoServico> show(int page, int size, ProdutoServicoFilterDTO filter);

    ProdutoServico update(String id, ProdutoServicoUpdateDTO dtoUpdate);

    Optional<ProdutoServico> findById(UUID id);

    List<ProdutoServico> findAll();
}
