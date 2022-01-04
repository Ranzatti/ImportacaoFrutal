package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.CodigoAplicacao;

public class CodigoAplicacaoDAO {

	PreparedStatement stmtAux = null;

	public CodigoAplicacaoDAO() {
	}

	public void insere(Connection con, String usuario, CodigoAplicacao obj) {

		try {

			stmtAux = con.prepareStatement(
					"Insert into " + usuario + ".CBPCODIGOAPLICACAO2 "
					+ "( CODAPLICACAO, FIXO, NOME, CODIGOGERAL, CODIGO ) values "
					+ "( ?, ?, ?, ?, ? ) ");
			stmtAux.setInt(1, obj.getCodAplicacao());
			stmtAux.setInt(2, obj.getFixo());
			stmtAux.setString(3, obj.getNome());
			stmtAux.setInt(4, obj.getCodigoGeral());
			stmtAux.setString(5, obj.getCodigo());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Codigo Aplicacao");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCODIGOAPLICACAO2 ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Codigo Aplicacao");
			e.printStackTrace();
		}
	}
}
