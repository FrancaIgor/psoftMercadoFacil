package com.ufcg.psoft.service;

import com.ufcg.psoft.model.DTO.ProdutoDTO;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.Relatorio;
import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.repository.LoteRepository;
import com.ufcg.psoft.repository.ProdutoRepository;
import com.ufcg.psoft.repository.VendaRepository;
import com.ufcg.psoft.util.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioServiceImpl implements RelatorioService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Override
    public Relatorio make() {
        List<ProdutoDTO> produtos = getProdutosDTO();
        List<Venda> vendas = vendaRepository.findAll();
        BigDecimal receita = getReceita(vendas);
        return new Relatorio(produtos, vendas, receita);
    }

    private BigDecimal getReceita(List<Venda> vendas) {
        BigDecimal receitaFinal = new BigDecimal(0);
        for (Venda venda : vendas) {
            receitaFinal = receitaFinal.add(new BigDecimal(venda.getValor()));
        }
        return receitaFinal;
    }

    private List<ProdutoDTO> getProdutosDTO() {
        List<ProdutoDTO> produtos = new ArrayList<ProdutoDTO>();
        List<Produto> todosProdutos = produtoRepository.findAll();
        if (todosProdutos.isEmpty()) {
            throw new RuntimeException(Mensagens.Produto.NENHUM_ENCONTRADO);
        }
        for (Produto produto : todosProdutos) {
            int numeroItens = 0;
            List<Lote> lotes = loteRepository.findAllByProduto(produto);
            List<String> datasDeValidade = new ArrayList<String>();
            for (Lote lote : lotes) {
                numeroItens += lote.getNumeroDeItens();
                datasDeValidade.add(lote.getDataDeValidade());
            }
            produtos.add(new ProdutoDTO(produto.getId(), lotes.size(), numeroItens, datasDeValidade));
        }
        return produtos;
    }
}
