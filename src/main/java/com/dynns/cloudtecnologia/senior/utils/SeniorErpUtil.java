package com.dynns.cloudtecnologia.senior.utils;

import com.dynns.cloudtecnologia.senior.exception.GeralException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Component
public abstract class SeniorErpUtil {

    private SeniorErpUtil() {
    }

    public static UUID retornarUUIDSanitizado(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new GeralException("Id UUID inválido: " + id);
        }
    }

    public static BigDecimal intToBigDecimal(int inteiro) {
        return BigDecimal.valueOf(inteiro).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal ajustarDuasCasasDecimais(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

}
