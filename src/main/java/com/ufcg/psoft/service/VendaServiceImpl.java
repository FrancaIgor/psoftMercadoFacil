package com.ufcg.psoft.service;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.ufcg.psoft.util.GenericQuickSort;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.repository.LoteRepository;
import com.ufcg.psoft.repository.ProdutoRepository;

import com.ufcg.psoft.util.comparator.LoteDataDeValidadeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufcg.psoft.model.ProdutoVendido;
import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.repository.VendaRepository;
import com.ufcg.psoft.util.Mensagens;

@Service
public class VendaServiceImpl implements VendaService {
	
    @Autowired
    private VendaRepository vendaRepository;
	
    @Autowired
    private ProdutoVendidoService produtoVendidoService;

	@Autowired
    private LoteRepository loteRepository;

	@Autowired
    private ProdutoRepository produtoRepository;

	
    public Venda findById(long id) {
        if(isNull(id)) {
            throw new RuntimeException(Mensagens.ID_INVALIDO);
        }
        if(!vendaRepository.existsById(id)) {
            throw new RuntimeException(Mensagens.Venda.NAO_ENCONTRADA);
        }

        return vendaRepository.findById(id).get();
    }

    @Override
    public Venda save(Venda venda) {
        if(isNull(venda.getProdutosVendidos())) {
            throw new RuntimeException(Mensagens.Venda.NENHUM_PRODUTO_ENCONTRADO);
        }
        if(isNull(venda.getValor())) {
            throw new RuntimeException(Mensagens.Venda.Valor_NULO);
        }

        Venda vendaSalva = vendaRepository.save(venda);

        for(ProdutoVendido p : venda.getProdutosVendidos()) {
            p.setVenda(vendaSalva);
            produtoVendidoService.save(p);
        }

        return vendaSalva;
    }

    @Override
    public List<Venda> findAll() {
        Iterable<Venda> vendas = vendaRepository.findAll();
        List<Venda> vendasResult = new ArrayList<>();

        for(Venda v : vendas) {
            vendasResult.add(v);
        }
        if(vendasResult.isEmpty()){
            throw new RuntimeException(Mensagens.Venda.NENHUMA_ENCONTRADA);
        }

        return vendasResult;
    }

    @Override
    public boolean existsById(long id) {
        if(isNull(id)) {
            throw new RuntimeException(Mensagens.ID_INVALIDO);
        }
        if(!vendaRepository.existsById(id)) {
            throw new RuntimeException(Mensagens.Venda.NAO_ENCONTRADA);
        }
        return vendaRepository.existsById(id);
		
    }

    @Override
    public List<Venda> findAllByData() {
        Iterable<Venda> vendas = vendaRepository.findAll();
        List<Venda> vendasResult = new ArrayList<>();

        for (Venda v : vendas) {
            vendasResult.add(v);
        }
        if (vendasResult.isEmpty())
            throw new RuntimeException(Mensagens.Venda.NENHUMA_ENCONTRADA);

        GenericQuickSort quickSort = new GenericQuickSort();
        quickSort.quickSortVenda(vendasResult, 0, vendasResult.size() - 1);

        return vendasResult;
    }

    @Override
    public List<Venda> findAllByValor() {
        Iterable<Venda> vendas = vendaRepository.findAll();
        List<Venda> vendasResult = new ArrayList<>();

        for (Venda v : vendas) {
            vendasResult.add(v);
        }
        if (vendasResult.isEmpty())
            throw new RuntimeException(Mensagens.Venda.NENHUMA_ENCONTRADA);

        GenericQuickSort quickSort = new GenericQuickSort();
        quickSort.quickSortVendaValor(vendasResult, 0, vendasResult.size() - 1);

        return vendasResult;
    }

    @Override
    public void deleteById(long id) {
        if(isNull(id)) {
            throw new RuntimeException(Mensagens.ID_INVALIDO);
        }
        if(!vendaRepository.existsById(id)) {
            throw new RuntimeException(Mensagens.Venda.NAO_ENCONTRADA);
        }
        Venda venda = findById(id);
        List<ProdutoVendido> produtosVendidos = venda.getProdutosVendidos();

        for (ProdutoVendido produtoVendido : produtosVendidos) {
            adicionaQuantidadeCanceladaNosLotes(produtoVendido.getProduto(), produtoVendido.getQuantProdutos());
            disponibilizaProduto(produtoVendido.getProduto());
        }
    }

    private void disponibilizaProduto(Produto produto) {
        if (!produto.getDisponibilidade()) {
            produto.setDisponibilidade(true);
            produtoRepository.save(produto);
        }
    }

    private void adicionaQuantidadeCanceladaNosLotes(Produto produto, int quantProdutos) {
	    List<Lote> lotes = loteRepository.findAllByProduto(produto);
        // Dá prioridade para o lote que vai demorar a expirar a válidade
	    lotes.sort(Collections.reverseOrder(new LoteDataDeValidadeComparator()));
	    Lote loteMaiorValidade = lotes.get(0);
	    loteMaiorValidade.setNumeroDeItens(loteMaiorValidade.getNumeroDeItens() + quantProdutos);
	    loteRepository.save(loteMaiorValidade);
    }
}
