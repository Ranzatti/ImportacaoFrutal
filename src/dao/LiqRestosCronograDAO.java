package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.LiqRestCronogra;

public class LiqRestosCronograDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public LiqRestosCronograDAO() {
	}

	public void insere(Connection con, String usuario, LiqRestCronogra obj) {

		try {

			stmtAux = con.prepareStatement(	"insert into " + usuario + ".CBPLIQRESTCRONOGRA "
					+ "( ANOEMPENHO, EMPENHO, LIQUIDACAO, PARCELA, ANOOP, OP ) values "
					+ "( ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAnoEmpenho());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getLiquidacao());
			stmtAux.setInt(4, obj.getParcela());
			stmtAux.setDate(5, obj.getAnoOP());
			stmtAux.setInt(6, obj.getNroOP());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Liquida Restos Cronogra");
			e.printStackTrace();
		} 
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPLIQRESTCRONOGRA ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Liquida Restos Cronogra");
			e.printStackTrace();
		}
	}
	public void delete(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPLIQRESTCRONOGRA where ANOOP = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Liquida Restos Cronogra");
			e.printStackTrace();
		}
	}

}
