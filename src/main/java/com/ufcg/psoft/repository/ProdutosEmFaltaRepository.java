package com.ufcg.psoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.model.ProdutosEmFalta;

@Repository
public interface ProdutosEmFaltaRepository extends JpaRepository<ProdutosEmFalta, Long>{

}