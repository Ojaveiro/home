package com.home.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

public class Lancamento {

	@Id
	private String id;

	private double valor;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data;

	private Conta conta;

	private int mes;
	
	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public Lancamento() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	public String getId() {
		return id;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
