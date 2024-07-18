package com.dynns.cloudtecnologia.senior.model.entity;

import com.dynns.cloudtecnologia.senior.model.enums.SituacaoPedidoEnum;
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
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "UUID")
    private UUID id;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

    @Column(nullable = false)
    private SituacaoPedidoEnum situacao;

    @Column(length = 150)
    private String descricao;

    @Column(nullable = false, name = "percentual_desconto")
    private int percentualDesconto;

    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, name = "total_bruto", precision = 12, scale = 2)
    private BigDecimal totalBruto;

    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, name = "total_descontos", precision = 12, scale = 2)
    private BigDecimal totalDescontos;

    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, name = "total_liquido", precision = 12, scale = 2)
    private BigDecimal totalLiquido;

    @Column(nullable = false, name = "data_criacao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataCriacao;

    @Column(nullable = false, name = "data_atualizacao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }
}
