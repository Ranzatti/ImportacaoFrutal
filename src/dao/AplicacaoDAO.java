package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Aplicacao;

public class AplicacaoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public AplicacaoDAO() {
	}

	public void insere(Connection con, String usuario, Aplicacao obj) {

		try {

			int max = getMax(con, usuario, obj.getFichaConta(), obj.getData());

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPAPLICACAO "
					+ "( FICHACONTA, BANCO, AGENCIA, CONTA, NUMERO, DATA, HISTORICO, ANOFATO, FATO, VALOR, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, TIPOAPLICFINAN ) values "
					+ "(  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setInt(1, obj.getFichaConta());
			stmtAux.setInt(2, obj.getBanco());
			stmtAux.setString(3, obj.getAgencia());
			stmtAux.setString(4, obj.getConta());
			stmtAux.setInt(5, max);
			stmtAux.setDate(6, obj.getData());
			stmtAux.setString(7, obj.getHistorico());
			stmtAux.setDate(8, obj.getAnoFato());
			stmtAux.setInt(9, obj.getFato());
			stmtAux.setDouble(10, obj.getValor());
			stmtAux.setInt(11, obj.getVersaoRecurso());
			stmtAux.setInt(12, obj.getFonteRecurso());
			stmtAux.setInt(13, obj.getCaFixo());
			stmtAux.setInt(14, obj.getCaVariavel());
			stmtAux.setInt(15, obj.getTipoAplicFinan());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Aplicação Bancários");
			e.printStackTrace();
		}
	}
	
	private int getMax(Connection con, String usuario, int ficha, Date data) {
		
		int max = 0;
		
		try {
			stmtAux = con.prepareStatement(
					"Select MAX( NUMERO ) From " + usuario + ".CBPAPLICACAO Where FICHACONTA = ? And DATA = ? ");
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
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPAPLICACAO ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Aplicação Bancários");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPAPLICACAO where ANOFATO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Aplicação Bancários");
			e.printStackTrace();
		}
	}

}
