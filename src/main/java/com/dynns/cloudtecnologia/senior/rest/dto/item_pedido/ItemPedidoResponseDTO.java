package com.dynns.cloudtecnologia.senior.rest.dto.item_pedido;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemPedidoResponseDTO {
    private UUID id;
    private ProdutoServico produtoServico;
    private int qtde;
    private BigDecimal valorTotal;
}
