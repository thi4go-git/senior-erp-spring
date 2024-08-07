package com.dynns.cloudtecnologia.senior.rest.dto.produtoservico;

import com.dynns.cloudtecnologia.senior.anottation.TipoEnumValido;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoServicoUpdateDTO {

    @NotBlank(message = "{campo.tipo.obrigatorio}")
    @TipoEnumValido
    private String tipo;

    @NotBlank(message = "{campo.descricao.obrigatorio}")
    private String descricao;

    @NotNull(message = "{campo.preco.obrigatorio}")
    @DecimalMin(value = "1.00", message = "{campo.preco.maiorQueZero}")
    private BigDecimal preco;

    @NotBlank(message = "{campo.ativo.obrigatorio}")
    private String ativo;
}
