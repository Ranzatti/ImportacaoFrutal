package bean;

import java.sql.Date;

public class AnulacaoLiqParc {

	private Date ano;
	private int empenho;
	private int liquidacao;
	private int parcela;

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

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

}
