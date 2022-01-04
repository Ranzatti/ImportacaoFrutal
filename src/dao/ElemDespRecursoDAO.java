package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ElemDespRecurso;

public class ElemDespRecursoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ElemDespRecursoDAO() {
	}

	public void insere(Connection con, String usuario, ElemDespRecurso obj) {

		try {

			stmtAux = con.prepareStatement(
					"Insert into " + usuario + ".CBPELEMDESPRECURSO "
					+ "( ANO, FICHA, VERSAORECURSO, RECURSO, ORCADO ) values "
					+ "( ?, ?, ?, ?, ?)");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getFicha());
			stmtAux.setInt(3, obj.getVersaoRecurso());
			stmtAux.setInt(4, obj.getRecurso());
			stmtAux.setDouble(5, obj.getOrcado());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Elem Despesa Recurso");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMDESPRECURSO  ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Despesa Recurso");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMDESPRECURSO Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Despesa Recurso");
			e.printStackTrace();
		}
	}

	public ElemDespRecurso seleciona(Connection con, String usuario, Date ano, int ficha, int fonteRecurso) {

		ElemDespRecurso obj = new ElemDespRecurso();

		try {
			stmtAux = con.prepareStatement(
					"Select VERSAORECURSO, ORCADO " 
					+ "From " + usuario	+ ".CBPELEMDESPRECURSO " 
					+ "Where ANO = ? " 
					+ "And FICHA = ? " 
					+ "And RECURSO = ?" );
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, ficha);
			stmtAux.setInt(3, fonteRecurso);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setAno(ano);
				obj.setFicha(ficha);
				obj.setVersaoRecurso(rsAux.getInt(1));
				obj.setRecurso(fonteRecurso);
				obj.setOrcado(rsAux.getDouble(2));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção Elem Despesa Recurso");
			e.printStackTrace();
		}
		return obj;
	}

}
