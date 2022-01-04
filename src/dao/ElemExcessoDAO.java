package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.ElemExcesso;

public class ElemExcessoDAO {

	PreparedStatement stmtAux = null;

	public ElemExcessoDAO() {
	}

	public void insere(Connection con, String usuario, ElemExcesso obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPCRDANLEXCESSO "
					+ "( ANO, CODIGO, FICHAPC, VALOR ) values "
					+ "( ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getCodigo());
			stmtAux.setInt(3, obj.getFichaPC());
			stmtAux.setDouble(4, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Elem Excesso");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCRDANLEXCESSO Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Excesso");
			e.printStackTrace();
		}
	}
}
