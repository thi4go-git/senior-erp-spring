package com.dynns.cloudtecnologia.senior.model.repository;

import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID> {
    Optional<ProdutoServico> findByDescricao(String descricao);

    Optional<ProdutoServico> findById(UUID id);
}
