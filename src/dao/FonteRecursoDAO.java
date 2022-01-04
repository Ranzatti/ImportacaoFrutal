package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.FonteRecurso;

public class FonteRecursoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public FonteRecursoDAO() {
	}

	public void insere(Connection con, String usuario, FonteRecurso obj) {

		try {
			stmtAux = con.prepareStatement(
					"Insert into " + usuario + ".CBPRECURSO "
							+ "( VERSAO, CODIGO, DESCRICAO ) values "
							+ "( ?, ?, ? )");
			stmtAux.setInt(1, obj.getVersao());
			stmtAux.setInt(2, obj.getCodigo());
			stmtAux.setString(3, obj.getDescricao());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclus�o Fonte Recurso");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECURSO ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclus�o Fonte Recurso");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario, int versao) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECURSO Where VERSAO = ?");
			stmtAux.setInt(1, versao);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclus�o Fonte Recurso");
			e.printStackTrace();
		}
	}
}
