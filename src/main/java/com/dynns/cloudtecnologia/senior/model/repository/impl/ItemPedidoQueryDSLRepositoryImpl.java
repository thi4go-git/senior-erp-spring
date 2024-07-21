package com.dynns.cloudtecnologia.senior.model.repository.impl;

import com.dynns.cloudtecnologia.senior.model.enums.TipoProdutoServicoEnum;
import com.dynns.cloudtecnologia.senior.model.repository.ItemPedidoQueryDSLRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;


import static com.dynns.cloudtecnologia.senior.model.entity.QItemPedido.itemPedido;
import static com.dynns.cloudtecnologia.senior.model.entity.QProdutoServico.produtoServico;


@Repository
public class ItemPedidoQueryDSLRepositoryImpl implements ItemPedidoQueryDSLRepository {

    @Autowired
    private JPAQueryFactory queryFactory;

    private static final int PRODUTO = 0;

    @Override
    public BigDecimal getSomaValorBrutoItensProdutosQueryDSL(UUID idPedido) {
        return queryFactory
                .select(itemPedido.valorTotal.sum())
                .from(itemPedido)
                .join(itemPedido.produtoServico, produtoServico)
                .where(itemPedido.pedido.id.eq(idPedido)
                        .and(produtoServico.tipo.eq(TipoProdutoServicoEnum.fromInt(PRODUTO))))
                .fetchOne();
    }
}
