package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.PagtoOP;

public class PagtoOPDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public PagtoOPDAO() {
	}

	public void insere(Connection con, String usuario, PagtoOP obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPPAGTOOPS "
					+ "( ANO, OP, VALORPAGTO, DATAPAGTO ) values "
					+ "( ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getOp());
			stmtAux.setDouble(3, obj.getValorPagto());
			stmtAux.setDate(4, obj.getDataPagto());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Pagamento Ordens Pagto");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPPAGTOOPS Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Pagamento Ordens Pagto");
			e.printStackTrace();
		}
	}
	
	public void deleteRestos(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPPAGTOOPS A Where exists ( Select * from " + usuario + ".CBPORDENSPAGTO B Where A.ANO = B.ANO AND A.OP = B.NUMERO AND B.ANO = ? AND B.ANORESTOS IS NOT NULL ) ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Pagamento Ordens Pagto");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano, int numero) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPPAGTOOPS Where ANO = ? and OP = ?");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, numero);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Pagamento Ordens Pagto");
			e.printStackTrace();
		}
	}

	public PagtoOP seleciona(Connection con, String usuario, Date ano, int numero) {

		PagtoOP obj = new PagtoOP();

		try {
			stmtAux = con.prepareStatement(
					"Select VALORPAGTO, DATAPAGTO "
					+ "From " + usuario + ".CBPPAGTOOPS " 
					+ "Where ANO = ? " 
					+ "And OP = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, numero);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setAno(ano);
				obj.setOp(numero);
				obj.setValorPagto(rsAux.getDouble(1));
				obj.setDataPagto(rsAux.getDate(2));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Pagamento Ordens Pagto");
			e.printStackTrace();
		}

		return obj;

	}
}
