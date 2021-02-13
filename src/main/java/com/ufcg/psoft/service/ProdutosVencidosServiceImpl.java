package com.ufcg.psoft.service;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.ProdutosVencidos;
import com.ufcg.psoft.repository.ProdutosVencidosRepository;
import com.ufcg.psoft.repository.ProdutoRepository;

@Service
public class ProdutosVencidosServiceImpl implements ProdutosVencidosService {

    @Autowired
    private ProdutosVencidosRepository produtoVencidosRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Override
    public ProdutosVencidos findById(long id) {
        if(isNull(id)) {
            throw new NullPointerException("ID nulo de produto vencido.");
        }
        if(!produtoVencidosRepository.existsById(id)) {
            throw new RuntimeException("Produto vencido nao encontrado.");
        }

        return produtoVencidosRepository.findById(id).get();
    }

    @Override
    public ProdutosVencidos save(ProdutosVencidos produtosVencidos) {
        if(isNull(produtosVencidos)) {
            throw new NullPointerException("Produto vencido nulo.");
        }
        if(isNull(produtosVencidos.getProdutos())) {
            throw new NullPointerException("Quantidade nula de produtos.");
        }

       return produtoVencidosRepository.save(produtosVencidos);
    }

    @Override
    public List<ProdutosVencidos> findAll() {
        Iterable<ProdutosVencidos> produtosVencidos = produtoVencidosRepository.findAll();
        List<ProdutosVencidos> produtosVencidosResult = new ArrayList<>();

        for(ProdutosVencidos p : produtosVencidos) {
            produtosVencidosResult.add(p);
        }

        return produtosVencidosResult;
    }
    
    @Override
    public ProdutosVencidos insert(){
        Iterable<Produto> produtos = produtoRepository.findAllByDisponibilidade(false);
        ProdutosVencidos resultadoProdutos = new ProdutosVencidos();

        for(Produto p : produtos){
            resultadoProdutos.add(p);
        }

        return resultadoProdutos;
    }



}