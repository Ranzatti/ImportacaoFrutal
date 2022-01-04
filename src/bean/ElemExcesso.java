package bean;

import java.sql.Date;

public class ElemExcesso {

	private Date ano;
	private int codigo;
	private int fichaPC;
	private double valor;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getFichaPC() {
		return fichaPC;
	}

	public void setFichaPC(int fichaPC) {
		this.fichaPC = fichaPC;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
