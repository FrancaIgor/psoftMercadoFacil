package com.ufcg.psoft.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class ProdutosEmFalta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToMany
	private List<Produto> produtos;
	
	public ProdutosEmFalta(){}
	
	public ProdutosEmFalta(List<Produto> produtosEmFalta) {
		this.produtos = produtosEmFalta;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void add(Produto produto) {
		produtos.add(produto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((produtos == null) ? 0 : produtos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutosEmFalta other = (ProdutosEmFalta) obj;
		if (id != other.id)
			return false;
		if (produtos == null) {
			if (other.produtos != null)
				return false;
		} else if (!produtos.equals(other.produtos))
			return false;
		return true;
	}
}
