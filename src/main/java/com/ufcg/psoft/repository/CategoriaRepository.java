package com.ufcg.psoft.repository;

import com.ufcg.psoft.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Igor Franca
 */
@Repository
public interface CategoriaRepository extends JpaRepository <Categoria, Long>{

}
