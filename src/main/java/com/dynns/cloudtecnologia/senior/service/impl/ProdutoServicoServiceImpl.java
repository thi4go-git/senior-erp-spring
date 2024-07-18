package com.dynns.cloudtecnologia.senior.service.impl;

import com.dynns.cloudtecnologia.senior.exception.GeralException;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.model.enums.TipoEnum;
import com.dynns.cloudtecnologia.senior.model.repository.ProdutoServicoRepository;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.service.ProdutoServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProdutoServicoServiceImpl implements ProdutoServicoService {

    @Autowired
    private ProdutoServicoRepository produtoServicoRepository;


    @Transactional
    @Override
    public ProdutoServico salvar(ProdutoServicoNewDTO produtoServicoDto) {
        TipoEnum tipo;
        try {
            tipo = TipoEnum.valueOf(produtoServicoDto.getTipo().trim());
        } catch (IllegalArgumentException e) {
            throw new GeralException("Erro ao converter String " + produtoServicoDto.getTipo().trim()
                    + " para TipoEnum!");
        }

        ProdutoServico produtoServico = ProdutoServico.builder()
                .tipo(tipo)
                .descricao(produtoServicoDto.getDescricao())
                .preco(BigDecimal.valueOf(produtoServicoDto.getPreco()))
                .build();
        return produtoServicoRepository.save(produtoServico);
    }

    @Override
    public Optional<ProdutoServico> findByDescricao(String descricao) {
        return produtoServicoRepository.findByDescricao(descricao);
    }
}
