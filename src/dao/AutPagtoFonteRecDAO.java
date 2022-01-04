package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.AutPagtoFonteRec;

public class AutPagtoFonteRecDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public AutPagtoFonteRecDAO() {
	}

	public void insere(Connection con, String usuario, AutPagtoFonteRec obj) {

		try {

			stmtAux = con.prepareStatement(
					"insert into " + usuario + ".CBPAUTPAGTOFONTREC " 
					+ "( ANO, AUTORIZACAO, FICHABANCO, TIPODOC, DOCUMENTO, PARCELA, SEQUENCIAL, VERSAORECURSO, FONTERECURSO, CAFIXO, CAVARIAVEL, VALOR ) values " 
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getAutorizacao());
			stmtAux.setInt(3, obj.getFichaBanco());
			stmtAux.setString(4, obj.getTipoDoc());
			stmtAux.setInt(5, obj.getDocumento());
			stmtAux.setInt(6, obj.getParcela());
			stmtAux.setInt(7, obj.getSequencial());
			stmtAux.setInt(8, obj.getVersaoRecurso());
			stmtAux.setInt(9, obj.getFonteRecurso());
			stmtAux.setInt(10, obj.getCaFixo());
			stmtAux.setInt(11, obj.getCaVariavel());
			stmtAux.setDouble(12, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Auto Pagto Fonte Rec " + obj.getAutorizacao());
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPAUTPAGTOFONTREC Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Auto Pagto Fonte Rec");
			e.printStackTrace();
		}
	}
}
