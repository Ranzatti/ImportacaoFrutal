package bean;

import java.sql.Date;

public class ContasBancarias {

	private int ficha;
	private int banco;
	private String agencia;
	private String codigo;
	private String dv;
	private String nome;
	private String tipoConta;
	private String titular;
	private int bancoAudesp;
	private String agenciaAudesp;
	private String contaAudesp;
	private Date encerramento;
	private int empresa;

	public int getFicha() {
		return ficha;
	}

	public void setFicha(int ficha) {
		this.ficha = ficha;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public int getBancoAudesp() {
		return bancoAudesp;
	}

	public void setBancoAudesp(int bancoAudesp) {
		this.bancoAudesp = bancoAudesp;
	}

	public String getAgenciaAudesp() {
		return agenciaAudesp;
	}

	public void setAgenciaAudesp(String agenciaAudesp) {
		this.agenciaAudesp = agenciaAudesp;
	}

	public String getContaAudesp() {
		return contaAudesp;
	}

	public void setContaAudesp(String contaAudesp) {
		this.contaAudesp = contaAudesp;
	}

	public Date getEncerramento() {
		return encerramento;
	}

	public void setEncerramento(Date encerramento) {
		this.encerramento = encerramento;
	}

	public int getEmpresa() {
		return empresa;
	}

	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}

}
