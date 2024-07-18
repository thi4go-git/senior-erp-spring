package com.dynns.cloudtecnologia.senior.model.repository;

import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
