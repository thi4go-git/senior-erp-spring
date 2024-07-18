package com.dynns.cloudtecnologia.senior.anottation.impl;

import com.dynns.cloudtecnologia.senior.anottation.DescricaoProdutoServicoUnica;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DescricaoProdutoServicoUnicaImpl implements ConstraintValidator<DescricaoProdutoServicoUnica, String> {
    @Override
    public boolean isValid(String descricao, ConstraintValidatorContext context) {
        return true;
    }
}
