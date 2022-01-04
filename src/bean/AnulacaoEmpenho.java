package bean;

import java.sql.Date;

public class AnulacaoEmpenho {

	private Date ano;
	private int empenho;
	private int anulacao;
	private Date dataAnulacao;
	private double valorAnulacao;
	private String historico;
	private String tipo;
	private int lancamento;

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

	public int getAnulacao() {
		return anulacao;
	}

	public void setAnulacao(int anulacao) {
		this.anulacao = anulacao;
	}

	public Date getDataAnulacao() {
		return dataAnulacao;
	}

	public void setDataAnulacao(Date dataAnulacao) {
		this.dataAnulacao = dataAnulacao;
	}

	public double getValorAnulacao() {
		return valorAnulacao;
	}

	public void setValorAnulacao(double valorAnulacao) {
		this.valorAnulacao = valorAnulacao;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getLancamento() {
		return lancamento;
	}

	public void setLancamento(int lancamento) {
		this.lancamento = lancamento;
	}

}
