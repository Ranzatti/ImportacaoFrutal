package bean;

import java.sql.Date;

public class Debito {

	private int numero;
	private int fichaConta;
	private int banco;
	private String agencia;
	private String conta;
	private Date data;
	private String historico;
	private Date anoLancto;
	private int lancamento;
	private String tipoAnulacaoRec;
	private int anulacaoReceita;
	private double valor;
	private int versaoRecurso;
	private int fonteRecurso;
	private int caFixo;
	private int caVariavel;
	private String finalidade;
	private int autPagto;
	private int transferencia;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getFichaConta() {
		return fichaConta;
	}

	public void setFichaConta(int fichaConta) {
		this.fichaConta = fichaConta;
	}

	public int getBanco() {
		return banco;
	}

	public void setBanco(int banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	public String getTipoAnulacaoRec() {
		return tipoAnulacaoRec;
	}

	public void setTipoAnulacaoRec(String tipoAnulacaoRec) {
		this.tipoAnulacaoRec = tipoAnulacaoRec;
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

	public int getCaFixo() {
		return caFixo;
	}

	public void setCaFixo(int caFixo) {
		this.caFixo = caFixo;
	}

	public int getCaVariavel() {
		return caVariavel;
	}

	public void setCaVariavel(int caVariavel) {
		this.caVariavel = caVariavel;
	}

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public int getAutPagto() {
		return autPagto;
	}

	public void setAutPagto(int autPagto) {
		this.autPagto = autPagto;
	}

	public int getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(int transferencia) {
		this.transferencia = transferencia;
	}

}
