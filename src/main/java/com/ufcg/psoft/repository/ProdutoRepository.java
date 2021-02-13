package com.ufcg.psoft.repository;

import com.ufcg.psoft.model.Produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCodigoBarra(String codigoBarra);

    Produto findByCodigoBarra(String codigoBarra);

    List<Produto> findAllByDisponibilidade(boolean disponibilidade);

    List<Produto> findAllByNome(String nome);

    List<Produto> findAllByFabricante(String fabricante);

}
