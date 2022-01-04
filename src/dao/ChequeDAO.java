package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Cheque;

public class ChequeDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ChequeDAO() {
	}

	public void insere(Connection con, String usuario, Cheque obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPCHEQUE "
					+ "( FICHACONTA, BANCO, AGENCIA, CONTA, NUMERO, DATA, HISTORICO, ANOLANCTO, LANCAMENTO, ANULACAORECEITA, VALOR, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, FINALIDADE, AUTPAGTO, DATAEMISSAO, DATABAIXA, TRANSFERENCIA ) values "
					+ "(  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setInt(1, obj.getFichaConta());
			stmtAux.setInt(2, obj.getBanco());
			stmtAux.setString(3, obj.getAgencia());
			stmtAux.setString(4, obj.getConta());
			stmtAux.setInt(5, obj.getNumero());
			stmtAux.setDate(6, obj.getData());
			stmtAux.setString(7, obj.getHistorico());
			stmtAux.setDate(8, obj.getAnoLancto());
			stmtAux.setInt(9, obj.getLancamento());
			stmtAux.setInt(10, obj.getAnulacaoReceita());
			stmtAux.setDouble(11, obj.getValor());
			stmtAux.setInt(12, obj.getVersaoRecurso());
			stmtAux.setInt(13, obj.getFonteRecurso());
			stmtAux.setInt(14, obj.getCaFixo());
			stmtAux.setInt(15, obj.getCaVariavel());
			stmtAux.setString(16, obj.getFinalidade());
			stmtAux.setInt(17, obj.getAutPagto());
			stmtAux.setDate(18, obj.getDataEmissao());
			stmtAux.setDate(19, obj.getDataBaixa());
			stmtAux.setInt(20, obj.getTransferencia());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Cheques");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCHEQUE ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Cheques");
			e.printStackTrace();
		}
	}
	
	public void deleteAutorizacao(Connection con, String usuario, Date ano) {
		
		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCHEQUE where ANOLANCTO = ? and AUTPAGTO > 0 ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Cheques");
			e.printStackTrace();
		}
	}


}
