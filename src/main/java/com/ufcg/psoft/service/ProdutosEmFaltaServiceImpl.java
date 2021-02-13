package com.ufcg.psoft.service;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.ProdutosEmFalta;
import com.ufcg.psoft.repository.ProdutosEmFaltaRepository;
import com.ufcg.psoft.repository.ProdutoRepository;

@Service
public class ProdutosEmFaltaServiceImpl implements ProdutosEmFaltaService {

    @Autowired
    private ProdutosEmFaltaRepository produtoEmFaltaRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Override
    public ProdutosEmFalta findById(long id) {
        if(isNull(id)) {
            throw new NullPointerException("ID nulo de produto em falta.");
        }
        if(!produtoEmFaltaRepository.existsById(id)) {
            throw new RuntimeException("Produto Vendido nao encontrado.");
        }

        return produtoEmFaltaRepository.findById(id).get();
    }

    @Override
    public ProdutosEmFalta save(ProdutosEmFalta produtosEmFalta) {
        if(isNull(produtosEmFalta)) {
            throw new NullPointerException("Produto em falta nulo.");
        }
        if(isNull(produtosEmFalta.getProdutos())) {
            throw new NullPointerException("Quantidade nula de produtos.");
        }

       return produtoEmFaltaRepository.save(produtosEmFalta);
    }

    @Override
    public List<ProdutosEmFalta> findAll() {
        Iterable<ProdutosEmFalta> produtosEmFalta = produtoEmFaltaRepository.findAll();
        List<ProdutosEmFalta> produtosEmFaltaResult = new ArrayList<>();

        for(ProdutosEmFalta p : produtosEmFalta) {
            produtosEmFaltaResult.add(p);
        }

        return produtosEmFaltaResult;
    }
    
    @Override
    public ProdutosEmFalta insert(){
        Iterable<Produto> produtos = produtoRepository.findAllByDisponibilidade(false);
        ProdutosEmFalta resultadoProdutos = new ProdutosEmFalta();

        for(Produto p : produtos){
            resultadoProdutos.add(p);
        }

        return resultadoProdutos;
    }



}