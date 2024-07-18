package com.dynns.cloudtecnologia.senior.anottation.impl;

import com.dynns.cloudtecnologia.senior.anottation.TipoEnumValido;
import com.dynns.cloudtecnologia.senior.model.enums.TipoProdutoServicoEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TipoEnumValidoImpl implements ConstraintValidator<TipoEnumValido, String> {
    @Override
    public boolean isValid(String tipoEnumStr, ConstraintValidatorContext context) {
        try {
            if (Objects.nonNull(tipoEnumStr)) {
                TipoProdutoServicoEnum.valueOf(tipoEnumStr.trim());
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
