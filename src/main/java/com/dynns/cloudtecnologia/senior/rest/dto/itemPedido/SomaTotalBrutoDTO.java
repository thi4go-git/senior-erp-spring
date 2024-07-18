package com.dynns.cloudtecnologia.senior.rest.dto.itemPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SomaTotalBrutoDTO {
    BigDecimal totalBrutoGeral;
    BigDecimal totalBrutoProdutos;
    BigDecimal totalBrutoServicos;
}
