package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.ProdutosEmFalta;

public interface ProdutosEmFaltaService {

    ProdutosEmFalta findById(long id);
    ProdutosEmFalta save(ProdutosEmFalta produtoEmFalta);
    List<ProdutosEmFalta> findAll();
	ProdutosEmFalta insert();

}