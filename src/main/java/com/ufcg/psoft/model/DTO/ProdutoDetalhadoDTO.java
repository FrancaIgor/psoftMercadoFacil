package com.ufcg.psoft.model.DTO;

import java.math.BigDecimal;

public class ProdutoDetalhadoDTO {

    private BigDecimal preco;

    private boolean disponibilidade;

    public ProdutoDetalhadoDTO(BigDecimal preco, boolean disponibilidade) {
        this.preco = preco;
        this.disponibilidade = disponibilidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public boolean getDisponibilidade() {
        return disponibilidade;
    }
}
