package com.ufcg.psoft.service;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.ProdutoVendido;
import com.ufcg.psoft.repository.ProdutoVendidoRepository;

@Service
public class ProdutoVendidoServiceImpl implements ProdutoVendidoService {

    @Autowired
    private ProdutoVendidoRepository produtoVendidoRepository;
	
    @Autowired
    private LoteService loteService;
	
    public ProdutoVendido findById(long id) {

        if(isNull(id)) {
            throw new NullPointerException("ID nulo de produto vendido.");
        }
        if(!produtoVendidoRepository.existsById(id)) {
            throw new RuntimeException("Produto Vendido nao encontrado.");
        }

        return produtoVendidoRepository.findById(id).get();
    }

	
    public ProdutoVendido save(ProdutoVendido produtoVendido) {

        if(isNull(produtoVendido)) {
            throw new NullPointerException("Produto vendido nulo.");
        }
        if(isNull(produtoVendido.getQuantProdutos())) {
            throw new NullPointerException("Quantidade nula de produtos.");
        }
        if(isNull(produtoVendido.getVenda())) {
            throw new NullPointerException("Venda nula.");
        }	
        
        return produtoVendidoRepository.save(produtoVendido);
        	
    }

	
    public List<ProdutoVendido> findAll() {
        Iterable<ProdutoVendido> produtosVendidos = produtoVendidoRepository.findAll();
        List<ProdutoVendido> produtosVendidosResult = new ArrayList<>();

        for(ProdutoVendido p : produtosVendidos) {
            produtosVendidosResult.add(p);
        }

        return produtosVendidosResult;
    }

}

