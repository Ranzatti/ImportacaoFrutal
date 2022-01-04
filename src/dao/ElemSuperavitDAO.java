package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.ElemSuperavit;

public class ElemSuperavitDAO {

	PreparedStatement stmtAux = null;

	public ElemSuperavitDAO() {
	}

	public void insere(Connection con, String usuario, ElemSuperavit obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPCRDANLSUPERAVIT "
					+ "( ANO, CODIGO, FICHAPC, VALOR ) values "
					+ "( ?, ?, ?, ? ) ");

			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getCodigo());
			stmtAux.setInt(3, obj.getFichaPC());
			stmtAux.setDouble(4, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Elem Superavit");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCRDANLSUPERAVIT where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Superavit");
			e.printStackTrace();
		}
	}
}
