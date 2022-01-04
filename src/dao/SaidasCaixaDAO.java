package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.SaidasCaixa;

public class SaidasCaixaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public SaidasCaixaDAO() {
	}

	public void insere(Connection con, String usuario, SaidasCaixa obj) {

		try {
			
			int max = getMax(con, usuario, obj.getData());

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPSAIDASCAIXAS "
					+ "( DATA, SAIDA, HISTORICO, ANOLANCTO, LANCAMENTO, ANULACAORECEITA, VALOR, TRANSFERENCIA, AUTPAGTO, VERSAORECURSO, FONTERECURSO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getData());
			stmtAux.setInt(2, max);
			stmtAux.setString(3, obj.getHistorico());
			stmtAux.setDate(4, obj.getAnoLancto());
			stmtAux.setInt(5, obj.getLancamento());
			stmtAux.setInt(6, obj.getAnulacaoReceita());
			stmtAux.setDouble(7, obj.getValor());
			stmtAux.setInt(8, obj.getTransferencia());
			stmtAux.setInt(9, obj.getAutPagto());
			stmtAux.setInt(10, obj.getVersaoRecurso());
			stmtAux.setInt(11, obj.getFonteRecurso());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Saidas Caixa");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPSAIDASCAIXAS ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Saidas Caixa");
			e.printStackTrace();
		}
	}
	
	public void deleteAnulacaoReceita(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPSAIDASCAIXAS Where ANOLANCTO = ? and ANULACAORECEITA > 0");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Saidas Caixa");
			e.printStackTrace();
		}
	}


	private int getMax(Connection con, String usuario, Date data) {
		
		int max = 0;

		try {
			stmtAux = con.prepareStatement(
					"Select MAX( SAIDA ) From " + usuario + ".CBPSAIDASCAIXAS Where DATA = ? ");
			stmtAux.setDate(1, data);
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max + 1;
	}
}
