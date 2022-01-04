package bean;

import java.sql.Date;

public class ContaExtra {

	private Date ano;
	private int contaExtra;
	private String nome;
	private Date anoRestos;
	private int empresa;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getContaExtra() {
		return contaExtra;
	}

	public void setContaExtra(int contaExtra) {
		this.contaExtra = contaExtra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getAnoRestos() {
		return anoRestos;
	}

	public void setAnoRestos(Date anoRestos) {
		this.anoRestos = anoRestos;
	}

	public int getEmpresa() {
		return empresa;
	}

	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}

}
