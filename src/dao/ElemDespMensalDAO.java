package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ElemDespMensal;

public class ElemDespMensalDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ElemDespMensalDAO() {
	}

	public void insere(Connection con, String usuario, ElemDespMensal obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPELEMDESPMENSAL "
					+ "( ANO, FICHA, VERSAORECURSO, MES, RECURSO, CAFIXO, CAVARIAVEL, ORCADO ) Values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getFicha());
			stmtAux.setInt(3, obj.getVersaoRecurso());
			stmtAux.setInt(4, obj.getMes());
			stmtAux.setInt(5, obj.getRecurso());
			stmtAux.setInt(6, obj.getCaFixo());
			stmtAux.setInt(7, obj.getCaVariavel());
			stmtAux.setDouble(8, obj.getOrcado());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Elem Despesa Mensal");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMDESPMENSAL ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Despesa Mensal");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMDESPMENSAL Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Despesa Mensal");
			e.printStackTrace();
		}
	}
	
	public ElemDespMensal seleciona(Connection con, String usuario, Date ano, int ficha, int mes, int fonteRecurso, int caFixo) {

		ElemDespMensal obj = new ElemDespMensal();

		try {
			stmtAux = con.prepareStatement(
					"Select VERSAORECURSO, CAVARIAVEL, ORCADO " 
					+ "From " + usuario	+ ".CBPELEMDESPMENSAL " 
					+ "Where ANO = ? " 
					+ "And FICHA = ? " 
					+ "And MES = ? " 
					+ "And RECURSO = ?" 
					+ "And CAFIXO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, ficha);
			stmtAux.setInt(3, mes);
			stmtAux.setInt(4, fonteRecurso);
			stmtAux.setInt(5, caFixo);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setAno(ano);
				obj.setFicha(ficha);
				obj.setVersaoRecurso(rsAux.getInt(1));
				obj.setMes(mes);
				obj.setRecurso(fonteRecurso);
				obj.setCaFixo(caFixo);
				obj.setCaVariavel(rsAux.getInt(2));
				obj.setOrcado(rsAux.getDouble(3));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção Elem Despesa Mensal");
			e.printStackTrace();
		}
		return obj;
	}

}
