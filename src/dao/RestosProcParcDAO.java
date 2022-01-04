package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.RestosProcParc;

public class RestosProcParcDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public RestosProcParcDAO() {
	}

	public RestosProcParc seleciona(Connection con, String usuario, Date ano, int empenho, int parcela) {

		RestosProcParc restos = new RestosProcParc();

		try {
			stmtAux = con.prepareStatement(
					"Select VENCIMENTO, VALOR " 
					+ " From " + usuario + ".CBPRESTOSPROCPARC " 
					+ " Where ANOEMPENHO = ? " 
					+ " And EMPENHO= ? "
					+ " And PARCELA = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, empenho);
			stmtAux.setInt(3, parcela);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				restos.setAnoEmpenho(ano);
				restos.setEmpenho(empenho);
				restos.setParcela(parcela);
				restos.setVencimento(rsAux.getDate(1));
				restos.setValor(rsAux.getDouble(2));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Restos Proc Parc");
			e.printStackTrace();
		}

		return restos;
	}
	
	public void insere(Connection con, String usuario, RestosProcParc obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPRESTOSPROCPARC "
					+ "( ANOEMPENHO, EMPENHO, PARCELA, VENCIMENTO, VALOR ) values " 
					+ "( ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAnoEmpenho());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getParcela());
			stmtAux.setDate(4, obj.getVencimento());
			stmtAux.setDouble(5, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Restos Proc Parc");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRESTOSPROCPARC ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Restos Proc Parc");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRESTOSPROCPARC Where ANOEMPENHO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Restos Proc Parc");
			e.printStackTrace();
		}
	}
}
