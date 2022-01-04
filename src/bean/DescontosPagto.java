package bean;

import java.sql.Date;

public class DescontosPagto {

	private Date ano;
	private int empenho;
	private int parcela;
	private String tipoGuia;
	private int guiaReceita;

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

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

	public String getTipoGuia() {
		return tipoGuia;
	}

	public void setTipoGuia(String tipoGuia) {
		this.tipoGuia = tipoGuia;
	}

	public int getGuiaReceita() {
		return guiaReceita;
	}

	public void setGuiaReceita(int guiaReceita) {
		this.guiaReceita = guiaReceita;
	}

}
