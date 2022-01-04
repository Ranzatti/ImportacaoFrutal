package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.PagamentoEmpenho;

public class PagamentoEmpenhoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public PagamentoEmpenhoDAO() {
	}

	public void insere(Connection con, String usuario, PagamentoEmpenho obj) {

		try {
			
			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPPAGAMENTOS "
					+ "( ANO, EMPENHO, PAGAMENTO, VALORPARCELA, VALORPAGAMENTO, DATAPAGAMENTO, DATASUBEMPENHO, VENCIMENTO, HISTORICO, DESCONTO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getPagamento());
			stmtAux.setDouble(4, obj.getValorParcela());
			stmtAux.setDouble(5, obj.getValorPagamento());
			stmtAux.setDate(6, obj.getDataPagamento());
			stmtAux.setDate(7, obj.getDataSubEmpenho());
			stmtAux.setDate(8, obj.getVencimento());
			stmtAux.setString(9, obj.getHistorico());
			stmtAux.setDouble(10, obj.getDesconto());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Pagamento de Empenhos: " + obj.getEmpenho());
			e.printStackTrace();
		}
	}

	public int getMax(Connection con, String usuario, Date ano, int empenho) {
		
		int max = 0;

		try {
			stmtAux = con.prepareStatement(
					"Select MAX( PAGAMENTO ) From " + usuario + ".CBPPAGAMENTOS Where ANO = ? And EMPENHO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, empenho);
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Max Pagamento de Empenhos");
			e.printStackTrace();
		}
		return max + 1;
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPPAGAMENTOS where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Pagamento de Empenho");
			e.printStackTrace();
		}
	}
}
