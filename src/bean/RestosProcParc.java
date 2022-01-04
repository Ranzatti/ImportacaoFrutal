package bean;

import java.sql.Date;

public class RestosProcParc {

	private Date anoEmpenho;
	private int empenho;
	private int parcela;
	private Date vencimento;
	private Double valor;

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

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
