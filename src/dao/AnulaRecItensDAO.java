package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.AnulaRecItens;

public class AnulaRecItensDAO {

	PreparedStatement stmtAux = null;

	public AnulaRecItensDAO() {
	}

	public void insere(Connection con, String usuario, AnulaRecItens obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CbPAnulaRecItens "
					+ "( ANO, TIPO, ANULACAO, FICHA, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setString(2, obj.getTipo());
			stmtAux.setInt(3, obj.getAnulacao());
			stmtAux.setInt(4, obj.getFicha());
			stmtAux.setInt(5, obj.getVersaoRecurso());
			stmtAux.setInt(6, obj.getFonteRecurso());
			stmtAux.setInt(7, obj.getCaFixo());
			stmtAux.setInt(8, obj.getCaVariavel());
			stmtAux.setDouble(9, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Itens Anulacao Receitas");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CbPAnulaRecItens ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Itens Anulacao Receitas");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CbPAnulaRecItens Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Itens Anulacao Receitas");
			e.printStackTrace();
		}
	}
}
