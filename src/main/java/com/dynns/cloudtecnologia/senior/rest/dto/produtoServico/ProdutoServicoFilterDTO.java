package com.dynns.cloudtecnologia.senior.rest.dto.produtoServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoServicoFilterDTO {
    private String id;
    private String tipo;
    private String descricao;
    private BigDecimal preco;
}
