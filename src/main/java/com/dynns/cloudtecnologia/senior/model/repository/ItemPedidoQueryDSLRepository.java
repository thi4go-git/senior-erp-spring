package com.dynns.cloudtecnologia.senior.model.repository;

import java.math.BigDecimal;
import java.util.UUID;

public interface ItemPedidoQueryDSLRepository {
    BigDecimal getSomaValorBrutoItensProdutosQueryDSL(UUID idPedido);
}
