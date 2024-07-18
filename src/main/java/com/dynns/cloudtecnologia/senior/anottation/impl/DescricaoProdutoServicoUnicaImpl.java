package com.dynns.cloudtecnologia.senior.anottation.impl;

import com.dynns.cloudtecnologia.senior.anottation.DescricaoProdutoServicoUnica;
import com.dynns.cloudtecnologia.senior.service.impl.ProdutoServicoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class DescricaoProdutoServicoUnicaImpl implements ConstraintValidator<DescricaoProdutoServicoUnica, String> {

    @Autowired
    private ProdutoServicoServiceImpl produtoServicoService;

    @Override
    public boolean isValid(String descricao, ConstraintValidatorContext context) {
        return produtoServicoService.findByDescricao(descricao).isEmpty();
    }
}
