package bean;

import java.sql.Date;

public class ReceitasCA {

	private Date ano;
	private int ficha;
	private int versaoRecurso;
	private int recurso;
	private int caFixo;
	private int caVariavel;
	private double orcado;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getFicha() {
		return ficha;
	}

	public void setFicha(int ficha) {
		this.ficha = ficha;
	}

	public int getVersaoRecurso() {
		return versaoRecurso;
	}

	public void setVersaoRecurso(int versaoRecurso) {
		this.versaoRecurso = versaoRecurso;
	}

	public int getRecurso() {
		return recurso;
	}

	public void setRecurso(int recurso) {
		this.recurso = recurso;
	}

	public int getCaFixo() {
		return caFixo;
	}

	public void setCaFixo(int caFixo) {
		this.caFixo = caFixo;
	}

	public int getCaVariavel() {
		return caVariavel;
	}

	public void setCaVariavel(int caVariavel) {
		this.caVariavel = caVariavel;
	}

	public double getOrcado() {
		return orcado;
	}

	public void setOrcado(double orcado) {
		this.orcado = orcado;
	}

}
