package com.dynns.cloudtecnologia.senior.utils;

import com.dynns.cloudtecnologia.senior.exception.GeralException;
import com.dynns.cloudtecnologia.senior.model.enums.TipoEnum;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public abstract class SeniorErpUtil {
    public static TipoEnum definirTipoEnum(String tipoEnumStr) {
        try {
            if (Objects.nonNull(tipoEnumStr)) {
                return TipoEnum.valueOf(tipoEnumStr.trim());
            }
            throw new GeralException("Erro ao converter String nula 'null' ");
        } catch (IllegalArgumentException e) {
            throw new GeralException("Erro ao converter String " + tipoEnumStr
                    + " para TipoEnum!");
        }
    }
}
