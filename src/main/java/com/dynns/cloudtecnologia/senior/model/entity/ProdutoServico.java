package com.dynns.cloudtecnologia.senior.model.entity;

import com.dynns.cloudtecnologia.senior.model.enums.AtivoEnum;
import com.dynns.cloudtecnologia.senior.model.enums.TipoProdutoServicoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoServico {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false)
    private TipoProdutoServicoEnum tipo;

    @Column(nullable = false, length = 150)
    private String descricao;

    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false, name = "data_criacao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataCriacao;

    @Column(nullable = false, name = "data_atualizacao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataAtualizacao;

    @Column(nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private AtivoEnum ativo;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        ativo = AtivoEnum.S;
    }

}
