package bean;

import java.sql.Date;

public class OPFonteRecurso {

	private Date ano;
	private int op;
	private int versaoRecurso;
	private int fonteRecurso;
	private int caFixo;
	private int caVariavel;
	private double valor;

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

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
