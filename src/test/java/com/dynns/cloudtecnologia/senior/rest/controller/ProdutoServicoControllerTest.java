package com.dynns.cloudtecnologia.senior.rest.controller;

import static com.dynns.cloudtecnologia.senior.rest.controller.ProdutoServicoSourcesTest.getProdutoServicoDadosIncorretos;
import static com.dynns.cloudtecnologia.senior.rest.controller.ProdutoServicoSourcesTest.getProdutoValido;
import static org.junit.jupiter.api.Assertions.*;

import com.dynns.cloudtecnologia.senior.model.enums.AtivoEnum;
import com.dynns.cloudtecnologia.senior.model.enums.TipoProdutoServicoEnum;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoUpdateDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
class ProdutoServicoControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    private static final Logger LOG = LoggerFactory.getLogger(ProdutoServicoControllerTest.class);
    private static final String URL_PATH = "api/produtos-servicos";
    private static final String ID_PRODSERV_INEXISTENTE = "aabd86c4-aaaa-ss2d-a375-ddc5d1dd98hh";
    private static final String MSG_ID_INVALIDO = "Id UUID inválido: ";
    private static final String DESCRICAO_OBRIGATORIA = "O Campo descricao é obrigatório!";
    private static final String TIPO_ENUM_INVALIDO = "O campo tipo não contém um TipoEnum válido = PRODUTO ou SERVICO.";
    private static final String PRODUTO = "PRODUTO";
    private static final String CORDA_9MM = "Corda 9mm";
    private static final String CORDA_9MM_UPDATE = CORDA_9MM + " - UPDATE";
    private static final String PRECO_12_99 = "12.99";
    private static final String ATIVO_N = "N";
    private static final String ATIVO_S = "S";
    private static String idProdutoServicoSalvo;
    private static final String CAMPO_ID = "id";
    private static final String CAMPO_ATIVO = "ativo";
    private static final String CAMPO_DESCRICAO = "descricao";
    private static final String CAMPO_PRECO = "preco";
    private static final String CAMPO_TIPO = "tipo";


    @ParameterizedTest
    @MethodSource("getProdutoServicoParaTeste")
    @DisplayName("Testar Cadastro de Produtos/Serviços")
    @Order(1)
    void testarCadastroDeProdutoServico(ProdutoServicoNewDTO dto, int indexProdServ) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(URL_PATH)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (indexProdServ == 1) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());
            assertTrue(responseBody.contains(DESCRICAO_OBRIGATORIA));
            assertTrue(responseBody.contains(TIPO_ENUM_INVALIDO));
            assertTrue(responseBody.contains("O Campo preco deve conter um valor >= 1!"));
        } else if (indexProdServ == 2) {
            idProdutoServicoSalvo = resposta.jsonPath().getString(CAMPO_ID).trim();
            assertEquals(HttpStatus.SC_CREATED, resposta.statusCode());
            assertEquals(PRODUTO, resposta.jsonPath().getString(CAMPO_TIPO));
            assertEquals(CORDA_9MM, resposta.jsonPath().getString(CAMPO_DESCRICAO));
            assertEquals("12.5", resposta.jsonPath().getString(CAMPO_PRECO));
            assertEquals(ATIVO_S, resposta.jsonPath().getString(CAMPO_ATIVO));
        }
    }

    @Test
    @DisplayName("Deve listar todos Produtos/Serviços")
    @Order(2)
    void deveListarTodosProdutosServicos() {
        var resposta = given()
                .contentType(ContentType.JSON)
                .body(ProdutoServicoFilterDTO.builder().build())
                .when()
                .post(URL_PATH.concat("/show"))
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        assertEquals(HttpStatus.SC_OK, resposta.statusCode());
        assertTrue(responseBody.contains("\"empty\":false"));
        assertTrue(responseBody.contains("\"pageSize\":10"));
        assertTrue(responseBody.contains("\"pageNumber\":0"));
        assertTrue(responseBody.contains("\"totalElements\":1"));
        assertTrue(responseBody.contains("\"numberOfElements\":1"));
    }

    @ParameterizedTest
    @MethodSource("getIdsProdutoServicoParaTeste")
    @DisplayName("Testar Atualização de Produto/Serviço")
    @Order(3)
    void deveAtualizarProdutoServico(String idProdServ, int index) {
        ProdutoServicoUpdateDTO prodServUpdateValido =
                ProdutoServicoUpdateDTO.builder()
                        .tipo(TipoProdutoServicoEnum.SERVICO.toString())
                        .descricao(CORDA_9MM_UPDATE)
                        .preco(new BigDecimal(PRECO_12_99))
                        .ativo(AtivoEnum.N.toString())
                        .build();
        ProdutoServicoUpdateDTO dtoupdate;
        if (index == 1) {
            dtoupdate = ProdutoServicoUpdateDTO.builder().build();
        } else {
            dtoupdate = prodServUpdateValido;
        }
        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dtoupdate)
                .when()
                .put(URL_PATH.concat("/").concat(idProdServ))
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());
            assertTrue(responseBody.contains("O Campo ativo é obrigatório!"));
            assertTrue(responseBody.contains(TIPO_ENUM_INVALIDO));
            assertTrue(responseBody.contains(DESCRICAO_OBRIGATORIA));
            assertTrue(responseBody.contains("O Campo preco é obrigatório!"));
            assertTrue(responseBody.contains("O Campo tipo é obrigatório!"));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_ID_INVALIDO + ID_PRODSERV_INEXISTENTE));
        } else if (index == 3) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
            assertEquals(idProdutoServicoSalvo, resposta.jsonPath().getString(CAMPO_ID));
            assertEquals("SERVICO", resposta.jsonPath().getString(CAMPO_TIPO));
            assertEquals(CORDA_9MM_UPDATE, resposta.jsonPath().getString(CAMPO_DESCRICAO));
            assertEquals(PRECO_12_99, resposta.jsonPath().getString(CAMPO_PRECO));
            assertEquals(ATIVO_N, resposta.jsonPath().getString(CAMPO_ATIVO));
        }
    }


    @ParameterizedTest
    @MethodSource("getIdsProdutoServicoParaTeste")
    @DisplayName("Testar Ativação do Produto/Serviço")
    @Order(4)
    void deveAtivarDesativarProdutoServico(String idProdServ, int index) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .patch(URL_PATH + "/" + idProdServ + "/ativar-desativar")
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
            assertEquals(ATIVO_S, resposta.jsonPath().getString(CAMPO_ATIVO));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_ID_INVALIDO + ID_PRODSERV_INEXISTENTE));
        } else if (index == 3) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
            assertEquals(ATIVO_N, resposta.jsonPath().getString(CAMPO_ATIVO));
        }
    }

    @ParameterizedTest
    @MethodSource("getIdsProdutoServicoParaTeste")
    @DisplayName("Testar Exclusão do Produto/Serviço")
    @Order(5)
    void deveExcluirProdutoServico(String idProdServ, int index) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL_PATH + "/" + idProdServ).then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NO_CONTENT, resposta.statusCode());
        } else if (index == 2) {
            assertNotNull(responseBody);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_ID_INVALIDO + ID_PRODSERV_INEXISTENTE));
        } else if (index == 3) {
            assertNotNull(responseBody);
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains("ProdutoServico com Id UUID não localizado: " + idProdServ));
        }
    }

    private static Stream<Arguments> getProdutoServicoParaTeste() {
        return Stream.of(
                Arguments.of(getProdutoServicoDadosIncorretos(), 1),
                Arguments.of(getProdutoValido(), 2)
        );
    }

    private static Stream<Arguments> getIdsProdutoServicoParaTeste() {
        return Stream.of(
                Arguments.of(idProdutoServicoSalvo, 1),
                Arguments.of(ID_PRODSERV_INEXISTENTE, 2),
                Arguments.of(idProdutoServicoSalvo, 3)
        );
    }


}