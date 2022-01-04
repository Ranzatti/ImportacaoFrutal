package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.EmpenhoComplemento;

public class EmpenhoComplementoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public EmpenhoComplementoDAO() {
	}

	public void insere(Connection con, String usuario, EmpenhoComplemento obj) {

		try {
			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPEMPCOMPLEMENTAR "
					+ "( ANO, EMPENHO, COMPLEMENTO, DATA, LANCAMENTO, DESCRICAO, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getComplemento());
			stmtAux.setDate(4, obj.getData());
			stmtAux.setInt(5, obj.getLancamento());
			stmtAux.setString(6, obj.getDescricao());
			stmtAux.setDouble(7, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Complemento de Empenhos");
			e.printStackTrace();
		}
	}

	public int getMax(Connection con, String usuario, Date ano, int empenho) {
		
		int max = 0;

		try {
			stmtAux = con.prepareStatement(
					"Select MAX( LIQUIDACAO ) From " + usuario + ".CBPEMPCOMPLEMENTAR Where ANO = ? And EMPENHO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, empenho);
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Max Complemento de Empenhos");
			e.printStackTrace();
		}
		return max + 1;
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPEMPCOMPLEMENTAR where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Liquidação de Empenho");
			e.printStackTrace();
		}
	}
}
