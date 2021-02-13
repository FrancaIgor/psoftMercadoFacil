package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.Lote;

public interface LoteService {

	Lote findById(long id);

	Lote save(Lote lote);

	List<Lote> findAll();

	void deleteById(long id);
	
	void deleteQtdById(long id, int quantProdutos);
	
	List<Lote> findLotesDispByProdutoId(long produtoId);

    List<Lote> findAllHaVencer();
}
