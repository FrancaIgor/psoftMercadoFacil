package com.ufcg.psoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.model.ProdutoVendido;

@Repository
public interface ProdutoVendidoRepository extends JpaRepository<ProdutoVendido, Long>{

}