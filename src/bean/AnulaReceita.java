package bean;

import java.sql.Date;

public class AnulaReceita {

	// ANO, TIPO, ANULACAO, DATAANULACAO, LANCAMENTO, HISTORICO, DEDUTORA

	private Date ano;
	private String tipo;
	private int anulacao;
	private Date dataAnulacao;
	private int lancamento;
	private String historico;
	private int dedutora;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public int getLancamento() {
		return lancamento;
	}

	public void setLancamento(int lancamento) {
		this.lancamento = lancamento;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public int getDedutora() {
		return dedutora;
	}

	public void setDedutora(int dedutora) {
		this.dedutora = dedutora;
	}

}
