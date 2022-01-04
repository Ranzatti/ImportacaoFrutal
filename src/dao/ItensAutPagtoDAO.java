package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ItensAutPagto;

public class ItensAutPagtoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ItensAutPagtoDAO() {
	}

	public void insere(Connection con, String usuario, ItensAutPagto obj) {

		try {

			stmtAux = con.prepareStatement(
					"insert into " + usuario + ".CBPITENSAUTPAGTO " 
					+ "( ANO, AUTORIZACAO, TIPODOC, DOCUMENTO, PARCELA ) values " 
					+ "( ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getAutorizacao());
			stmtAux.setString(3, obj.getTipoDoc());
			stmtAux.setInt(4, obj.getDocumento());
			stmtAux.setInt(5, obj.getParcela());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Itens Autorizacao pagamento");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPITENSAUTPAGTO Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Itens Autorizacao pagamento");
			e.printStackTrace();
		}
	}
}
