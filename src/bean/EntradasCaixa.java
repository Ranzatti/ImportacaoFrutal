package bean;

import java.sql.Date;

public class EntradasCaixa {

	private Date data;
	private int entrada;
	private String historico;
	private Date anoLancto;
	private int lancamento;
	private String tipoGuia;
	private int guia;
	private int transferencia;
	private double valor;
	private int empenho;
	private int anulacao;
	private int anulRecDedutora;
	private int parcela;
	private int versaoRecurso;
	private int fonteRecurso;
	private int regulaFUNDEB;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getEntrada() {
		return entrada;
	}

	public void setEntrada(int entrada) {
		this.entrada = entrada;
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

	public String getTipoGuia() {
		return tipoGuia;
	}

	public void setTipoGuia(String tipoGuia) {
		this.tipoGuia = tipoGuia;
	}

	public int getGuia() {
		return guia;
	}

	public void setGuia(int guia) {
		this.guia = guia;
	}

	public int getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(int transferencia) {
		this.transferencia = transferencia;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
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

	public int getAnulRecDedutora() {
		return anulRecDedutora;
	}

	public void setAnulRecDedutora(int anulRecDedutora) {
		this.anulRecDedutora = anulRecDedutora;
	}

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
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

	public int getRegulaFUNDEB() {
		return regulaFUNDEB;
	}

	public void setRegulaFUNDEB(int regulaFUNDEB) {
		this.regulaFUNDEB = regulaFUNDEB;
	}

}
