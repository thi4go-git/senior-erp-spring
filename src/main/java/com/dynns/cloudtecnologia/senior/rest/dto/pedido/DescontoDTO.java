package com.dynns.cloudtecnologia.senior.rest.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DescontoDTO {
    @NotNull(message = "{campo.percentualDesconto.obrigatorio}")
    @DecimalMin(value = "1.00", message = "{campo.percentualDesconto.maiorQueZero}")
    @DecimalMax(value = "99", message = "{campo.percentualDesconto.menorQueCem}")
    private BigDecimal percentualDesconto;
}
