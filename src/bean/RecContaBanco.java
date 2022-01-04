package bean;

import java.sql.Date;

public class RecContaBanco {

	private Date ano;
	private String tipo;
	private int guia;
	private int fichaBanco;
	private int ficha;
	private int versaoRecurso;
	private int recurso;
	private int caFixo;
	private int caVariavel;
	private int item;
	private double valor;
	private int versaoRecursoBanco;
	private int fonteRecursoBanco;
	private int caFixoBanco;
	private int caVariavelBanco;

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getGuia() {
		return guia;
	}

	public void setGuia(int guia) {
		this.guia = guia;
	}

	public int getFichaBanco() {
		return fichaBanco;
	}

	public void setFichaBanco(int fichaBanco) {
		this.fichaBanco = fichaBanco;
	}

	public int getVersaoRecurso() {
		return versaoRecurso;
	}

	public void setVersaoRecurso(int versaoRecurso) {
		this.versaoRecurso = versaoRecurso;
	}

	public int getRecurso() {
		return recurso;
	}

	public void setRecurso(int recurso) {
		this.recurso = recurso;
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

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getVersaoRecursoBanco() {
		return versaoRecursoBanco;
	}

	public void setVersaoRecursoBanco(int versaoRecursoBanco) {
		this.versaoRecursoBanco = versaoRecursoBanco;
	}

	public int getFonteRecursoBanco() {
		return fonteRecursoBanco;
	}

	public void setFonteRecursoBanco(int fonteRecursoBanco) {
		this.fonteRecursoBanco = fonteRecursoBanco;
	}

	public int getCaFixoBanco() {
		return caFixoBanco;
	}

	public void setCaFixoBanco(int caFixoBanco) {
		this.caFixoBanco = caFixoBanco;
	}

	public int getCaVariavelBanco() {
		return caVariavelBanco;
	}

	public void setCaVariavelBanco(int caVariavelBanco) {
		this.caVariavelBanco = caVariavelBanco;
	}

}
