package bean;

import java.sql.Date;

public class Receitas {

	private Date ano;
	private int empresa;
	private int ficha;
	private String receita;
	private Double orcado;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getEmpresa() {
		return empresa;
	}

	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}

	public int getFicha() {
		return ficha;
	}

	public void setFicha(int ficha) {
		this.ficha = ficha;
	}

	public String getReceita() {
		return receita;
	}

	public void setReceita(String receita) {
		this.receita = receita;
	}

	public Double getOrcado() {
		return orcado;
	}

	public void setOrcado(Double orcado) {
		this.orcado = orcado;
	}

}
