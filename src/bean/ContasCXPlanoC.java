package bean;

import java.sql.Date;

public class ContasCXPlanoC {

	private Date ano;
	private int banco;
	private String agencia;
	private String conta;
	private int fichaConta;
	private int fichaPC;
	private int ficnaPCAplic;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
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

	public int getFichaConta() {
		return fichaConta;
	}

	public void setFichaConta(int fichaConta) {
		this.fichaConta = fichaConta;
	}

	public int getFichaPC() {
		return fichaPC;
	}

	public void setFichaPC(int fichaPC) {
		this.fichaPC = fichaPC;
	}

	public int getFicnaPCAplic() {
		return ficnaPCAplic;
	}

	public void setFicnaPCAplic(int ficnaPCAplic) {
		this.ficnaPCAplic = ficnaPCAplic;
	}

}
