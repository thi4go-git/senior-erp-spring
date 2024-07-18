package com.dynns.cloudtecnologia.senior.anottation.impl;

import com.dynns.cloudtecnologia.senior.anottation.SituacaoPedidoEnumValido;
import com.dynns.cloudtecnologia.senior.model.enums.SituacaoPedidoEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class SituacaoPedidoEnumValidoImpl implements ConstraintValidator<SituacaoPedidoEnumValido, String> {
    @Override
    public boolean isValid(String situacaoPedidoEnumStr, ConstraintValidatorContext context) {
        try {
            if (Objects.nonNull(situacaoPedidoEnumStr)) {
                SituacaoPedidoEnum.valueOf(situacaoPedidoEnumStr.trim());
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
