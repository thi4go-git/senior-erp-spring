package com.dynns.cloudtecnologia.senior.rest.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.dynns.cloudtecnologia.senior.model.enums.SituacaoPedidoEnum;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.DescontoDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoFilterDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoNewDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.pedido.PedidoUpdateDTO;
import com.dynns.cloudtecnologia.senior.rest.dto.produtoservico.ProdutoServicoNewDTO;
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
class PedidoControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    private static final Logger LOG = LoggerFactory.getLogger(PedidoControllerTest.class);
    private static final String URL_PATH = "api/pedidos";
    private static final String ID_PEDIDO_INEXISTENTE = "d4ed4b51-7e5d-4777-bad7-5d01fbb7f550";
    private static String idServicoSalvo;
    private static String idProdutoSalvo;
    private static String idPedidoSalvo;
    private static final String CAMPO_ID = "id";
    private static final String CAMPO_DESCRICAO = "descricao";
    private static final String CAMPO_TIPO = "tipo";
    private static final String CAMPO_SITUACAO = "situacao";
    private static final String CAMPO_PERC_DESC = "percentualDesconto";
    private static final String NOME_PEDIDO = "Pedido TESTE";
    private static final String NOME_TOTAL_BRUTO = "totalBruto";
    private static final String NOME_TOTAL_LIQ = "totalLiquido";
    private static final String MSG_PEDIDO_NOTFOUND = "Pedido com Id UUID não localizado: ";
    private static final String TOT_BRUTO = "4023.24";


    @ParameterizedTest
    @MethodSource("getProdutoServicoParaTeste")
    @DisplayName("Deve cadastrar 1 Serviço e 1 Produto")
    @Order(1)
    void deveCadastrarUmServicoProduto(ProdutoServicoNewDTO dto, int indexProdServ) {
        dto.setDescricao(dto.getDescricao() + " - 2");
        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("api/produtos-servicos")
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);

        if (indexProdServ == 1) {
            assertEquals(HttpStatus.SC_CREATED, resposta.statusCode());
            assertEquals(dto.getTipo(), resposta.jsonPath().getString(CAMPO_TIPO));
            assertEquals(dto.getDescricao(), resposta.jsonPath().getString(CAMPO_DESCRICAO));
            assertEquals(dto.getPreco().toString(), resposta.jsonPath().getString("preco"));
            idServicoSalvo = resposta.jsonPath().getString(CAMPO_ID).trim();
        } else if (indexProdServ == 2) {
            assertEquals(HttpStatus.SC_CREATED, resposta.statusCode());
            assertEquals(dto.getTipo(), resposta.jsonPath().getString(CAMPO_TIPO));
            assertEquals(dto.getDescricao(), resposta.jsonPath().getString(CAMPO_DESCRICAO));
            idProdutoSalvo = resposta.jsonPath().getString(CAMPO_ID).trim();
        }
    }

    @ParameterizedTest
    @MethodSource("getPedidosParaTeste")
    @DisplayName("Testar Cadastro de Pedidos")
    @Order(2)
    void testarCadastroDePedidos(PedidoNewDTO dto, int index) {
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
        if (index == 1) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());
            assertTrue(responseBody.contains("O Campo descricao é obrigatório!"));
            assertTrue(responseBody.contains("A Lista itensPedido deve ser preenchida!"));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_CREATED, resposta.statusCode());
            assertEquals(SituacaoPedidoEnum.ABERTO.toString(), resposta.jsonPath().getString(CAMPO_SITUACAO));
            assertEquals(NOME_PEDIDO, resposta.jsonPath().getString(CAMPO_DESCRICAO));
            assertEquals("0", resposta.jsonPath().getString(CAMPO_PERC_DESC));
            assertEquals(TOT_BRUTO, resposta.jsonPath().getString(NOME_TOTAL_BRUTO));
            assertEquals(TOT_BRUTO, resposta.jsonPath().getString(NOME_TOTAL_LIQ));
            idPedidoSalvo = resposta.jsonPath().getString(CAMPO_ID).trim();
        }
    }

    @Test
    @DisplayName("Deve listar todos os Pedidos")
    @Order(3)
    void deveListarTodosPedidos() {
        var resposta = given()
                .contentType(ContentType.JSON)
                .body(PedidoFilterDTO.builder().build())
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
    }

    @ParameterizedTest
    @MethodSource("getIdsPedidosParaTeste")
    @DisplayName("Deve listar Pedido pelo ID")
    @Order(4)
    void deveListarPedidoPeloId(String idPedido, int index) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL_PATH + "/" + idPedido)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_PEDIDO_NOTFOUND + ID_PEDIDO_INEXISTENTE));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
            assertEquals(idPedidoSalvo, resposta.jsonPath().getString(CAMPO_ID));
            assertEquals(SituacaoPedidoEnum.ABERTO.toString(), resposta.jsonPath().getString(CAMPO_SITUACAO));
            assertEquals(NOME_PEDIDO, resposta.jsonPath().getString(CAMPO_DESCRICAO));
            assertEquals("0", resposta.jsonPath().getString(CAMPO_PERC_DESC));
            assertEquals(TOT_BRUTO, resposta.jsonPath().getString(NOME_TOTAL_BRUTO));
            assertEquals(TOT_BRUTO, resposta.jsonPath().getString(NOME_TOTAL_LIQ));
        }
    }

    @ParameterizedTest
    @MethodSource("getIdsPedidosParaTeste")
    @DisplayName("Deve listar Itens do Pedido")
    @Order(5)
    void deveListarItensPedido(String idPedido, int index) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL_PATH + "/" + idPedido + "/itens")
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_PEDIDO_NOTFOUND + ID_PEDIDO_INEXISTENTE));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
        }
    }

    @ParameterizedTest
    @MethodSource("getIdsPedidosParaTeste")
    @DisplayName("Deve Fechar Pedido")
    @Order(6)
    void deveFecharPedido(String idPedido, int index) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .patch(URL_PATH + "/" + idPedido)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_PEDIDO_NOTFOUND + ID_PEDIDO_INEXISTENTE));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
            assertEquals(idPedidoSalvo, resposta.jsonPath().getString(CAMPO_ID));
            assertEquals(SituacaoPedidoEnum.FECHADO.toString(), resposta.jsonPath().getString(CAMPO_SITUACAO));
            assertEquals(NOME_PEDIDO, resposta.jsonPath().getString(CAMPO_DESCRICAO));
            assertEquals("0", resposta.jsonPath().getString(CAMPO_PERC_DESC));
            assertEquals(TOT_BRUTO, resposta.jsonPath().getString(NOME_TOTAL_BRUTO));
            assertEquals(TOT_BRUTO, resposta.jsonPath().getString(NOME_TOTAL_LIQ));
        }
    }

    @ParameterizedTest
    @MethodSource("getIdsPedidosParaTeste")
    @DisplayName("Não deve Aplicar desconto em pedido Fechado")
    @Order(7)
    void naoDeveAplicarDescontoEmPedidoFechado(String idPedido, int index) {
        DescontoDTO dtoDesconto = DescontoDTO.builder().percentualDesconto(new BigDecimal("13.64")).build();
        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dtoDesconto)
                .when()
                .post(URL_PATH + "/" + idPedido + "/desconto")
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_PEDIDO_NOTFOUND + ID_PEDIDO_INEXISTENTE));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());
            assertTrue(responseBody.contains("400 BAD_REQUEST"));
            assertTrue(responseBody.contains("Não foi possível aplicar desconto. Causa: o pedido já está fechado."));
        }
    }

    @ParameterizedTest
    @MethodSource("getIdsPedidosParaTeste")
    @DisplayName("Deve atualizar o Pedido")
    @Order(8)
    void deveAtualizarPedido(String idPedido, int index) {
        PedidoUpdateDTO updateDto = PedidoUpdateDTO.builder()
                .situacao(SituacaoPedidoEnum.ABERTO.toString())
                .descricao(NOME_PEDIDO + " - UPDATE DESCONTO")
                .build();
        var resposta = given()
                .contentType(ContentType.JSON)
                .body(updateDto)
                .when()
                .put(URL_PATH + "/" + idPedido)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_PEDIDO_NOTFOUND + ID_PEDIDO_INEXISTENTE));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
            assertEquals(idPedidoSalvo, resposta.jsonPath().getString(CAMPO_ID));
            assertEquals(SituacaoPedidoEnum.ABERTO.toString(), resposta.jsonPath().getString(CAMPO_SITUACAO));
            assertEquals(updateDto.getDescricao(), resposta.jsonPath().getString(CAMPO_DESCRICAO));
            assertEquals("0", resposta.jsonPath().getString(CAMPO_PERC_DESC));
            assertEquals(TOT_BRUTO, resposta.jsonPath().getString(NOME_TOTAL_BRUTO));
            assertEquals(TOT_BRUTO, resposta.jsonPath().getString(NOME_TOTAL_LIQ));
        }
    }


    @ParameterizedTest
    @MethodSource("getIdsPedidosParaTeste")
    @DisplayName("Deve Aplicar desconto no PEDIDO")
    @Order(9)
    void deveAplicarDescontoNoPedido(String idPedido, int index) {
        String percDesconto = "19.64";
        DescontoDTO dtoDesconto = DescontoDTO.builder().percentualDesconto(new BigDecimal(percDesconto)).build();
        var resposta = given()
                .contentType(ContentType.JSON)
                .body(dtoDesconto)
                .when()
                .post(URL_PATH + "/" + idPedido + "/desconto")
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_PEDIDO_NOTFOUND + ID_PEDIDO_INEXISTENTE));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_OK, resposta.statusCode());
            assertEquals(idPedidoSalvo, resposta.jsonPath().getString(CAMPO_ID));
            assertEquals(SituacaoPedidoEnum.ABERTO.toString(), resposta.jsonPath().getString(CAMPO_SITUACAO));
            assertEquals(TOT_BRUTO, resposta.jsonPath().getString(NOME_TOTAL_BRUTO));
            assertEquals("4.91", resposta.jsonPath().getString("totalDescontos"));
            assertEquals("4018.33", resposta.jsonPath().getString(NOME_TOTAL_LIQ));
        } else if (index == 3) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, resposta.statusCode());
            assertTrue(responseBody.contains("Não foi possível aplicar desconto. Causa: Desconto já aplicado de " + percDesconto + "%"));
        }
    }

    @ParameterizedTest
    @MethodSource("getIdsPedidosParaTeste")
    @DisplayName("Deve deletar o Pedido pelo ID")
    @Order(10)
    void deveDeletarPedidoPeloId(String idPedido, int index) {
        var resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL_PATH + "/" + idPedido)
                .then()
                .extract()
                .response();
        String responseBody = resposta.getBody().asString();
        LOG.info(responseBody);
        assertNotNull(responseBody);
        if (index == 1) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_PEDIDO_NOTFOUND + ID_PEDIDO_INEXISTENTE));
        } else if (index == 2) {
            assertEquals(HttpStatus.SC_NO_CONTENT, resposta.statusCode());
        } else if (index == 3) {
            assertEquals(HttpStatus.SC_NOT_FOUND, resposta.statusCode());
            assertTrue(responseBody.contains(MSG_PEDIDO_NOTFOUND + idPedidoSalvo));
        }
    }

    static Stream<Arguments> getProdutoServicoParaTeste() {
        return Stream.of(
                Arguments.of(PedidosSource.getServicoValido(), 1),
                Arguments.of(PedidosSource.getProdutoValido(), 2)
        );
    }

    static Stream<Arguments> getPedidosParaTeste() {
        return Stream.of(
                Arguments.of(PedidosSource.getPedidoDadosIncorretos(), 1),
                Arguments.of(PedidosSource.getPedidoDadosCorretdos(idServicoSalvo, idProdutoSalvo, 2, NOME_PEDIDO), 2)
        );
    }

    static Stream<Arguments> getIdsPedidosParaTeste() {
        return Stream.of(
                Arguments.of(ID_PEDIDO_INEXISTENTE, 1),
                Arguments.of(idPedidoSalvo, 2),
                Arguments.of(idPedidoSalvo, 3)
        );
    }
}