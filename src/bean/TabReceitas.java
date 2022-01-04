package bean;

import java.sql.Date;

public class TabReceitas {

	private Date ano;
	private String receita;
	private String tipoConta;
	private String nome;
	private String natureza;
	private String OrigemNaLei;
	private double percentual;
	private int RecDividaAtiva;
	private int deducao;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public String getReceita() {
		return receita;
	}

	public void setReceita(String receita) {
		this.receita = receita;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public String getOrigemNaLei() {
		return OrigemNaLei;
	}

	public void setOrigemNaLei(String origemNaLei) {
		OrigemNaLei = origemNaLei;
	}

	public double getPercentual() {
		return percentual;
	}

	public void setPercentual(double percentual) {
		this.percentual = percentual;
	}

	public int getRecDividaAtiva() {
		return RecDividaAtiva;
	}

	public void setRecDividaAtiva(int recDividaAtiva) {
		RecDividaAtiva = recDividaAtiva;
	}

	public int getDeducao() {
		return deducao;
	}

	public void setDeducao(int deducao) {
		this.deducao = deducao;
	}

}
