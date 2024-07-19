package com.dynns.cloudtecnologia.senior.rest.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoFilterDTO {
    private String id;
    private String situacao;
    private String descricao;
}
