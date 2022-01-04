package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.EmpFonteRecurso;

public class EmpFonteRecursoDAO {

	PreparedStatement stmtAux = null;

	public EmpFonteRecursoDAO() {
	}

	public void insere(Connection con, String usuario, EmpFonteRecurso obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPEMPFONTERECURSO "
					+ "( ANO, EMPENHO, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getVersaoRecurso());
			stmtAux.setInt(4, obj.getFonteRecurso());
			stmtAux.setInt(5, obj.getCaFixo());
			stmtAux.setInt(6, obj.getCaVariavel());
			stmtAux.setDouble(7, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Fonte Recurso Empenhos");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPEMPFONTERECURSO where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Fonte Recurso Empenhos");
			e.printStackTrace();
		}
	}
}
