package com.ufcg.psoft.model;

import com.ufcg.psoft.model.DTO.ProdutoDTO;

import java.math.BigDecimal;
import java.util.List;

public class Relatorio {

    private List<ProdutoDTO> produtos;

    private List<Venda> vendas;

    private BigDecimal receita;

    public Relatorio(List<ProdutoDTO> produtos, List<Venda> vendas, BigDecimal receita) {
        this.produtos = produtos;
        this.vendas = vendas;
        this.receita = receita;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public BigDecimal getReceita() {
        return receita;
    }

    public void setReceita(BigDecimal receita) {
        this.receita = receita;
    }
}
