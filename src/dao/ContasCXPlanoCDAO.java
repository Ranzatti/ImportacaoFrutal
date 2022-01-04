package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ContasCXPlanoC;

public class ContasCXPlanoCDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ContasCXPlanoCDAO() {
	}

	public void insere(Connection con, String usuario, ContasCXPlanoC obj) {

		try {

			stmtAux = con.prepareStatement(
					"insert into " + usuario + ".CBPCONTACXPLANOC "
					+ "( ANO, BANCO, AGENCIA, CONTA, FICHACONTA, FICHAPC, FICHAPCAPLIC ) values "
					+ "( ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getBanco());
			stmtAux.setString(3, obj.getAgencia());
			stmtAux.setString(4, obj.getConta());
			stmtAux.setInt(5, obj.getFichaConta());
			stmtAux.setInt(6, obj.getFichaPC());
			stmtAux.setInt(7, obj.getFicnaPCAplic());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão ContasCXPlanoC");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCONTACXPLANOC ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão ContasCXPlanoC");
			e.printStackTrace();
		}
	}
	public ContasCXPlanoC seleciona(Connection con, String usuario, Date anoSonner, int ficha) {

		ContasCXPlanoC contacContasCXPlanoC = new ContasCXPlanoC();
		contacContasCXPlanoC.setAno(null);
		contacContasCXPlanoC.setFichaConta(0);
		contacContasCXPlanoC.setBanco(-1);
		contacContasCXPlanoC.setAgencia(null);
		contacContasCXPlanoC.setConta(null);
		
		if(ficha == 0) {
			return contacContasCXPlanoC;
		}

		try {
			stmtAux = con.prepareStatement(
					"Select BANCO, AGENCIA, CONTA, FICHAPC, FICHAPCAPLIC From " + usuario + ".CBPCONTACXPLANOC Where ANO = ? and FICHA = ? ");
			stmtAux.setDate(1, anoSonner);
			stmtAux.setInt(1, ficha);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				contacContasCXPlanoC.setAno(anoSonner);
				contacContasCXPlanoC.setFichaConta(ficha);
				contacContasCXPlanoC.setBanco(rsAux.getInt(1));
				contacContasCXPlanoC.setAgencia(rsAux.getString(2).trim());
				contacContasCXPlanoC.setConta(rsAux.getString(3).trim());
				contacContasCXPlanoC.setFichaPC(rsAux.getInt(4));
				contacContasCXPlanoC.setFicnaPCAplic(rsAux.getInt(5));
			} 
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Conta Bancaria");
			e.printStackTrace();
		}
		return contacContasCXPlanoC;
	}
}
