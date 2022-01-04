package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.OPFonteRecurso;

public class OPFonteRecursoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public OPFonteRecursoDAO() {
	}

	public void insere(Connection con, String usuario, OPFonteRecurso obj) {

		try {

			stmtAux = con.prepareStatement("Insert Into " + usuario + ".CBPOPFONTERECURSO "
					+ "( ANO, OP, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, VALOR ) Values "
					+ "( ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getOp());
			stmtAux.setInt(3, obj.getVersaoRecurso());
			stmtAux.setInt(4, obj.getFonteRecurso());
			stmtAux.setInt(5, obj.getCaFixo());
			stmtAux.setInt(6, obj.getCaVariavel());
			stmtAux.setDouble(7, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Ordens Pagto Fonte Recurso");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPOPFONTERECURSO Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Ordens Pagto Fonte Recurso");
			e.printStackTrace();
		}
	}
	
	public void deleteRestos(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPOPFONTERECURSO A Where exists ( Select * from " + usuario + ".CBPORDENSPAGTO B Where A.ANO = B.ANO AND A.OP = B.NUMERO AND B.ANO = ? AND B.ANORESTOS IS NOT NULL ) ");
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
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPOPFONTERECURSO Where ANO = ? and OP = ?");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, numero);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Ordens Pagto Fonte Recurso");
			e.printStackTrace();
		}
	}
	public OPFonteRecurso seleciona(Connection con, String usuario, Date ano, int numero) {

		OPFonteRecurso obj = new OPFonteRecurso();

		try {
			stmtAux = con.prepareStatement(
					"Select  VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, VALOR "
					+ "From " + usuario + ".CBPOPFONTERECURSO " 
					+ "Where ANO = ? " 
					+ "And OP = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, numero);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setAno(ano);
				obj.setOp(numero);
				obj.setVersaoRecurso(rsAux.getInt(1));
				obj.setFonteRecurso(rsAux.getInt(2));
				obj.setCaFixo(rsAux.getInt(3));
				obj.setCaVariavel(rsAux.getInt(4));
				obj.setValor(rsAux.getDouble(5));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Ordens Pagto Fonte Recurso");
			e.printStackTrace();
		}

		return obj;

	}
}
