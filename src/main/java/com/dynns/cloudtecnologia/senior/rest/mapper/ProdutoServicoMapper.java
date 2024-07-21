package com.dynns.cloudtecnologia.senior.rest.mapper;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoServicoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoServicoResponseDTO produtoServicoToProdutoServicoResponseDTO(ProdutoServico produtoServico) {
        return modelMapper.map(produtoServico, ProdutoServicoResponseDTO.class);
    }

    public ProdutoServico produtoServicoFilterDtoToProdutoServico(ProdutoServicoFilterDTO filterDTO) {
        return modelMapper.map(filterDTO, ProdutoServico.class);
    }


    public Page<ProdutoServicoResponseDTO> pageProdutoServicoToPageProdutoServicoResponseDTO(Page<ProdutoServico> pageProdutoServico) {

        List<ProdutoServicoResponseDTO> produtoServicoList = pageProdutoServico.stream()
                .map(produtoServico -> modelMapper.map(produtoServico, ProdutoServicoResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(produtoServicoList, pageProdutoServico.getPageable(), pageProdutoServico.getTotalElements());
    }
}
