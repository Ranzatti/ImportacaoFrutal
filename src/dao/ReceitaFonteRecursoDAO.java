package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.ReceitaFonteRecurso;

public class ReceitaFonteRecursoDAO {

	PreparedStatement stmtAux = null;

	public ReceitaFonteRecursoDAO() {
	}

	public void insere(Connection con, String usuario, ReceitaFonteRecurso obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPRECFONTERECURSO "
					+ "( ANO, FICHARECEITA, VERSAORECURSO, FONTERECURSO, ORCADO ) values "
					+ "( ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getFichaReceita());
			stmtAux.setInt(3, obj.getVersaoRecurso());
			stmtAux.setInt(4, obj.getFonteRecurso());
			stmtAux.setDouble(5, obj.getOrcado());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Receitas Fonte Recurso");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECFONTERECURSO ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Receitas Fonte Recurso");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECFONTERECURSO Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Receitas Fonte Recurso");
			e.printStackTrace();
		}
	}
}
