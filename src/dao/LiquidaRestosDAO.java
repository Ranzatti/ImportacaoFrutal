package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.LiquidaRestos;

public class LiquidaRestosDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public LiquidaRestosDAO() {
	}

	public void insere(Connection con, String usuario, LiquidaRestos obj) {

		try {

			stmtAux = con.prepareStatement(	"insert into " + usuario + ".CBPLIQUIDARESTOS "
					+ "( ANOEMPENHO, EMPENHO, LIQUIDACAO, DATALIQUIDACAO, VALOR, HISTORICO, VENCIMENTO, LIQUIDANTE, ANOFATO, FATO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAnoEmpenho());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getLiquidacao());
			stmtAux.setDate(4, obj.getDataLiquidacao());
			stmtAux.setDouble(5, obj.getValor());
			stmtAux.setString(6, obj.getHistorico());
			stmtAux.setDate(7, obj.getVencimento());
			stmtAux.setString(8, obj.getLiquidante());
			stmtAux.setDate(9, obj.getAnoFato());
			stmtAux.setInt(10, obj.getFato());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Liquida Restos");
			e.printStackTrace();
		} 
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPLIQUIDARESTOS ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Liquida Restos");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPLIQUIDARESTOS Where ANOFATO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Liquida Restos");
			e.printStackTrace();
		}
	}
	
	public int getMax(Connection con, String usuario, Date anoEmpenho, int empenho) {
		
		int max = 0;
		
		try {
			stmtAux = con.prepareStatement(
					"Select MAX( LIQUIDACAO ) From " + usuario + ".CBPLIQUIDARESTOS Where ANOEMPENHO = ? And EMPENHO = ? ");
			stmtAux.setDate(1, anoEmpenho);
			stmtAux.setInt(2, empenho);
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			rsAux.close();
			stmtAux.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max + 1;
	}

}
