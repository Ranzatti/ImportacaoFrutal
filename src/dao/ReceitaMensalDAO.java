package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.ReceitaMensal;

public class ReceitaMensalDAO {

	PreparedStatement stmtAux = null;

	public ReceitaMensalDAO() {
	}

	public void insere(Connection con, String usuario, ReceitaMensal obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPRECEITASMENSAL "
					+ "( ANO, FICHA, VERSAORECURSO, RECURSO, CAFIXO, CAVARIAVEL, MES, ORCADO ) Values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getFichaReceita());
			stmtAux.setInt(3, obj.getVersaoRecurso());
			stmtAux.setInt(4, obj.getFonteRecurso());
			stmtAux.setInt(5, obj.getCaFixo());
			stmtAux.setInt(6, obj.getCaVariavel());
			stmtAux.setInt(7, obj.getMes());
			stmtAux.setDouble(8, obj.getOrcado());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Receitas Fonte Recurso Mensal");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECEITASMENSAL ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Receitas Fonte Recurso Mensal");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECEITASMENSAL Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Receitas Fonte Recurso Mensal");
			e.printStackTrace();
		}
	}
}
