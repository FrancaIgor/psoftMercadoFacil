package com.ufcg.psoft.service;


import java.util.List;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.Produto;

public interface ProdutoService {

    Produto findById(long id);

    Produto save(Produto produto);

    void deleteById(long id);

    List<Produto> findAll();

    List<Produto> findAllByNome();

    List<Produto> findAllByFabricante();

    List<Produto> findAllByCategoria();

    List<Produto> findAllByPreco();
    
    boolean existsByCodigoBarra(String codigoBarra);
	
	List<Produto> findAllByDisponibilidade(boolean disponibilidade);

	Produto findVencido(long id);

    List<Produto> findAllEmBaixa();

    List<Produto> findProdutoByCategoria(String categoria);

    List<Produto> findProdutoByNome(String nome);
}
