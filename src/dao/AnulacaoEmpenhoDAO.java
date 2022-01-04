package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.AnulacaoEmpenho;

public class AnulacaoEmpenhoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public AnulacaoEmpenhoDAO() {
	}

	public void insere(Connection con, String usuario, AnulacaoEmpenho obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPANULACAOEMPENHO "
					+ "( ANO, EMPENHO, ANULACAO, DATAANULACAO, VALORANULACAO, HISTORICO, TIPO, LANCAMENTO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getAnulacao());
			stmtAux.setDate(4, obj.getDataAnulacao());
			stmtAux.setDouble(5, obj.getValorAnulacao());
			stmtAux.setString(6, obj.getHistorico());
			stmtAux.setString(7, obj.getTipo());
			stmtAux.setInt(8, obj.getLancamento());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Anulação de Empenhos");
			e.printStackTrace();
		}
	}

	public int getMax(Connection con, String usuario, Date ano, int empenho) {
		
		int max = 0;

		try {
			stmtAux = con.prepareStatement(
					"Select MAX( ANULACAO ) From " + usuario + ".CBPANULACAOEMPENHO Where ANO = ? And EMPENHO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, empenho);
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Max Anulação de Empenhos");
			e.printStackTrace();
		}
		return max + 1;
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPANULACAOEMPENHO where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Anulação de Empenho");
			e.printStackTrace();
		}
	}
}
