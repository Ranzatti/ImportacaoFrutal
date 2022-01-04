package bean;

import java.sql.Date;

public class LiquidaRestos {

	private Date anoEmpenho;
	private int empenho;
	private int liquidacao;
	private Date dataLiquidacao;
	private double valor;
	private String historico;
	private Date vencimento;
	private String liquidante;
	private Date anoFato;
	private int fato;

	public Date getAnoEmpenho() {
		return anoEmpenho;
	}

	public void setAnoEmpenho(Date anoEmpenho) {
		this.anoEmpenho = anoEmpenho;
	}

	public int getEmpenho() {
		return empenho;
	}

	public void setEmpenho(int empenho) {
		this.empenho = empenho;
	}

	public int getLiquidacao() {
		return liquidacao;
	}

	public void setLiquidacao(int liquidacao) {
		this.liquidacao = liquidacao;
	}

	public Date getDataLiquidacao() {
		return dataLiquidacao;
	}

	public void setDataLiquidacao(Date dataLiquidacao) {
		this.dataLiquidacao = dataLiquidacao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public String getLiquidante() {
		return liquidante;
	}

	public void setLiquidante(String liquidante) {
		this.liquidante = liquidante;
	}

	public Date getAnoFato() {
		return anoFato;
	}

	public void setAnoFato(Date anoFato) {
		this.anoFato = anoFato;
	}

	public int getFato() {
		return fato;
	}

	public void setFato(int fato) {
		this.fato = fato;
	}
	
}
