package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.EntradasCaixa;

public class EntradasCaixaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public EntradasCaixaDAO() {
	}

	public void insere(Connection con, String usuario, EntradasCaixa obj) {

		try {

			int max = getMax(con, usuario, obj.getData());

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPENTRADASCAIXAS "
					+ "( DATA, ENTRADA, HISTORICO, ANOLANCTO, LANCAMENTO, TIPOGUIA, GUIA, TRANSFERENCIA, VALOR, EMPENHO, ANULACAO, ANULRECDEDUTORA, PARCELA, VERSAORECURSO, FONTERECURSO, REGULAFUNDEB ) values "
					+ "(  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getData());
			stmtAux.setInt(2, max);
			stmtAux.setString(3, obj.getHistorico());
			stmtAux.setDate(4, obj.getAnoLancto());
			stmtAux.setInt(5, obj.getLancamento());
			stmtAux.setString(6, obj.getTipoGuia());
			stmtAux.setInt(7, obj.getGuia());
			stmtAux.setInt(8, obj.getTransferencia());
			stmtAux.setDouble(9, obj.getValor());
			stmtAux.setInt(10, obj.getEmpenho());
			stmtAux.setInt(11, obj.getAnulacao());
			stmtAux.setInt(12, obj.getAnulRecDedutora());
			stmtAux.setInt(13, obj.getParcela());
			stmtAux.setInt(14, obj.getVersaoRecurso());
			stmtAux.setInt(15, obj.getFonteRecurso());
			stmtAux.setInt(16, obj.getRegulaFUNDEB());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Entradas Caixa");
			e.printStackTrace();
		}
	}
	
	private int getMax(Connection con, String usuario, Date data) {
		
		int max = 0;
		
		try {
			stmtAux = con.prepareStatement(
					"Select MAX( ENTRADA ) From " + usuario + ".CBPENTRADASCAIXAS Where DATA = ? ");
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

	public void deleteGuiaReceita(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPENTRADASCAIXAS Where ANOLANCTO = ? and GUIA > 0 ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Entradas Caixa");
			e.printStackTrace();
		}
	}
	
	public void deleteAnulacaoReceita(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPENTRADASCAIXAS Where ANOLANCTO = ? and ANULRECDEDUTORA > 0 ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Entradas Caixa");
			e.printStackTrace();
		}
	}

}
