package com.dynns.cloudtecnologia.senior.rest.dto.pedido;

import com.dynns.cloudtecnologia.senior.anottation.SituacaoPedidoEnumValido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoUpdateDTO {

    @NotBlank(message = "{campo.situacao.obrigatorio}")
    @SituacaoPedidoEnumValido
    private String situacao;

    @NotBlank(message = "{campo.descricao.obrigatorio}")
    private String descricao;
}
