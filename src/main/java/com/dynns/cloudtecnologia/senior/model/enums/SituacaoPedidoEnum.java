package com.dynns.cloudtecnologia.senior.model.enums;

import com.dynns.cloudtecnologia.senior.exception.GeralException;

public enum SituacaoPedidoEnum {
    ABERTO, FECHADO;

    private static final String MSG_INVALIDO = "Valor inválido para enum SituacaoPedidoEnum: ";
    private static final String MSG_INVALID1 = ". SituacaoPedidoEnum deve ser: ABERTO ou FECHADO";

    public static SituacaoPedidoEnum fromString(String value) {
        if (value == null) {
            throw new GeralException(MSG_INVALIDO + MSG_INVALID1);
        }
        try {
            return SituacaoPedidoEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GeralException(MSG_INVALIDO + value + MSG_INVALID1);
        }
    }
}
