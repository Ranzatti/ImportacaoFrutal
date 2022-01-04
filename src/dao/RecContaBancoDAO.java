package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.RecContaBanco;

public class RecContaBancoDAO {

	PreparedStatement stmtAux = null;

	public RecContaBancoDAO() {
	}

	public void insere(Connection con, String usuario, RecContaBanco obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPRECCONTABANCO "
					+ "( ANO, TIPO, GUIA, FICHABANCO, FICHA, VERSAORECURSO, RECURSO, CAFIXO, CAVARIAVEL, VERSAORECURSOBANCO, FONTERECURSOBANCO, CAFIXOBANCO, CAVARIAVELBANCO, ITEM, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setString(2, obj.getTipo());
			stmtAux.setInt(3, obj.getGuia());
			stmtAux.setInt(4, obj.getFichaBanco());
			stmtAux.setInt(5, obj.getFicha());
			stmtAux.setInt(6, obj.getVersaoRecurso());
			stmtAux.setInt(7, obj.getRecurso());
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
			System.err.println("Erro de Inclusão Rec Conta Banco");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPRECCONTABANCO Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Rec Conta Banco");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano, String tipo, int numeroGuia) {

		try {
			stmtAux = con.prepareStatement(
					"Delete from " + usuario + ".CBPRECCONTABANCO A Where ANO = ? And TIPO= ? And GUIA = ?  )");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipo);
			stmtAux.setInt(3, numeroGuia);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Rec Conta Banco");
			e.printStackTrace();
		}
	}
}
