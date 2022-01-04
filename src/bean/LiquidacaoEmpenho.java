package bean;

import java.sql.Date;

public class LiquidacaoEmpenho {

	private Date ano;
	private int empenho;
	private int liquidacao;
	private Date dataLiquidacao;
	private double valorLiquidacao;
	private String historico;
	private String liquidante;
	private int lancamento;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
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

	public double getValorLiquidacao() {
		return valorLiquidacao;
	}

	public void setValorLiquidacao(double valorLiquidacao) {
		this.valorLiquidacao = valorLiquidacao;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getLiquidante() {
		return liquidante;
	}

	public void setLiquidante(String liquidante) {
		this.liquidante = liquidante;
	}

	public int getLancamento() {
		return lancamento;
	}

	public void setLancamento(int lancamento) {
		this.lancamento = lancamento;
	}

}
