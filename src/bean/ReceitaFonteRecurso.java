package bean;

import java.sql.Date;

public class ReceitaFonteRecurso {

	private Date ano;
	private int fichaReceita;
	private int versaoRecurso;
	private int fonteRecurso;
	private Double orcado;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getFichaReceita() {
		return fichaReceita;
	}

	public void setFichaReceita(int fichaReceita) {
		this.fichaReceita = fichaReceita;
	}

	public int getVersaoRecurso() {
		return versaoRecurso;
	}

	public void setVersaoRecurso(int versaoRecurso) {
		this.versaoRecurso = versaoRecurso;
	}

	public int getFonteRecurso() {
		return fonteRecurso;
	}

	public void setFonteRecurso(int fonteRecurso) {
		this.fonteRecurso = fonteRecurso;
	}

	public Double getOrcado() {
		return orcado;
	}

	public void setOrcado(Double orcado) {
		this.orcado = orcado;
	}

}
