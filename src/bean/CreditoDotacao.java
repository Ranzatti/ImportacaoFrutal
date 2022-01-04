package bean;

import java.sql.Date;

public class CreditoDotacao {

	private Date ano;
	private int codigo;
	private Date dataCredito;
	private String tipo;
	private String historico;
	private String natureza;
	private String usaOrcamento;
	private int lei;
	private int transposicao;
	private int transposicaoFonte;
	private int lancamento;

	public int getLancamento() {
		return lancamento;
	}

	public void setLancamento(int lancamento) {
		this.lancamento = lancamento;
	}

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

	public Date getDataCredito() {
		return dataCredito;
	}

	public void setDataCredito(Date dataCredito) {
		this.dataCredito = dataCredito;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public String getUsaOrcamento() {
		return usaOrcamento;
	}

	public void setUsaOrcamento(String usaOrcamento) {
		this.usaOrcamento = usaOrcamento;
	}

	public int getLei() {
		return lei;
	}

	public void setLei(int lei) {
		this.lei = lei;
	}

	public int getTransposicao() {
		return transposicao;
	}

	public void setTransposicao(int transposicao) {
		this.transposicao = transposicao;
	}

	public int getTransposicaoFonte() {
		return transposicaoFonte;
	}

	public void setTransposicaoFonte(int transposicaoFonte) {
		this.transposicaoFonte = transposicaoFonte;
	}

}
