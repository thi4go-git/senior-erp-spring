package com.dynns.cloudtecnologia.senior.rest.dto.produtoServico;

import com.dynns.cloudtecnologia.senior.model.enums.TipoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoServicoResponseDTO {
    private UUID id;
    private TipoEnum tipo;
    private String descricao;
    private BigDecimal preco;
    private boolean ativo;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dataAtualizacao;
}
