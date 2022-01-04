package bean;

import java.sql.Date;

public class Resgate {

	private int banco;
	private String agencia;
	private String conta;
	private int numero;
	private Date data;
	private int fichaConta;
	private String historico;
	private double valor;
	private int tipoAplicFinan;
	private Date anoFato;
	private int fato;
	private int versaoRecurso;
	private int fonteRecurso;
	private int caFixo;
	private int caVariavel;

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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getFichaConta() {
		return fichaConta;
	}

	public void setFichaConta(int fichaConta) {
		this.fichaConta = fichaConta;
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

	public int getTipoAplicFinan() {
		return tipoAplicFinan;
	}

	public void setTipoAplicFinan(int tipoAplicFinan) {
		this.tipoAplicFinan = tipoAplicFinan;
	}

	public Date getAnoFato() {
		return anoFato;
	}

	public void setAnoFato(Date anoFato) {
		this.anoFato = anoFato;
	}

	public int getFato() {
		return fato;
	}

	public void setFato(int fato) {
		this.fato = fato;
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

}
