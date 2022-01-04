package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.RestosFonteRec;

public class RestosFonteRecDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public RestosFonteRecDAO() {
	}

	public void insere(Connection con, String usuario, RestosFonteRec obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPRESTOSFONTEREC "
					+ "( ANO, EMPENHO, VERSAORECURSO, RECURSO, CAFIXO, CAVARIAVEL, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getVersaoRecurso());
			stmtAux.setInt(4, obj.getRecurso());
			stmtAux.setInt(5, obj.getCaFixo());
			stmtAux.setInt(6, obj.getCaVariavel());
			stmtAux.setDouble(7, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Fontes de Restos A pagar");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRESTOSFONTEREC ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Fontes de Restos A pagar");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRESTOSFONTEREC Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Fontes de Restos A pagar");
			e.printStackTrace();
		}
	}

	public RestosFonteRec seleciona(Connection con, String usuario, Date ano, int empenho, int fonteRecurso) {

		RestosFonteRec restos = new RestosFonteRec();

		try {
			stmtAux = con.prepareStatement(
					"Select VERSAORECURSO, CAFIXO, CAVARIAVEL, VALOR "
							+ " From " + usuario + ".CBPRESTOSFONTEREC " 
							+ " Where ANO = ? " 
							+ " And EMPENHO= ? "
							+ " And RECURSO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, empenho);
			stmtAux.setInt(3, fonteRecurso);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				restos.setAno(ano);
				restos.setEmpenho(empenho);
				restos.setVersaoRecurso(rsAux.getInt(1));
				restos.setRecurso(fonteRecurso);
				restos.setCaFixo(rsAux.getInt(2));
				restos.setCaVariavel(rsAux.getInt(3));
				restos.setValor(rsAux.getDouble(4));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Fontes de Restos A pagar");
			e.printStackTrace();
		}
		return restos;
	}
}
