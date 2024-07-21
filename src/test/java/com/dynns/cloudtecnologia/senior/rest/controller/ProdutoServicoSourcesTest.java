package com.dynns.cloudtecnologia.senior.rest.controller;

import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoNewDTO;

import java.math.BigDecimal;

public abstract class ProdutoServicoSourcesTest {
    private ProdutoServicoSourcesTest() {
    }


    public static ProdutoServicoNewDTO getProdutoServicoDadosIncorretos() {
        return ProdutoServicoNewDTO
                .builder()
                .tipo("TIPO")
                .descricao("")
                .preco(new BigDecimal(0))
                .build();
    }

    public static ProdutoServicoNewDTO getProdutoValido() {
        return ProdutoServicoNewDTO
                .builder()
                .tipo("PRODUTO")
                .descricao("Corda 9mm")
                .preco(new BigDecimal("12.50"))
                .build();
    }
}
