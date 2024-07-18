package com.dynns.cloudtecnologia.senior.anottation.impl;

import com.dynns.cloudtecnologia.senior.anottation.TipoEnumValido;
import com.dynns.cloudtecnologia.senior.model.enums.TipoEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TipoEnumValidoImpl implements ConstraintValidator<TipoEnumValido, String> {
    @Override
    public boolean isValid(String tipoEnumStr, ConstraintValidatorContext context) {
        try {
            TipoEnum.valueOf(tipoEnumStr.trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
