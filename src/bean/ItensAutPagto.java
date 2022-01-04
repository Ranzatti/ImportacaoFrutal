package bean;

import java.sql.Date;

public class ItensAutPagto {

	private Date ano;
	private int autorizacao;
	private String tipoDoc;
	private int documento;
	private int parcela;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
	}

	public int getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(int autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public int getDocumento() {
		return documento;
	}

	public void setDocumento(int documento) {
		this.documento = documento;
	}

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

}
