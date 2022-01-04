package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.LiquidacaoEmpenho;

public class LiquidacaoEmpenhoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public LiquidacaoEmpenhoDAO() {
	}

	public void insere(Connection con, String usuario, LiquidacaoEmpenho obj) {

		try {
			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPLIQUIDACOES "
					+ "( ANO, EMPENHO, LIQUIDACAO, DATALIQUIDACAO, VALORLIQUIDACAO, HISTORICO, LIQUIDANTE, LANCAMENTO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getLiquidacao());
			stmtAux.setDate(4, obj.getDataLiquidacao());
			stmtAux.setDouble(5, obj.getValorLiquidacao());
			stmtAux.setString(6, obj.getHistorico());
			stmtAux.setString(7, obj.getLiquidante());
			stmtAux.setInt(8, obj.getLancamento());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Liquidação de Empenhos: " + obj.getEmpenho());
			e.printStackTrace();
		}
	}

	public int getMax(Connection con, String usuario, Date ano, int empenho) {
		
		int max = 0;

		try {
			stmtAux = con.prepareStatement(
					"Select MAX( LIQUIDACAO ) From " + usuario + ".CBPLIQUIDACOES Where ANO = ? And EMPENHO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, empenho);
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Max Liquidação de Empenhos");
			e.printStackTrace();
		}
		return max + 1;
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPLIQUIDACOES where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Liquidação de Empenho");
			e.printStackTrace();
		}
	}

	public LiquidacaoEmpenho seleciona(Connection con, String usuario, Date ano, int empenho, int liquidacao) {

		LiquidacaoEmpenho obj = new LiquidacaoEmpenho();

		try {
			stmtAux = con.prepareStatement(
					"Select DATALIQUIDACAO, VALORLIQUIDACAO, HISTORICO, LIQUIDANTE, LANCAMENTO "
							+ " From " + usuario + ".CBPLIQUIDACOES " 
							+ " Where ANO = ? " 
							+ " And	EMPENHO = ? "
							+ " And LIQUIDACAO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, empenho);
			stmtAux.setInt(3, liquidacao);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setAno(ano);
				obj.setEmpenho(empenho);
				obj.setLiquidacao(liquidacao);
				obj.setDataLiquidacao(rsAux.getDate(1));
				obj.setValorLiquidacao(rsAux.getDouble(2));
				obj.setHistorico(rsAux.getString(3));
				obj.setLiquidante(rsAux.getString(4));
				obj.setLancamento(rsAux.getInt(5));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Guia Receita");
			e.printStackTrace();
		}

		return obj;
	}
}
