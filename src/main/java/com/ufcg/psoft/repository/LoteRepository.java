package com.ufcg.psoft.repository;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

    List<Lote> findAllByProduto(Produto produto);

}
