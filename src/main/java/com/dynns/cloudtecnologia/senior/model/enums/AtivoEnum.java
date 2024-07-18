package com.dynns.cloudtecnologia.senior.model.enums;

import com.dynns.cloudtecnologia.senior.exception.GeralException;
import lombok.Getter;

@Getter
public enum AtivoEnum {
    S('S'),
    N('N');

    private final char value;

    AtivoEnum(char value) {
        this.value = value;
    }

    private static final String MSG_INVALIDO = "Valor inválido para enum AtivoEnum: ";
    private static final String MSG_INVALID1 = ". AtivoEnum deve ser: S ou N";

    public static AtivoEnum fromValue(char value) {
        for (AtivoEnum ativoEnum : AtivoEnum.values()) {
            if (ativoEnum.value == value) {
                return ativoEnum;
            }
        }
        throw new GeralException(MSG_INVALIDO + value + MSG_INVALID1);
    }

    public static AtivoEnum fromString(String value) {
        if (value == null || value.length() != 1) {
            throw new GeralException(MSG_INVALIDO + value + MSG_INVALID1);
        }
        return fromValue(value.charAt(0));
    }
}
