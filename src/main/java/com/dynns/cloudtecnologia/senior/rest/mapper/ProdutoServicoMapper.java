package com.dynns.cloudtecnologia.senior.rest.mapper;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoServico.ProdutoServicoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoServicoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoServicoResponseDTO produtoServicoToProdutoServicoResponseDTO(ProdutoServico produtoServico) {
        return modelMapper.map(produtoServico, ProdutoServicoResponseDTO.class);
    }

}
