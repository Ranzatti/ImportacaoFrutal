package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.CreditoDotacao;

public class CreditoDotacaoDAO {

	PreparedStatement stmtAux = null;

	public CreditoDotacaoDAO() {
	}

	public void insere(Connection con, String usuario, CreditoDotacao obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPCRDANL "
					+ "( ANO, CODIGO, DATACREDITO, TIPO, HISTORICO, NATUREZA, USAORCAMENTO, LEI, TRANSPOSICAO, TRANSPOSICAOFONTE ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getCodigo());
			stmtAux.setDate(3, obj.getDataCredito());
			stmtAux.setString(4, obj.getTipo());
			stmtAux.setString(5, obj.getHistorico());
			stmtAux.setString(6, obj.getNatureza());
			stmtAux.setString(7, obj.getUsaOrcamento());
			stmtAux.setInt(8, obj.getLei());
			stmtAux.setInt(9, obj.getTransposicao());
			stmtAux.setInt(10, obj.getTransposicaoFonte());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Creditos de Dotação");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCRDANL where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Creditos de Dotação");
			e.printStackTrace();
		}
	}
}
