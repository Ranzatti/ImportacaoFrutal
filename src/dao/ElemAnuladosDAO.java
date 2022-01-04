package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.ElemAnulados;

public class ElemAnuladosDAO {

	PreparedStatement stmtAux = null;

	public ElemAnuladosDAO() {
	}

	public void insere(Connection con, String usuario, ElemAnulados obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPELEMANULADOS "
					+ "( ANO, CODIGO, FICHA, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getCodigo());
			stmtAux.setInt(3, obj.getFicha());
			stmtAux.setInt(4, obj.getVersaoRecurso());
			stmtAux.setInt(5, obj.getFonteRecurso());
			stmtAux.setInt(6, obj.getCaFixo());
			stmtAux.setInt(7, obj.getCaVariavel());
			stmtAux.setDouble(8, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Elem Anulados");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMANULADOS Where ANO = ?  ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Anulados");
			e.printStackTrace();
		}
	}
}
