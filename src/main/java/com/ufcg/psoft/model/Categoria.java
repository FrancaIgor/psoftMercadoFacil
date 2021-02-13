package com.ufcg.psoft.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Igor Franca
 */


@Entity
public class Categoria {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	private String nome;

	private BigDecimal desconto;

	public Categoria(){}

	public Categoria(String nome, double valor){
		this.desconto = new BigDecimal(valor);
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String name) {
		this.nome = name;
	}

	public BigDecimal getDesconto() {
		return this.desconto;
	}

	public void setDesconto(BigDecimal discount) {
		this.desconto = discount;
	}

	public Long getId() {
		return this.id;
	}

}