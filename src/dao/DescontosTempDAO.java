package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.DescontosTemp;

public class DescontosTempDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public DescontosTempDAO() {
	}

	public void insere(Connection con, String usuario, DescontosTemp obj) {

		try {

			stmtAux = con.prepareStatement(
					"insert into " + usuario + ".CBPDESCONTOSTEMP "
					+ "( ANO, TIPODOC, DOCUMENTO, PARCELA, TIPODESC, FICHA, VERSAORECURSO, RECURSO, CAFIXO, CAVARIAVEL, CONTRIBUINTE, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setString(2, obj.getTipoDoc());
			stmtAux.setInt(3, obj.getDocumento());
			stmtAux.setInt(4, obj.getParcela());
			stmtAux.setString(5, obj.getTipoDesc());
			stmtAux.setInt(6, obj.getFicha());
			stmtAux.setDouble(7, obj.getVersaoRecurso());
			stmtAux.setInt(8, obj.getFonteRecurso());
			stmtAux.setInt(9, obj.getCaFixo());
			stmtAux.setInt(10, obj.getCaVariavel());
			stmtAux.setInt(11, obj.getContribuinte());
			stmtAux.setDouble(12, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Descontos Temp");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement(
					"Delete from " + usuario + ".CBPDESCONTOSTEMP Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Descontos Temp");
			e.printStackTrace();
		}
	}
	

	public void delete(Connection con, String usuario, Date ano, String tipoDoc) {

		try {
			stmtAux = con.prepareStatement(
					"Delete From " + usuario + ".CBPDESCONTOSTEMP Where ANO = ? And TIPODOC = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipoDoc);
			stmtAux.executeQuery();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão DescontoTemp");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano, String tipoDoc, int documento, int parcela) {

		try {
			stmtAux = con.prepareStatement(
					"Delete From " + usuario + ".CBPDESCONTOSTEMP Where ANO = ? And TIPODOC = ? And DOCUMENTO = ? And PARCELA = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipoDoc);
			stmtAux.setInt(3, documento);
			stmtAux.setInt(4, parcela);
			stmtAux.executeQuery();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão DescontoTemp");
			e.printStackTrace();
		}
	}
}
