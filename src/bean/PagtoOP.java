package bean;

import java.sql.Date;

public class PagtoOP {

	private Date ano;
	private int op;
	private double valorPagto;
	private Date dataPagto;

	public Date getDataPagto() {
		return dataPagto;
	}

	public void setDataPagto(Date dataPagto) {
		this.dataPagto = dataPagto;
	}

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

	public double getValorPagto() {
		return valorPagto;
	}

	public void setValorPagto(double valorPagto) {
		this.valorPagto = valorPagto;
	}

}
