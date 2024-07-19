package com.dynns.cloudtecnologia.senior.rest.dto.pedido;

import com.dynns.cloudtecnologia.senior.rest.dto.item_pedido.ItemPedidoNewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoNewDTO {

    @NotBlank(message = "{campo.descricao.obrigatorio}")
    private String descricao;

    @NotNull(message = "{campo.itensPedido.obrigatorio}")
    @NotEmpty(message = "{campo.itensPedido.naoVazio}")
    private List<ItemPedidoNewDTO> itensPedido;
}
