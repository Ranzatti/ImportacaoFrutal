package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.AutPagto;

public class AutPagtoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public AutPagtoDAO() {
	}

	public void insere(Connection con, String usuario, AutPagto obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPAUTPAGTO "
					+ "( ANO, NUMERO, DATAAUTORIZACAO, LANCAMENTO, DATAPAGAMENTO ) values " + "( ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getNumero());
			stmtAux.setDate(3, obj.getDataAutorizacao());
			stmtAux.setInt(4, obj.getLancamento());
			stmtAux.setDate(5, obj.getDataPagamento());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Autorizacao pagamento");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPAUTPAGTO Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Autorizacao pagamento");
			e.printStackTrace();
		}
	}

	public AutPagto seleciona(Connection con, String usuario, Date ano, int numero) {

		AutPagto autPagto = new AutPagto();

		try {
			stmtAux = con.prepareStatement("Select DATAAUTORIZACAO, LANCAMENTO, DATAPAGAMENTO "
					+ "From " + usuario	+ ".CBPAUTPAGTO "
					+ "Where ANO = ? "
					+ "And NUMERO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, numero);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				autPagto.setAno(ano);
				autPagto.setNumero(numero);
				autPagto.setDataAutorizacao(rsAux.getDate(1));
				autPagto.setLancamento(rsAux.getInt(2));
				autPagto.setDataPagamento(rsAux.getDate(3));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Guia Receita");
			e.printStackTrace();
		}

		return autPagto;
	}
}
