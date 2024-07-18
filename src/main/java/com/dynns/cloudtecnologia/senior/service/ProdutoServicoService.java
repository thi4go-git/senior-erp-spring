package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoNewDTO;

import java.util.Optional;

public interface ProdutoServicoService {
    ProdutoServico salvar(ProdutoServicoNewDTO produtoServico);
    Optional<ProdutoServico> findByDescricao(String descricao);
}
