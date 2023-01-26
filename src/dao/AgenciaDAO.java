package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Agencia;

public class AgenciaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public AgenciaDAO() {
	}

	public void insere(Connection con, String usuario, Agencia obj) {

		try {

			stmtAux = con.prepareStatement(	"insert into " + usuario + ".CBPAGENCIAS "
					+ "( BANCO, CODIGO, NOME ) values "
					+ "( ?, ?, ? )");
			stmtAux.setInt(1, obj.getBanco());
			stmtAux.setString(2, obj.getCodigo());
			stmtAux.setString(3, obj.getNome());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclus�o Agencia");
			e.printStackTrace();
		} 
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPAGENCIAS ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclus�o Agencia");
			e.printStackTrace();
		}
	}

	public Agencia seleciona(Connection con, String usuario, int banco, String agenciaCodigo) {

		Agencia agencia = new Agencia();

		try {
			stmtAux = con.prepareStatement(
					"Select BANCO, CODIGO, NOME From " + usuario + ".CBPAGENCIAS Where BANCO = ? And CODIGO = ?");
			stmtAux.setInt(1, banco);
			stmtAux.setString(2, agenciaCodigo);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				agencia.setBanco(rsAux.getInt(1));
				agencia.setCodigo(rsAux.getString(2));
				agencia.setNome(rsAux.getString(3));
			} 
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Sele��o de Agencia");
			e.printStackTrace();
		}

		return agencia;
	}
}
