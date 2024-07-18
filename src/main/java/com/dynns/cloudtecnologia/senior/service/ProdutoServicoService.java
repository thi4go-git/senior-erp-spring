package com.dynns.cloudtecnologia.senior.service;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoNewDTO;

public interface ProdutoServicoService {
    ProdutoServico salvar(ProdutoServicoNewDTO produtoServico);
}
