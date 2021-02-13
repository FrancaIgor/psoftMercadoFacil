package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.Venda;

public interface VendaService {
	
	Venda findById(long id);

	Venda save(Venda venda);

	List<Venda> findAll();

	boolean existsById(long id);

	List<Venda> findAllByData();

    List<Venda> findAllByValor();

    void deleteById(long id);

}
