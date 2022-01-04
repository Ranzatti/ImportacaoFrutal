package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.BaixaRestos;

public class BaixaRestosDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public BaixaRestosDAO() {
	}

	public void insere(Connection con, String usuario, BaixaRestos obj) {

		try {

			stmtAux = con.prepareStatement(	"insert into " + usuario + ".CBPBAIXARESTOS "
					+ "( ANO, EMPENHO, ANOOP, NROOP, DATABAIXA, VALORBAIXA, VALORNP, VALORP ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setDate(3, obj.getAnoOP());
			stmtAux.setInt(4, obj.getNroOP());
			stmtAux.setDate(5, obj.getDataBaixa());
			stmtAux.setDouble(6, obj.getValorBaixa());
			stmtAux.setDouble(7, obj.getValorNP());
			stmtAux.setDouble(8, obj.getValorP());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Baixa Restos");
			e.printStackTrace();
		} 
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPBAIXARESTOS ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Agencia");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPBAIXARESTOS Where ANOOP = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Agencia");
			e.printStackTrace();
		}
	}
}
