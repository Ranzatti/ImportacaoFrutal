package bean;

import java.sql.Date;

public class ContaExtraFonteRec {

	private Date ano;
	private int contaExtra;
	private int versaoRecurso;
	private int fonteRecurso;
	private String tipoSaldo;
	private double saldo;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getContaExtra() {
		return contaExtra;
	}

	public void setContaExtra(int contaExtra) {
		this.contaExtra = contaExtra;
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

	public String getTipoSaldo() {
		return tipoSaldo;
	}

	public void setTipoSaldo(String tipoSaldo) {
		this.tipoSaldo = tipoSaldo;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
}
