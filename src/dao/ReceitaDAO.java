package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.Receitas;

public class ReceitaDAO {

	PreparedStatement stmtAux = null;

	public ReceitaDAO() {
	}

	public void insere(Connection con, String usuario, Receitas obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPRECEITAS "
					+ "( ANO, EMPRESA, FICHA, RECEITA, ORCADO ) values "
					+ "( ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpresa());
			stmtAux.setInt(3, obj.getFicha());
			stmtAux.setString(4, obj.getReceita());
			stmtAux.setDouble(5, obj.getOrcado());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Receitas");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECEITAS ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Receitas");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECEITAS Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Receitas");
			e.printStackTrace();
		}
	}
}
