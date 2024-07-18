package com.dynns.cloudtecnologia.senior.anottation;


import com.dynns.cloudtecnologia.senior.anottation.impl.TipoEnumValidoImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TipoEnumValidoImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoEnumValido {
    String message() default "O campo tipo não contém um TipoEnum válido = PRODUTO ou SERVICO.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
