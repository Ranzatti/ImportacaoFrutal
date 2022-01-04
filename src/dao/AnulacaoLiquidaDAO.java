package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.AnulacaoLiquida;

public class AnulacaoLiquidaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public AnulacaoLiquidaDAO() {
	}

	public void insere(Connection con, String usuario, AnulacaoLiquida obj, boolean geraNovo) {

		try {

			if (geraNovo) {
				obj.setAnulacao(getMax(con, usuario, obj.getAno(), obj.getEmpenho(), obj.getLiquidacao()));
			}

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPANULACAOLIQUIDA "
					+ "(  ANO, EMPENHO, LIQUIDACAO, PARCELA, ANULACAO, DATA, VALOR, LANCAMENTO, HISTORICO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getLiquidacao());
			stmtAux.setInt(4, obj.getParcela());
			stmtAux.setInt(5, obj.getAnulacao());
			stmtAux.setDate(6, obj.getData());
			stmtAux.setDouble(7, obj.getValor());
			stmtAux.setInt(8, obj.getLancamento());
			stmtAux.setString(9, obj.getHistorico());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Anulação de Liqudiacao de  Empenhos: " + obj.getEmpenho());
			e.printStackTrace();
		}
	}

	private int getMax(Connection con, String usuario, Date ano, int empenho, int liquidacao) {
		
		int max = 0;

		try {
			stmtAux = con.prepareStatement(
					"Select MAX( ANULACAO ) From " + usuario + ".CBPANULACAOLIQUIDA Where ANO = ? And EMPENHO = ? and LIQUIDACAO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, empenho);
			stmtAux.setInt(3, liquidacao);
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Max Anulação de Liqudiacao de  Empenhos");
			e.printStackTrace();
		}
		return max + 1;
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPANULACAOLIQUIDA where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Anulação de Liqudiacao de  Empenho");
			e.printStackTrace();
		}
	}
}
