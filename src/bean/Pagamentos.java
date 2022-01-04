package bean;

import java.sql.Date;

public class Pagamentos {

	private int empenho;
	private int pagamento;
	private Date dataPagamento;

	public Pagamentos() {
		// TODO Auto-generated constructor stub
	}

	public int getEmpenho() {
		return empenho;
	}

	public void setEmpenho(int empenho) {
		this.empenho = empenho;
	}

	public int getPagamento() {
		return pagamento;
	}

	public void setPagamento(int pagamento) {
		this.pagamento = pagamento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

}
