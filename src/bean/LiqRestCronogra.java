package bean;

import java.sql.Date;

public class LiqRestCronogra {

	// ANOEMPENHO, EMPENHO, LIQUIDACAO, PARCELA, ANOOP, OP

	private Date anoEmpenho;
	private int empenho;
	private int liquidacao;
	private int parcela;
	private Date anoOP;
	private int nroOP;

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

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

	public Date getAnoOP() {
		return anoOP;
	}

	public void setAnoOP(Date anoOP) {
		this.anoOP = anoOP;
	}

	public int getNroOP() {
		return nroOP;
	}

	public void setNroOP(int nroOP) {
		this.nroOP = nroOP;
	}

}
