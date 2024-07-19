package com.dynns.cloudtecnologia.senior.service.impl;

import com.dynns.cloudtecnologia.senior.model.enums.AtivoEnum;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.model.enums.TipoProdutoServicoEnum;
import com.dynns.cloudtecnologia.senior.model.repository.ProdutoServicoRepository;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoUpdateDTO;
import com.dynns.cloudtecnologia.senior.rest.mapper.ProdutoServicoMapper;
import com.dynns.cloudtecnologia.senior.service.ProdutoServicoService;
import com.dynns.cloudtecnologia.senior.utils.SeniorErpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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
    public ProdutoServico save(ProdutoServicoNewDTO produtoServicoDto) {
        ProdutoServico produtoServico = ProdutoServico.builder()
                .tipo(TipoProdutoServicoEnum.fromString(produtoServicoDto.getTipo()))
                .descricao(produtoServicoDto.getDescricao())
                .preco(SeniorErpUtil.ajustarDuasCasasDecimais(produtoServicoDto.getPreco()))
                .build();
        return produtoServicoRepository.save(produtoServico);
    }

    @Override
    public Optional<ProdutoServico> findByDescricao(String descricao) {
        return produtoServicoRepository.findByDescricao(descricao);
    }

    @Override
    public Page<ProdutoServico> show(int page, int size, ProdutoServicoFilterDTO filter) {

        TipoProdutoServicoEnum tipo = null;
        if (Objects.nonNull(filter.getTipo())) {
            tipo = TipoProdutoServicoEnum.fromString(filter.getTipo().trim());
        }

        AtivoEnum ativo = null;
        if (Objects.nonNull(filter.getAtivo())) {
            ativo = AtivoEnum.fromString(filter.getAtivo().trim());
        }

        UUID idSanitizado;
        if (Objects.isNull(filter.getId())) {
            idSanitizado = null;
        } else {
            idSanitizado = SeniorErpUtil.retornarUUIDSanitizado(filter.getId().trim());
        }

        ProdutoServico filtroProdutoServico = produtoServicoMapper.ProdutoServicoFilterDtoToProdutoServico(filter);
        filtroProdutoServico.setId(idSanitizado);
        filtroProdutoServico.setTipo(tipo);
        filtroProdutoServico.setAtivo(ativo);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<ProdutoServico> example = Example.of(filtroProdutoServico, matcher);
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.Direction.ASC, "dataCriacao");

        return produtoServicoRepository.findAll(example, pageRequest);
    }

    @Override
    public ProdutoServico update(String id, ProdutoServicoUpdateDTO dtoUpdate) {
        UUID idSanitizado = SeniorErpUtil.retornarUUIDSanitizado(id);
        return produtoServicoRepository.findById(idSanitizado).map(prodServ -> {
            prodServ.setTipo(TipoProdutoServicoEnum.fromString(dtoUpdate.getTipo().trim()));
            prodServ.setDescricao(dtoUpdate.getDescricao().trim());
            prodServ.setPreco(SeniorErpUtil.ajustarDuasCasasDecimais(dtoUpdate.getPreco()));
            prodServ.setAtivo(AtivoEnum.fromString(dtoUpdate.getAtivo().trim()));
            prodServ.setDataAtualizacao(LocalDateTime.now());
            return produtoServicoRepository.save(prodServ);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ProdutoServico com Id UUID não localizado: " + id));
    }

    @Override
    public Optional<ProdutoServico> findById(UUID id) {
        return produtoServicoRepository.findById(id);
    }

    @Override
    public List<ProdutoServico> findAll() {
        return produtoServicoRepository.findAll();
    }
}
