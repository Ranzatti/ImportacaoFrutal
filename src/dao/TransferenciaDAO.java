package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Transferencia;

public class TransferenciaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public TransferenciaDAO() {
	}

	public void insere(Connection con, String usuario, Transferencia obj) {
		
		try {

			stmtAux = con.prepareStatement(
					"insert into " + usuario + ".CBPTRANSFERENCIA "
					+ "( ANO, SEQUENCIAL) values "
					+ "(  ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getSequencial());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Transferencia Bancários");
			e.printStackTrace();
		}
	}
	
	public int getMax(Connection con, String usuario, Date ano) {
		
		int max = 0;
		
		try {
			stmtAux = con.prepareStatement(
					"Select MAX( SEQUENCIAL ) From " + usuario + ".CBPTRANSFERENCIA Where ANO = ? ");
			stmtAux.setDate(1, ano);
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max + 1;
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPTRANSFERENCIA ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Transferencia Bancários");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPTRANSFERENCIA where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Transferencia Bancários");
			e.printStackTrace();
		}
	}

}
