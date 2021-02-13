package com.ufcg.psoft.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Lote {

	@Id
	@GeneratedValue
	private long id;
	@OneToOne
	private Produto produto;
	private int numeroDeItens;
	private String dataDeValidade;

	public Lote() {
		this.id = 0;
	}

	public Lote(Produto produto, int numeroDeItens, String dataDeValidade) {
		super();
		this.produto = produto;
		this.numeroDeItens = numeroDeItens;
		this.dataDeValidade = dataDeValidade;
	}

	public Lote(long id, Produto produto, int numeroDeItens, String dataDeValidade) {
		this.id = id;
		this.produto = produto;
		this.numeroDeItens = numeroDeItens;
		this.dataDeValidade = dataDeValidade;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getNumeroDeItens() {
		return numeroDeItens;
	}

	public void setNumeroDeItens(int numeroDeItens) {
		this.numeroDeItens = numeroDeItens;
	}

	public String getDataDeValidade() {
		return dataDeValidade;
	}

	public void setDataDeValidade(String dataDeValidade) {
		this.dataDeValidade = dataDeValidade;
	}

	@Override
	public String toString() {
		return "Lote{" + "id=" + id + ", produto=" + produto.getId() + ", numeroDeItens=" + numeroDeItens
				+ ", dataDeValidade='" + dataDeValidade + '\'' + '}';
	}

	public int diasParaVencer() throws ParseException {
		Date dataAtual = new Date();
		DateFormat formatoDataAtual = new SimpleDateFormat("yyyyMMdd");
		int valorDataAtual = Integer.parseInt(formatoDataAtual.format(dataAtual));
		SimpleDateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
		Date dataDeVencimeto = formatoData.parse(this.getDataDeValidade());
		DateFormat formatoDataDeVencimento = new SimpleDateFormat("yyyyMMdd");
		int valorDataDeVencimento = Integer.parseInt(formatoDataDeVencimento.format(dataDeVencimeto));
		return valorDataDeVencimento - valorDataAtual;
	}

	public boolean isVencido() throws ParseException {
		boolean isVencido = false;
		if (this.diasParaVencer() < 0) {
			isVencido = true;
		}
		return isVencido;
	}
}
