package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ContaExtra;

public class ContaExtraDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ContaExtraDAO() {
	}

	public void insere(Connection con, String usuario, ContaExtra obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPCONTAEXTRA "
					+ "( ANO, CONTAEXTRA, NOME, ANORESTOS, EMPRESA ) values " 
					+ "( ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getContaExtra());
			stmtAux.setString(3, obj.getNome());
			stmtAux.setDate(4, obj.getAnoRestos());
			stmtAux.setInt(5, obj.getEmpresa());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Conta Extra");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCONTAEXTRA Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Créditos Bancários");
			e.printStackTrace();
		}
	}
}
