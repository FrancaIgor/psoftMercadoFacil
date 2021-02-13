package com.ufcg.psoft.model.DTO;

import com.ufcg.psoft.model.Categoria;

public class ProdutoVencidoDTO {
	private long id;

	private String nome;
	private String codigoBarra;
	private String fabricante;
	
	private boolean disponibilidade;

	private Categoria categoria;

	public ProdutoVencidoDTO(long id, String nome, String codigoBarra, String fabricante, boolean disponibilidade,
		 Categoria categoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.codigoBarra = codigoBarra;
		this.fabricante = fabricante;
		this.disponibilidade = disponibilidade;
		this.categoria = categoria;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public boolean isDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(boolean disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
