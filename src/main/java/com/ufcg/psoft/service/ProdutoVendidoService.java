package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.ProdutoVendido;

public interface ProdutoVendidoService {
	
	ProdutoVendido findById(long id);
	ProdutoVendido save(ProdutoVendido produtoVendido);
	List<ProdutoVendido> findAll();

}
