package com.ufcg.psoft.service;

import com.ufcg.psoft.model.Categoria;

import java.util.List;

/**
 *
 * @author Igor Franca
 */

public interface CategoriaService {

    boolean existsById(Long id);

    Categoria findById(Long id);

    Categoria save(Categoria category);

    List<Categoria> findAll();

    void deleteById(Long id);
}