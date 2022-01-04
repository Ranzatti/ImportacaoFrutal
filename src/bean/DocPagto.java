package bean;

import java.sql.Date;

public class DocPagto {

	private Date ano;
	private String tipoDoc;
	private int documento;
	private int parcela;
	private int item;
	private int tipoDocPagto;
	private double valor;
	private Date dataEmissao;
	private String descricaoAdicional;
	private String documentoPagto;

	public Date getAno() {
		return ano;
	}

	public void setAno(Date ano) {
		this.ano = ano;
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

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public int getTipoDocPagto() {
		return tipoDocPagto;
	}

	public void setTipoDocPagto(int tipoDocPagto) {
		this.tipoDocPagto = tipoDocPagto;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getDescricaoAdicional() {
		return descricaoAdicional;
	}

	public void setDescricaoAdicional(String descricaoAdicional) {
		this.descricaoAdicional = descricaoAdicional;
	}

	public String getDocumentoPagto() {
		return documentoPagto;
	}

	public void setDocumentoPagto(String documentoPagto) {
		this.documentoPagto = documentoPagto;
	}

}
