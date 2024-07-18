package com.dynns.cloudtecnologia.senior.model.enums;

import com.dynns.cloudtecnologia.senior.exception.GeralException;

public enum TipoEnum {
    PRODUTO, SERVICO;

    private static final String MSG_INVALIDO = "Valor inválido para enum TipoEnum: ";
    private static final String MSG_INVALID1 = ". TipoEnum deve ser: PRODUTO ou SERVICO";

    public static TipoEnum fromString(String value) {
        if (value == null) {
            throw new GeralException(MSG_INVALIDO + value + MSG_INVALID1);
        }
        try {
            return TipoEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GeralException(MSG_INVALIDO + value + MSG_INVALID1);
        }
    }
}
