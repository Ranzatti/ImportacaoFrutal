package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.ElemAnulMes;

public class ElemAnulMesDAO {

	PreparedStatement stmtAux = null;

	public ElemAnulMesDAO() {
	}

	public void insere(Connection con, String usuario, ElemAnulMes obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPELEMANULMES "
					+ "( ANO, ANULADO, FICHA, VERSAORECURSO, RECURSO, CAFIXO, CAVARIAVEL, MES, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getAnulado());
			stmtAux.setInt(3, obj.getFicha());
			stmtAux.setInt(4, obj.getVersaoRecurso());
			stmtAux.setInt(5, obj.getRecurso());
			stmtAux.setInt(6, obj.getCaFixo());
			stmtAux.setInt(7, obj.getCaVariavel());
			stmtAux.setInt(8, obj.getMes());
			stmtAux.setDouble(9, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Elem Anulados Mes");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMANULMES Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Anulados Mes");
			e.printStackTrace();
		}
	}
}
