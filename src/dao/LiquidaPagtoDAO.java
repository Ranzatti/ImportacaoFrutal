package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.LiquidaPagto;

public class LiquidaPagtoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public LiquidaPagtoDAO() {
	}

	public void insere(Connection con, String usuario, LiquidaPagto obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPLIQUIDAPAGTO "
					+ "( ANO, EMPENHO, LIQUIDACAO, PAGAMENTO ) values "
					+ "( ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getLiquidacao());
			stmtAux.setInt(4, obj.getPagamento());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Liquidação Pagamento de Empenhos");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPLIQUIDAPAGTO where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Liquidação Pagamento de Empenho");
			e.printStackTrace();
		}
	}
}
