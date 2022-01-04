package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ReceitasCA;

public class ReceitasCADAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ReceitasCADAO() {
	}

	public void insere(Connection con, String usuario, ReceitasCA obj) {

		try {

			stmtAux = con.prepareStatement(
					"Insert into " + usuario + ".CBPRECEITASCA "
					+ "( ANO, FICHA, VERSAORECURSO, RECURSO, CAFIXO, CAVARIAVEL, ORCADO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getFicha());
			stmtAux.setInt(3, obj.getVersaoRecurso());
			stmtAux.setInt(4, obj.getRecurso());
			stmtAux.setInt(5, obj.getCaFixo());
			stmtAux.setInt(6, obj.getCaVariavel());
			stmtAux.setDouble(7, obj.getOrcado());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Receitas CA");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECEITASCA ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Receitas CA");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECEITASCA Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Receitas CA");
			e.printStackTrace();
		}
	}

	public ReceitasCA seleciona(Connection con, String usuario, Date ano, int ficha, int fonteRecurso, int caFixo) {

		ReceitasCA obj = new ReceitasCA();

		try {
			stmtAux = con.prepareStatement(
					"Select VERSAORECURSO, CAVARIAVEL, ORCADO "
					+ "From " + usuario + ".CBPRECEITASCA "
					+ "Where ANO = ? "
					+ "And FICHA = ? "
					+ "And RECURSO = ?"
					+ "And CAFIXO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, ficha);
			stmtAux.setInt(3, fonteRecurso);
			stmtAux.setInt(4, caFixo);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setAno(ano);
				obj.setFicha(ficha);
				obj.setVersaoRecurso(rsAux.getInt(1));
				obj.setRecurso(fonteRecurso);
				obj.setCaFixo(caFixo);
				obj.setCaVariavel(rsAux.getInt(2));
				obj.setOrcado(rsAux.getDouble(3));
			} 
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção Receitas CA");
			e.printStackTrace();
		}
		return obj;
	}
}
