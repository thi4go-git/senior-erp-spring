package com.dynns.cloudtecnologia.senior.rest.dto.produtoServico;

import com.dynns.cloudtecnologia.senior.anottation.DescricaoProdutoServicoUnica;
import com.dynns.cloudtecnologia.senior.anottation.TipoEnumValido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoServicoNewDTO {

    @TipoEnumValido
    private String tipo;

    @DescricaoProdutoServicoUnica
    @NotBlank(message = "{campo.nomeArquivo.obrigatorio}")
    private String descricao;

    @NotNull(message = "{campo.nomeArquivo.obrigatorio}")
    @DecimalMin(value = "1.00", message = "{campo.preco.maiorQueZero}")
    private BigDecimal preco;
}
