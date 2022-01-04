package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.DocPagto;

public class DocPagtoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public DocPagtoDAO() {
		// TODO Auto-generated constructor stub
	}

	public void insere(Connection con, String usuario, DocPagto obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPDOCPAGTO "
					+ "( ANO, TIPODOC, DOCUMENTO, PARCELA, TIPODOCPAGTO, DOCUMENTOPAGTO, DATAEMISSAO, DESCRICAOADICIONAL, ITEM, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setString(2, obj.getTipoDoc());
			stmtAux.setInt(3, obj.getDocumento());
			stmtAux.setInt(4, obj.getParcela());
			stmtAux.setInt(5, obj.getTipoDocPagto());
			stmtAux.setString(6, obj.getDocumentoPagto());
			stmtAux.setDate(7, obj.getDataEmissao());
			stmtAux.setString(8, obj.getDescricaoAdicional());
			stmtAux.setInt(9, obj.getItem());
			stmtAux.setDouble(10, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Documento Pagamento");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDOCPAGTO Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Documento Pagamento");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario, Date ano, String tipoDoc, int documento, int parcela) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDOCPAGTO Where ANO = ? And TIPODOC = ? And DOCUMENTO  = ? And PARCELA = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipoDoc);
			stmtAux.setInt(3, documento);
			stmtAux.setInt(4, parcela);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Documento Pagamento");
			e.printStackTrace();
		}
	}

	public DocPagto seleciona(Connection con, String usuario, Date ano, String tipoDoc, int documento, int parcela) {

		DocPagto obj = new DocPagto();

		try {
			stmtAux = con.prepareStatement(
					"Select TIPODOCPAGTO, DOCUMENTOPAGTO, DATAEMISSAO, DESCRICAOADICIONAL, ITEM, VALOR "
					+ " From " + usuario + ".CBPDOCPAGTO "
					+ " Where ANO = ? "
					+ " And TIPODOC = ? "
					+ " And DOCUMENTO = ? "
					+ " And PARCELA = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipoDoc);
			stmtAux.setInt(3, documento);
			stmtAux.setInt(4, parcela);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setAno(ano);
				obj.setTipoDoc(tipoDoc);
				obj.setDocumento(documento);
				obj.setParcela(parcela);
				obj.setTipoDocPagto(rsAux.getInt(1));
				obj.setDocumentoPagto(rsAux.getString(2));
				obj.setDataEmissao(rsAux.getDate(3));
				obj.setDescricaoAdicional(rsAux.getString(4));
				obj.setItem(rsAux.getInt(5));
				obj.setValor(rsAux.getDouble(6));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Documento Pagamento");
			e.printStackTrace();
		}

		return obj;
	}
}
