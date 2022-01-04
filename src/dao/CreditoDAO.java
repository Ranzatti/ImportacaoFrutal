package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Credito;

public class CreditoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public CreditoDAO() {
	}

	public void insere(Connection con, String usuario, Credito obj) {

		try {

			int max = getMax(con, usuario, obj.getFichaConta(), obj.getData());

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPCREDITO "
					+ "(FICHACONTA, BANCO, AGENCIA, CONTA, NUMERO, DATA, HISTORICO, LANCAMENTO, TIPOGUIA, GUIA, VALOR, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, ANOLANCTO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setInt(1, obj.getFichaConta());
			stmtAux.setInt(2, obj.getBanco());
			stmtAux.setString(3, obj.getAgencia());
			stmtAux.setString(4, obj.getConta());
			stmtAux.setInt(5, max);
			stmtAux.setDate(6, obj.getData());
			stmtAux.setString(7, obj.getHistorico());
			stmtAux.setInt(8, obj.getLancamento());
			stmtAux.setString(9, obj.getTipoGuia());
			stmtAux.setInt(10, obj.getGuia());
			stmtAux.setDouble(11, obj.getValor());
			stmtAux.setInt(12, obj.getVersaoRecurso());
			stmtAux.setInt(13, obj.getFonteRecurso());
			stmtAux.setInt(14, obj.getCaFixo());
			stmtAux.setInt(15, obj.getCaVariavel());
			stmtAux.setDate(16, obj.getAnoLancto());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Créditos Bancários");
			e.printStackTrace();
		}
	}
	
	private int getMax(Connection con, String usuario, int ficha, Date data) {
		
		int max = 0;
		
		try {
			stmtAux = con.prepareStatement(
					"Select MAX( NUMERO ) From " + usuario + ".CBPCREDITO Where FICHACONTA = ? And DATA = ? ");
			stmtAux.setInt(1, ficha);
			stmtAux.setDate(2, data);
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

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCREDITO ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Créditos Bancários");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCREDITO Where ANOLANCTO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Créditos Bancários");
			e.printStackTrace();
		}
	}
	
	public void deleteGuiaReceita(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCREDITO Where ANOLANCTO = ? and GUIA > 0 ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Créditos Bancários");
			e.printStackTrace();
		}
	}
	
	public void deleteAnuacaoReceita(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCREDITO Where ANOLANCTO = ? and ANULRECDEDUTORA > 0 ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Créditos Bancários");
			e.printStackTrace();
		}
	}
	
}
