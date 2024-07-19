package com.dynns.cloudtecnologia.senior.rest.dto.produtoservico;

import com.dynns.cloudtecnologia.senior.model.enums.AtivoEnum;
import com.dynns.cloudtecnologia.senior.model.enums.TipoProdutoServicoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoServicoResponseDTO {
    private UUID id;
    private TipoProdutoServicoEnum tipo;
    private String descricao;
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private AtivoEnum ativo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataAtualizacao;
}
