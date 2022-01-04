package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ContasCA;

public class ContasCADAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ContasCADAO() {
	}

	public void insere(Connection con, String usuario, ContasCA obj) {

		try {

			stmtAux = con.prepareStatement(
					"insert into " + usuario + ".CBPCONTASCA "
					+ "( FICHA, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, SALDOINICIAL ) values "
					+ "(  ?, ?, ?, ?, ?, ? )");
			stmtAux.setInt(1, obj.getFicha());
			stmtAux.setInt(2, obj.getVersaoRecurso());
			stmtAux.setInt(3, obj.getFonteRecurso());
			stmtAux.setInt(4, obj.getCaFixo());
			stmtAux.setInt(5, obj.getCaVariavel());
			stmtAux.setDouble(6, obj.getSaldoInicial());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Contas Cod Aplicação");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCONTASCA ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Contas Cod Aplicação");
			e.printStackTrace();
		}
	}

	public ContasCA seleciona(Connection con, String usuario, int ficha, int fonteRecurso, int caFixo) {

		ContasCA obj = new ContasCA();

		try {
			stmtAux = con.prepareStatement(
					"Select VERSAORECURSO, CAVARIAVEL, SALDOINICIAL "
					+ "From " + usuario + ".CBPCONTASCA "
					+ "Where FICHA = ? "
					+ "And FONTERECURSO = ?"
					+ "And CAFIXO = ? ");
			stmtAux.setInt(1, ficha);
			stmtAux.setInt(2, fonteRecurso);
			stmtAux.setInt(3, caFixo);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setFicha(ficha);
				obj.setVersaoRecurso(rsAux.getInt(1));
				obj.setFonteRecurso(fonteRecurso);
				obj.setCaFixo(caFixo);
				obj.setCaVariavel(rsAux.getInt(2));
				obj.setSaldoInicial(rsAux.getDouble(3));
			} 
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Contas Cod Aplicação");
			e.printStackTrace();
		}
		return obj;
	}
}
