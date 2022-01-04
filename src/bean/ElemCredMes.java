package bean;

import java.sql.Date;

public class ElemCredMes {

	private Date ano;
	private int credito;
	private int ficha;
	private int versaoRecurso;
	private int recurso;
	private int caFixo;
	private int caVariavel;
	private int mes;
	private double valor;

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getCredito() {
		return credito;
	}

	public void setCredito(int credito) {
		this.credito = credito;
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

}
