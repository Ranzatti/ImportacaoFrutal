package bean;

import java.sql.Date;

public class SaidasCaixa {

	private Date data;
	private int saida;
	private String historico;
	private Date anoLancto;
	private int lancamento;
	private String tipoAnulacaoRec;
	private int anulacaoReceita;
	private double valor;
	private int transferencia;
	private int autPagto;
	private int versaoRecurso;
	private int fonteRecurso;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getSaida() {
		return saida;
	}

	public void setSaida(int saida) {
		this.saida = saida;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
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

	public int getAnulacaoReceita() {
		return anulacaoReceita;
	}

	public void setAnulacaoReceita(int anulacaoReceita) {
		this.anulacaoReceita = anulacaoReceita;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(int transferencia) {
		this.transferencia = transferencia;
	}

	public int getAutPagto() {
		return autPagto;
	}

	public void setAutPagto(int autPagto) {
		this.autPagto = autPagto;
	}

	public int getVersaoRecurso() {
		return versaoRecurso;
	}

	public void setVersaoRecurso(int versaoRecurso) {
		this.versaoRecurso = versaoRecurso;
	}

	public int getFonteRecurso() {
		return fonteRecurso;
	}

	public void setFonteRecurso(int fonteRecurso) {
		this.fonteRecurso = fonteRecurso;
	}

	public String getTipoAnulacaoRec() {
		return tipoAnulacaoRec;
	}

	public void setTipoAnulacaoRec(String tipoAnulacaoRec) {
		this.tipoAnulacaoRec = tipoAnulacaoRec;
	}

}
