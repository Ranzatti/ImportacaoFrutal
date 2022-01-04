package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.ElemCredMes;

public class ElemCredMesDAO {

	PreparedStatement stmtAux = null;

	public ElemCredMesDAO() {
	}

	public void insere(Connection con, String usuario, ElemCredMes obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPELEMCREDMES "
					+ "( ANO, CREDITO, FICHA, VERSAORECURSO, RECURSO, CAFIXO, CAVARIAVEL, MES, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getCredito());
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
			System.err.println("Erro de Inclusão Elem Creditados Mes: " + obj.getCredito());
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMCREDMES Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Creditados Mes");
			e.printStackTrace();
		}
	}
}
