package com.dynns.cloudtecnologia.senior.rest.dto.produtoservico;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoServicoFilterDTO {
    private String id;
    private String tipo;
    private String descricao;
    private BigDecimal preco;
    private String ativo;
}
