package bean;

import java.sql.Date;

public class DescontosOP {

	private Date ano;
	private int op;
	private int parcela;
	private String tipoGuia;
	private int guiaReceita;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getOp() {
		return op;
	}

	public void setOp(int op) {
		this.op = op;
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
