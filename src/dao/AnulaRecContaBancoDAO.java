package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.AnulaRecContaBanco;

public class AnulaRecContaBancoDAO {

	PreparedStatement stmtAux = null;

	public AnulaRecContaBancoDAO() {
	}

	public void insere(Connection con, String usuario, AnulaRecContaBanco obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPANULRECCONTABAN "
					+ "( ANO, TIPO, ANULACAO, FICHABANCO, FICHARECEITA, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, VERSAORECURSOBANCO, FONTERECURSOBANCO, CAFIXOBANCO, CAVARIAVELBANCO, ITEM, VALOR ) values"
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setString(2, obj.getTipo());
			stmtAux.setInt(3, obj.getAnulacao());
			stmtAux.setInt(4, obj.getFichaBanco());
			stmtAux.setInt(5, obj.getFichaReceita());
			stmtAux.setInt(6, obj.getVersaoRecurso());
			stmtAux.setInt(7, obj.getFonteRecurso());
			stmtAux.setInt(8, obj.getCaFixo());
			stmtAux.setInt(9, obj.getCaVariavel());
			stmtAux.setInt(10, obj.getVersaoRecursoBanco());
			stmtAux.setInt(11, obj.getFonteRecursoBanco());
			stmtAux.setInt(12, obj.getCaFixoBanco());
			stmtAux.setInt(13, obj.getCaVariavelBanco());
			stmtAux.setInt(14, obj.getItem());
			stmtAux.setDouble(15, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Rec Anulacao Receitas");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPANULRECCONTABAN ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Rec Anulacao Receitas");
			e.printStackTrace();
		}
	}


	public void delete(Connection con, String usuario, Date ano ) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPANULRECCONTABAN Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Rec Anulacao Receitas");
			e.printStackTrace();
		}
	}
}
