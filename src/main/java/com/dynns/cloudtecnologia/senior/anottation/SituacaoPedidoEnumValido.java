package com.dynns.cloudtecnologia.senior.anottation;

import com.dynns.cloudtecnologia.senior.anottation.impl.SituacaoPedidoEnumValidoImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SituacaoPedidoEnumValidoImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SituacaoPedidoEnumValido {
    String message() default "O campo situacao não contém um SituacaoPedidoEnum válido = ABERTO ou FECHADO.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
