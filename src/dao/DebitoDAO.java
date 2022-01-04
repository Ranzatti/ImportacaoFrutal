package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Debito;

public class DebitoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public DebitoDAO() {
	}

	public void insere(Connection con, String usuario, Debito obj) {

		try {

			int max = getMax(con, usuario, obj.getFichaConta(), obj.getData());

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPDEBITO "
					+ "( FICHACONTA, BANCO, AGENCIA, CONTA, NUMERO, DATA, HISTORICO, ANOLANCTO, LANCAMENTO, TIPOANULACAOREC, ANULACAORECEITA, VALOR, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, FINALIDADE, AUTPAGTO, TRANSFERENCIA ) values "
					+ "(  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setInt(1, obj.getFichaConta());
			stmtAux.setInt(2, obj.getBanco());
			stmtAux.setString(3, obj.getAgencia());
			stmtAux.setString(4, obj.getConta());
			stmtAux.setInt(5, max);
			stmtAux.setDate(6, obj.getData());
			stmtAux.setString(7, obj.getHistorico());
			stmtAux.setDate(8, obj.getAnoLancto());
			stmtAux.setInt(9, obj.getLancamento());
			stmtAux.setString(10, obj.getTipoAnulacaoRec());
			stmtAux.setInt(11, obj.getAnulacaoReceita());
			stmtAux.setDouble(12, obj.getValor());
			stmtAux.setInt(13, obj.getVersaoRecurso());
			stmtAux.setInt(14, obj.getFonteRecurso());
			stmtAux.setInt(15, obj.getCaFixo());
			stmtAux.setInt(16, obj.getCaVariavel());
			stmtAux.setString(17, obj.getFinalidade());
			stmtAux.setInt(18, obj.getAutPagto());
			stmtAux.setInt(19, obj.getTransferencia());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Débitos Bancários");
			e.printStackTrace();
		}
	}
	
	private int getMax(Connection con, String usuario, int ficha, Date data) {
		
		int max = 0;
		
		try {
			stmtAux = con.prepareStatement(
					"Select MAX( NUMERO ) From " + usuario + ".CBPDEBITO Where FICHACONTA = ? And DATA = ? ");
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
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDEBITO ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Débitos Bancários");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDEBITO Where ANOLANCTO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Débitos Bancários");
			e.printStackTrace();
		}
	}

	public void deleteAnulacaoReceita(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDEBITO where ANOLANCTO = ? and ANULACAORECEITA > 0 ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Débitos Bancários");
			e.printStackTrace();
		}
	}
	
	public void deleteAutorizacao(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDEBITO where ANOLANCTO = ? and AUTPAGTO > 0");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Débitos Bancários");
			e.printStackTrace();
		}
	}
	
	public void deleteTransferencia(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDEBITO where ANOLANCTO = ? and TRANSFERENCIA > 0");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Débitos Bancários");
			e.printStackTrace();
		}
	}

}
