package com.ufcg.psoft.model;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String nome;
	private BigDecimal preco;
	private String codigoBarra;
	private String fabricante;

	private boolean disponibilidade;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	public Produto() {
		this.id = 0;
		this.preco = new BigDecimal(0);
	}

	public Produto(long id, String nome, String codigoBarra, String fabricante, Categoria categoria, boolean disponibilidade) {

		this.id = id;
		this.nome = nome;
		this.preco = new BigDecimal(0);
		this.codigoBarra = codigoBarra;
		this.fabricante = fabricante;
		this.categoria = categoria;
		this.disponibilidade = disponibilidade;
	}

	public String getNome() {
		return nome;
	}

	public void mudaNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public long getId() {
		return id;
	}

	public void mudaId(long codigo) {
		this.id = codigo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void mudaFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void mudaCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setDisponibilidade(boolean disponibilidade) {
		if(disponibilidade == false) {
			this.preco = null;
		}
		this.disponibilidade = disponibilidade;
	}

	public boolean getDisponibilidade() {
		return this.disponibilidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fabricante == null) ? 0 : fabricante.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Produto other = (Produto) obj;
		if (fabricante == null) {
			if (other.fabricante != null)
				return false;
		} else if (!fabricante.equals(other.fabricante))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}