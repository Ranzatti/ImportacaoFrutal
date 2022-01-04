package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.AnulacaoLiqParc;

public class AnulacaoLiqParcDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public AnulacaoLiqParcDAO() {
	}

	public void insere(Connection con, String usuario, AnulacaoLiqParc obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPANULACAOLIQPARC "
					+ "(  ANO, EMPENHO, LIQUIDACAO, PARCELA ) values "
					+ "( ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getLiquidacao());
			stmtAux.setInt(4, obj.getParcela());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Anulação de Liquidacao parc de Empenhos");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPANULACAOLIQPARC where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Anulação de Liquidacao Parc de  Empenho");
			e.printStackTrace();
		}
	}
}
