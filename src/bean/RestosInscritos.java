package bean;

import java.sql.Date;

public class RestosInscritos {

	private Date ano;
	private int empenho;
	private String origem;
	private String credor;
	private int codCredor;
	private Double valor;
	private Double valorNaoProc;
	private int ficha;
	private String subElemento;
	private Date dataEmpenho;
	private Double valorEmpenho;

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

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getCredor() {
		return credor;
	}

	public void setCredor(String credor) {
		this.credor = credor;
	}

	public int getCodCredor() {
		return codCredor;
	}

	public void setCodCredor(int codCredor) {
		this.codCredor = codCredor;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getValorNaoProc() {
		return valorNaoProc;
	}

	public void setValorNaoProc(Double valorNaoProc) {
		this.valorNaoProc = valorNaoProc;
	}

	public int getFicha() {
		return ficha;
	}

	public void setFicha(int ficha) {
		this.ficha = ficha;
	}

	public String getSubElemento() {
		return subElemento;
	}

	public void setSubElemento(String subElemento) {
		this.subElemento = subElemento;
	}

	public Date getDataEmpenho() {
		return dataEmpenho;
	}

	public void setDataEmpenho(Date dataEmpenho) {
		this.dataEmpenho = dataEmpenho;
	}

	public Double getValorEmpenho() {
		return valorEmpenho;
	}

	public void setValorEmpenho(Double valorEmpenho) {
		this.valorEmpenho = valorEmpenho;
	}

}
