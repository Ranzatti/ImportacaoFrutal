package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Deposito;

public class DepositoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public DepositoDAO() {
	}

	public void insere(Connection con, String usuario, Deposito obj) {

		try {

			int max = getMax(con, usuario, obj.getFichaConta(), obj.getData());

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPDEPOSITO "
					+ "( FICHACONTA, BANCO, AGENCIA, CONTA, NUMERO, DATA, HISTORICO, ANOLANCTO, LANCAMENTO, ORIGEM, TIPOGUIA, GUIA, VALOR, EMPENHO, ANULACAO, PARCELA, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, TRANSFERENCIA ) values "
					+ "(  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setInt(1, obj.getFichaConta());
			stmtAux.setInt(2, obj.getBanco());
			stmtAux.setString(3, obj.getAgencia());
			stmtAux.setString(4, obj.getConta());
			stmtAux.setInt(5, max);
			stmtAux.setDate(6, obj.getData());
			stmtAux.setString(7, obj.getHistorico());
			stmtAux.setDate(8, obj.getAnoLancto());
			stmtAux.setInt(9, obj.getLancamento());
			stmtAux.setString(10, obj.getOrigem());
			stmtAux.setString(11, obj.getTipoGuia());
			stmtAux.setInt(12, obj.getGuia());
			stmtAux.setDouble(13, obj.getValor());
			stmtAux.setInt(14, obj.getEmpenho());
			stmtAux.setInt(15, obj.getAnulacao());
			stmtAux.setInt(16, obj.getParcela());
			stmtAux.setInt(17, obj.getVersaoRecurso());
			stmtAux.setInt(18, obj.getFonteRecurso());
			stmtAux.setInt(19, obj.getCaFixo());
			stmtAux.setInt(20, obj.getCaVariavel());
			stmtAux.setInt(21, obj.getTransferencia());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Deposito Bancários");
			e.printStackTrace();
		}
	}
	
	private int getMax(Connection con, String usuario, int ficha, Date data) {
		
		int max = 0;
		
		try {
			stmtAux = con.prepareStatement(
					"Select MAX( NUMERO ) From " + usuario + ".CBPDEPOSITO Where FICHACONTA = ? And DATA = ? ");
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
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDEPOSITO ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Deposito Bancários");
			e.printStackTrace();
		}
	}

	public void deleteTransferencia(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDEPOSITO where ANOLANCTO = ? and TRANSFERENCIA > 0");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Deposito Bancários");
			e.printStackTrace();
		}
	}

}
