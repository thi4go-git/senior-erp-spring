package com.dynns.cloudtecnologia.senior.rest.dto.pedido;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoFilterDTO {
    @ApiModelProperty(value = "Id do Pedido", example = "3255175d-5b89-4aba-a67c-9185b8018bcd")
    private String id;

    @ApiModelProperty(value = "Situação do Pedido", example = "ABERTO ou FECHADO")
    private String situacao;

    @ApiModelProperty(value = "Descrição do Pedido", example = "Descrição do Pedido ou parte dela")
    private String descricao;
}
