package com.dynns.cloudtecnologia.senior.rest.dto.pedido;

import com.dynns.cloudtecnologia.senior.model.enums.SituacaoPedidoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoResponseDTO {
    private UUID id;
    private SituacaoPedidoEnum situacao;
    private String descricao;
    private int percentualDesconto;
    private BigDecimal totalBruto;
    private BigDecimal totalDescontos;
    private BigDecimal totalLiquido;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataAtualizacao;
}
