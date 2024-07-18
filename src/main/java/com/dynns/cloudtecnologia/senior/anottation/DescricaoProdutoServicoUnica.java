package com.dynns.cloudtecnologia.senior.anottation;

import com.dynns.cloudtecnologia.senior.anottation.impl.DescricaoProdutoServicoUnicaImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DescricaoProdutoServicoUnicaImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DescricaoProdutoServicoUnica {
    String message() default "O campo DESCRICAO já está em uso!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
