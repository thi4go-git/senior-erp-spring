package com.dynns.cloudtecnologia.senior.model.repository;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID> {
}
