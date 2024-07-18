package com.dynns.cloudtecnologia.senior.rest.dto.produtoServico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoServicoFilterDTO {
    private String id;
    private String tipo;
    private String descricao;
    private BigDecimal preco;
    private String ativo;
}
