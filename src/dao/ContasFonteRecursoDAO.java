package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ContasFonteRecurso;

public class ContasFonteRecursoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ContasFonteRecursoDAO() {
	}

	public void insere(Connection con, String usuario, ContasFonteRecurso obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPCONTASFONTEREC "
					+ "( FICHA, VERSAORECURSO, FONTERECURSO, SALDOINICIAL ) values "
					+ "( ?, ?, ?, ? )");
			stmtAux.setInt(1, obj.getFicha());
			stmtAux.setInt(2, obj.getVersaoRecurso());
			stmtAux.setInt(3, obj.getFonteRecurso());
			stmtAux.setDouble(4, obj.getSaldoInicial());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Contas Fonte Recurso");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCONTASFONTEREC ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Contas Fonte Recurso");
			e.printStackTrace();
		}
	}
}
