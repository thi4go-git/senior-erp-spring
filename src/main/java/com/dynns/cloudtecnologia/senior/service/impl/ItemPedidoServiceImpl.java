package com.dynns.cloudtecnologia.senior.service.impl;

import com.dynns.cloudtecnologia.senior.exception.GeralException;
import com.dynns.cloudtecnologia.senior.model.entity.ItemPedido;
import com.dynns.cloudtecnologia.senior.model.entity.Pedido;
import com.dynns.cloudtecnologia.senior.model.entity.ProdutoServico;
import com.dynns.cloudtecnologia.senior.model.enums.AtivoEnum;
import com.dynns.cloudtecnologia.senior.model.repository.ItemPedidoRepository;
import com.dynns.cloudtecnologia.senior.rest.dto.item_pedido.ItemPedidoNewDTO;
import com.dynns.cloudtecnologia.senior.service.ItemPedidoService;
import com.dynns.cloudtecnologia.senior.utils.SeniorErpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Autowired
    private ProdutoServicoServiceImpl produtoServicoService;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;


    @Override
    public void save(Pedido pedidoSalvo, List<ItemPedidoNewDTO> itensPedido) {
        List<ProdutoServico> produtosServicosList = produtoServicoService.findAll();
        for (ItemPedidoNewDTO item : itensPedido) {
            UUID idSanitizado = SeniorErpUtil.retornarUUIDSanitizado(item.getIdProdutoServico().trim());

            ProdutoServico produtoServico =
                    produtosServicosList.stream()
                            .filter(produto -> produto.getId().equals(idSanitizado))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Produto com ID UUID: " + item.getIdProdutoServico().trim() + " não encontrado! "));

            if (produtoServico.getAtivo().equals(AtivoEnum.N)) {
                throw new GeralException("Não foi possível adicionar o Produto/Serviço '" + produtoServico.getDescricao() +
                        "' ao pedido. Causa: Produto/Serviço inativo.");
            }

            BigDecimal precoProduto = produtoServico.getPreco();
            BigDecimal quantidade = SeniorErpUtil.intToBigDecimal(item.getQtde());
            BigDecimal valorTotal = precoProduto.multiply(quantidade);

            ItemPedido itemSave = ItemPedido.builder()
                    .pedido(pedidoSalvo)
                    .produtoServico(produtoServico)
                    .qtde(item.getQtde())
                    .valorTotal(SeniorErpUtil.ajustarDuasCasasDecimais(valorTotal))
                    .build();

            itemPedidoRepository.save(itemSave);
        }
    }

    @Override
    public List<ItemPedido> findByPedido(Pedido pedido) {
        return itemPedidoRepository.findByPedido(pedido);
    }

    @Override
    public BigDecimal getSomaValorBrutoItensProdutos(UUID idPedido) {
        return itemPedidoRepository.getSomaValorBrutoItensProdutos(idPedido);
    }
}
