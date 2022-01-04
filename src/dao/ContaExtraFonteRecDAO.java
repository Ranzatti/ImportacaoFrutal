package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ContaExtraFonteRec;

public class ContaExtraFonteRecDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ContaExtraFonteRecDAO() {
	}

	public void insere(Connection con, String usuario, ContaExtraFonteRec obj) {

		try {
			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPCONTAEXFONTEREC "
					+ "( ANO, CONTAEXTRA, VERSAORECURSO, FONTERECURSO, TIPOSALDO, SALDO ) values "
					+ "( ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getContaExtra());
			stmtAux.setInt(3, obj.getVersaoRecurso());
			stmtAux.setInt(4, obj.getFonteRecurso());
			stmtAux.setString(5, obj.getTipoSaldo());
			stmtAux.setDouble(6, obj.getSaldo());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Conta Extra");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCONTAEXFONTEREC Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Créditos Bancários");
			e.printStackTrace();
		}
	}
}
