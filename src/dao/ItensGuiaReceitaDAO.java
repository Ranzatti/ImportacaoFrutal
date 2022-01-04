package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ItensGuiaReceita;

public class ItensGuiaReceitaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ItensGuiaReceitaDAO() {
	}

	public void insere(Connection con, String usuario, ItensGuiaReceita obj) {

		try {

			stmtAux = con.prepareStatement("INSERT INTO " + usuario + ".CBPITENSGUIA "
					+ "( ANO, TIPO, GUIA, FICHA, VERSAORECURSO, RECURSO, CAFIXO, CAVARIAVEL, RECEITA, VALOR ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setString(2, obj.getTipo());
			stmtAux.setInt(3, obj.getGuia());
			stmtAux.setInt(4, obj.getFicha());
			stmtAux.setInt(5, obj.getVersaoRecurso());
			stmtAux.setInt(6, obj.getFonteRecurso());
			stmtAux.setInt(7, obj.getCaFixo());
			stmtAux.setInt(8, obj.getCaVariavel());
			stmtAux.setString(9, obj.getReceita());
			stmtAux.setDouble(10, obj.getValor());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Itens Guia Receita");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPITENSGUIA Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Itens da Guia Receita");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano, String origem) {

		try {
			stmtAux = con.prepareStatement(
					"Delete from " + usuario + ".CBPITENSGUIA A "
					+ " Where EXISTS ( SELECT * from " + usuario + ".CBPGUIARECEITA B Where A.ANO = B.ANO AND A.TIPO = B.TIPO AND A.GUIA = B.NUMERO AND B.ANO = ? AND B.ORIGEM = ? )");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, origem);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Itens da Guia Receita");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano, String tipo, int numeroGuia) {

		try {
			stmtAux = con.prepareStatement(
					"Delete from " + usuario + ".CBPITENSGUIA A Where ANO = ? And TIPO= ? And GUIA = ?  )");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipo);
			stmtAux.setInt(3, numeroGuia);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Itens da Guia Receita");
			e.printStackTrace();
		}
	}

	public ItensGuiaReceita seleciona(Connection con, String usuario, Date ano, String tipo, int numeroGuia, int ficha, int FonteRecurso) {

		ItensGuiaReceita itensGuia = new ItensGuiaReceita();

		try {
			stmtAux = con.prepareStatement(
					"Select ANO, TIPO, GUIA, FICHA, VERSAORECURSO, RECURSO, CAFIXO, CAVARIAVEL, RECEITA, VALOR " 
					+ " From " + usuario + ".CBPITENSGUIA " 
					+ " Where ANO = ? " 
					+ " And TIPO = ? " 
					+ " And GUIA = ?"
					+ " And FICHA = ? "
					+ " And RECURSO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipo);
			stmtAux.setInt(3, numeroGuia);
			stmtAux.setInt(4, ficha);
			stmtAux.setInt(5, FonteRecurso);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				itensGuia.setAno(ano);
				itensGuia.setTipo(tipo);
				itensGuia.setGuia(numeroGuia);
				itensGuia.setFicha(ficha);
				itensGuia.setVersaoRecurso(rsAux.getInt(5));
				itensGuia.setFonteRecurso(FonteRecurso);
				itensGuia.setCaFixo(rsAux.getInt(7));
				itensGuia.setCaVariavel(rsAux.getInt(8));
				itensGuia.setReceita(rsAux.getString(9));
				itensGuia.setValor(rsAux.getDouble(10));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Guia Receita");
			e.printStackTrace();
		}
		return itensGuia;
	}
}
