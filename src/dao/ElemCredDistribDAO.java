package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.ElemCredDistrib;

public class ElemCredDistribDAO {

	PreparedStatement stmtAux = null;

	public ElemCredDistribDAO() {
	}

	public void insere(Connection con, String usuario, ElemCredDistrib obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPELEMDISTCREDANU "
					+ "( ANO, CODIGO, FICHACREDITO, VERSAORECCREDITO, FONTERECCREDITO, CAFIXOCREDITO, CAVARIAVELCREDITO, FICHAANULACAO, VERSAORECANULACAO, FONTERECANULACAO, CAFIXOANULACAO, CAVARIAVELANULACAO, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getCodigo());
			stmtAux.setInt(3, obj.getFichaCredito());
			stmtAux.setInt(4, obj.getVersaoRecCredito());
			stmtAux.setInt(5, obj.getFonteRecursoCredito());
			stmtAux.setInt(6, obj.getCaFixoCredito());
			stmtAux.setInt(7, obj.getCaVariavelCredito());
			stmtAux.setInt(8, obj.getFichaAnulacao());
			stmtAux.setInt(9, obj.getVersaoRecAnulacao());
			stmtAux.setInt(10, obj.getFonteRecursoAnulacao());
			stmtAux.setInt(11, obj.getCaFixoAnulacao());
			stmtAux.setInt(12, obj.getCaVariavelAnulacao());
			stmtAux.setDouble(13, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Distribuição Creditos de Dotação");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMDISTCREDANU Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Distribuição Creditos de Dotação");
			e.printStackTrace();
		}
	}
}
