package bean;

import java.sql.Date;

public class BaixaRestos {

	private Date ano;
	private int empenho;
	private Date anoOP;
	private int nroOP;
	private Date dataBaixa;
	private double valorBaixa;
	private double valorNP;
	private double valorP;

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

	public Date getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public double getValorBaixa() {
		return valorBaixa;
	}

	public void setValorBaixa(double valorBaixa) {
		this.valorBaixa = valorBaixa;
	}

	public double getValorNP() {
		return valorNP;
	}

	public void setValorNP(double valorNP) {
		this.valorNP = valorNP;
	}

	public double getValorP() {
		return valorP;
	}

	public void setValorP(double valorP) {
		this.valorP = valorP;
	}

}
