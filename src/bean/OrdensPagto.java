package bean;

import java.sql.Date;

public class OrdensPagto {

	private Date ano;
	private int numero;
	private int ficha;
	private int fornecedor;
	private Date dataOP;
	private String historico;
	private double valor;
	private Date vencimento;
	private String status;
	private double desconto;
	private Date anoRestos;
	private int empenhoRestos;
	private int parcelaRestos;
	private double valorP;
	private double valorNP;
	private Date anoLancto;
	private int lancamento;

	public double getValorP() {
		return valorP;
	}

	public void setValorP(double valorP) {
		this.valorP = valorP;
	}

	public double getValorNP() {
		return valorNP;
	}

	public void setValorNP(double valorNP) {
		this.valorNP = valorNP;
	}

	public Date getAnoLancto() {
		return anoLancto;
	}

	public void setAnoLancto(Date anoLancto) {
		this.anoLancto = anoLancto;
	}

	public int getLancamento() {
		return lancamento;
	}

	public void setLancamento(int lancamento) {
		this.lancamento = lancamento;
	}

	public Date getAnoRestos() {
		return anoRestos;
	}

	public void setAnoRestos(Date anoRestos) {
		this.anoRestos = anoRestos;
	}

	public int getEmpenhoRestos() {
		return empenhoRestos;
	}

	public void setEmpenhoRestos(int empenhoRestos) {
		this.empenhoRestos = empenhoRestos;
	}

	public int getParcelaRestos() {
		return parcelaRestos;
	}

	public void setParcelaRestos(int parcelaRestos) {
		this.parcelaRestos = parcelaRestos;
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

	public int getFicha() {
		return ficha;
	}

	public void setFicha(int ficha) {
		this.ficha = ficha;
	}

	public int getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(int fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Date getDataOP() {
		return dataOP;
	}

	public void setDataOP(Date dataOP) {
		this.dataOP = dataOP;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

}
