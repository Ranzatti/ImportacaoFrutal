package bean;

import java.sql.Date;

public class GuiaReceita {

	private Date ano;
	private int numero;
	private String historico;
	private Date data;
	private String tipo;
	private Date vencimento;
	private Date recebimento;
	private int contribuinte;
	private int lancamento;
	private String origem;
	private int autPagto;

	public int getAutPagto() {
		return autPagto;
	}

	public void setAutPagto(int autPagto) {
		this.autPagto = autPagto;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public int getLancamento() {
		return lancamento;
	}

	public void setLancamento(int lancamento) {
		this.lancamento = lancamento;
	}

	public int getContribuinte() {
		return contribuinte;
	}

	public void setContribuinte(int contribuinte) {
		this.contribuinte = contribuinte;
	}

	public Date getRecebimento() {
		return recebimento;
	}

	public void setRecebimento(Date recebimento) {
		this.recebimento = recebimento;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
