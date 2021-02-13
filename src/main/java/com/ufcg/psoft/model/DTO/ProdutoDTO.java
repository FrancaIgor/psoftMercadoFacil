package com.ufcg.psoft.model.DTO;

import java.util.List;

public class ProdutoDTO {

    private long id;

    private int numeroLotes;

    private int numeroItens;

    private List<String> datasDeValidade;

    public ProdutoDTO(long id, int numeroLotes, int numeroItens, List<String> datasDeValidade) {
        this.id = id;
        this.numeroLotes = numeroLotes;
        this.numeroItens = numeroItens;
        this.datasDeValidade = datasDeValidade;
    }

    public long getId() {
        return id;
    }

    public int getNumeroLotes() {
        return numeroLotes;
    }

    public int getNumeroItens() {
        return numeroItens;
    }

    public List<String> getDatasDeValidade() {
        return datasDeValidade;
    }
}
