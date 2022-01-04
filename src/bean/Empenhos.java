package bean;

import java.sql.Date;

public class Empenhos {

	private Date ano;
	private int numero;
	private String tipo;
	private int ficha;
	private Date dataEmpenho;
	private Date vencimento;
	private int fornecedor;
	private double valorEmpenho;
	private String desdobramento;
	private double desconto;

	public int getFicha() {
		return ficha;
	}

	public void setFicha(int ficha) {
		this.ficha = ficha;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getDataEmpenho() {
		return dataEmpenho;
	}

	public void setDataEmpenho(Date dataEmpenho) {
		this.dataEmpenho = dataEmpenho;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public int getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(int fornecedor) {
		this.fornecedor = fornecedor;
	}

	public double getValorEmpenho() {
		return valorEmpenho;
	}

	public void setValorEmpenho(double valorEmpenho) {
		this.valorEmpenho = valorEmpenho;
	}

	public String getDesdobramento() {
		return desdobramento;
	}

	public void setDesdobramento(String desdobramento) {
		this.desdobramento = desdobramento;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

}
