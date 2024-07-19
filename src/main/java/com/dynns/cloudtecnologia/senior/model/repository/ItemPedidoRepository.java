package com.dynns.cloudtecnologia.senior.model.repository;

import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {
    List<ItemPedido> findByPedido(Pedido pedido);

    List<ItemPedido> findByProdutoServico(ProdutoServico prodServ);


    @Query(value =
            "SELECT " +
                    " SUM(item.valor_total) as valor_bruto_produtos " +
                    "FROM " +
                    " item_pedido as item, " +
                    " produto_servico AS ps " +
                    "WHERE " +
                    " item.id_pedido = :idPedido " +
                    "AND " +
                    " item.id_produto_servico = ps.id " +
                    "AND " +
                    " ps.tipo = 0",
            nativeQuery = true)
    BigDecimal getSomaValorBrutoItensProdutos(@Param("idPedido") UUID idPedido);
}
