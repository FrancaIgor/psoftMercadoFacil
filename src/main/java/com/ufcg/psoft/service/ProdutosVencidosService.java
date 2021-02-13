package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.ProdutosVencidos;

public interface ProdutosVencidosService {

    ProdutosVencidos findById(long id);
    ProdutosVencidos save(ProdutosVencidos produtoVencidos);
    List<ProdutosVencidos> findAll();
	ProdutosVencidos insert();

}