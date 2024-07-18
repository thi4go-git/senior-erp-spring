package com.dynns.cloudtecnologia.senior.service.impl;

import com.dynns.cloudtecnologia.senior.exception.GeralException;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.model.enums.TipoEnum;
import com.dynns.cloudtecnologia.senior.model.repository.ProdutoServicoRepository;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.mapper.ProdutoServicoMapper;
import com.dynns.cloudtecnologia.senior.service.ProdutoServicoService;
import com.dynns.cloudtecnologia.senior.utils.SeniorErpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoServicoServiceImpl implements ProdutoServicoService {

    @Autowired
    private ProdutoServicoRepository produtoServicoRepository;

    @Autowired
    private ProdutoServicoMapper produtoServicoMapper;


    @Transactional
    @Override
    public ProdutoServico salvar(ProdutoServicoNewDTO produtoServicoDto) {
        ProdutoServico produtoServico = ProdutoServico.builder()
                .tipo(SeniorErpUtil.definirTipoEnum(produtoServicoDto.getTipo().trim()))
                .descricao(produtoServicoDto.getDescricao())
                .preco(BigDecimal.valueOf(produtoServicoDto.getPreco()))
                .build();
        return produtoServicoRepository.save(produtoServico);
    }

    @Override
    public Optional<ProdutoServico> findByDescricao(String descricao) {
        return produtoServicoRepository.findByDescricao(descricao);
    }

    @Override
    public Page<ProdutoServico> findAllPageFilter(int page, int size, ProdutoServicoFilterDTO filter) {

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        TipoEnum tipo = null;
        if (Objects.nonNull(filter.getTipo())) {
            tipo = SeniorErpUtil.definirTipoEnum(filter.getTipo().trim());
        }

        UUID idSanitizado;
        if (Objects.isNull(filter.getId())) {
            idSanitizado = null;
        } else {
            try {
                idSanitizado = UUID.fromString(filter.getId());
            } catch (IllegalArgumentException e) {
                throw new GeralException("id tipo UUID inválido: " + filter.getId());
            }
        }

        ProdutoServico filtroProdutoServico = produtoServicoMapper.ProdutoServicoFilterDtoToProdutoServico(filter);
        filtroProdutoServico.setId(idSanitizado);
        filtroProdutoServico.setTipo(tipo);
        filtroProdutoServico.setAtivo(true);

        Example<ProdutoServico> example = Example.of(filtroProdutoServico, matcher);
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.Direction.ASC, "dataCriacao");

        Page<ProdutoServico> pages = produtoServicoRepository.findAll(example, pageRequest);

        return pages;
    }
}
