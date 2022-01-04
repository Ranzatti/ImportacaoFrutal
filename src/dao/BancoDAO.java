package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Banco;

public class BancoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public BancoDAO() {
	}

	public void insere(Connection con, String usuario, Banco obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPBANCOS "
					+ "( CODIGO, NOME ) values "
					+ "( ?, ? )");
			stmtAux.setInt(1, obj.getCodigo());
			stmtAux.setString(2, obj.getNome());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Bancos");
			e.printStackTrace();
		} 
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPBANCOS ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Bancos");
			e.printStackTrace();
		}
	}

	public Banco seleciona(Connection con, String usuario, int codigo) {

		Banco banco = new Banco();

		try {
			stmtAux = con.prepareStatement(
					"Select CODIGO, NOME From " + usuario + ".CBPBANCOS Where CODIGO = ? ");
			stmtAux.setInt(1, codigo);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				banco.setCodigo(rsAux.getInt(1));
				banco.setNome(rsAux.getString(2));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Bancos");
			e.printStackTrace();
		}

		return banco;

	}
}
