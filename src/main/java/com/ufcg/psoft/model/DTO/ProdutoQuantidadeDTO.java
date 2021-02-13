package com.ufcg.psoft.model.DTO;

import com.ufcg.psoft.model.Produto;

public class ProdutoQuantidadeDTO {
	private int quantidade;
	private Produto produto;

	public ProdutoQuantidadeDTO() {
    }

    public ProdutoQuantidadeDTO(Produto produto, int quantidade) {
        super();
        this.produto = produto;
        this.quantidade = quantidade;
       
    }

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

    public void incrementaQuantd(int quantidade) {
    	this.setQuantidade(this.getQuantidade() + quantidade);
    }

}
