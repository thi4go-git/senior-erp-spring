package com.dynns.cloudtecnologia.senior.rest.dto.item_pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPedidoNewDTO {
    private String idProdutoServico;
    private int qtde;
}
