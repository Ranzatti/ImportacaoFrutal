package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.RestosInscritos;

public class RestosInscritosDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public RestosInscritosDAO() {
	}

	public void insere(Connection con, String usuario, RestosInscritos obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPRESTOSINSCRITOS "
					+ "( ANO, EMPENHO, ORIGEM, CREDOR, CODCREDOR, VALOR, VALORNAOPROC, FICHA, SUBELEMENTO, DATAEMPENHO, VALOREMPENHO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setString(3, obj.getOrigem());
			stmtAux.setString(4, obj.getCredor());
			stmtAux.setInt(5, obj.getCodCredor());
			stmtAux.setDouble(6, obj.getValor());
			stmtAux.setDouble(7, obj.getValorNaoProc());
			stmtAux.setInt(8, obj.getFicha());
			stmtAux.setString(9, obj.getSubElemento());
			stmtAux.setDate(10, obj.getDataEmpenho());
			stmtAux.setDouble(11, obj.getValorEmpenho());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Restos Inscritos");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRESTOSINSCRITOS ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Restos Inscritos");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRESTOSINSCRITOS Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Restos Inscritos");
			e.printStackTrace();
		}
	}

	public RestosInscritos seleciona(Connection con, String usuario, Date ano, int empenho) {

		RestosInscritos restos = new RestosInscritos();

		try {
			stmtAux = con.prepareStatement(
					"Select ORIGEM, CREDOR, CODCREDOR, VALOR, VALORNAOPROC, FICHA, SUBELEMENTO, DATAEMPENHO, VALOREMPENHO " 
					+ " From " + usuario + ".CBPRESTOSINSCRITOS " 
					+ " Where ANO = ? " 
					+ " And EMPENHO= ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, empenho);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				restos.setAno(ano);
				restos.setEmpenho(empenho);
				restos.setOrigem(rsAux.getString(1));
				restos.setCredor(rsAux.getString(2));
				restos.setCodCredor(rsAux.getInt(3));
				restos.setValor(rsAux.getDouble(4));
				restos.setValorNaoProc(rsAux.getDouble(5));
				restos.setFicha(rsAux.getInt(6));
				restos.setSubElemento(rsAux.getString(7));
				restos.setDataEmpenho(rsAux.getDate(8));
				restos.setValorEmpenho(rsAux.getDouble(9));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Restos Inscritos");
			e.printStackTrace();
		}

		return restos;
	}
}
