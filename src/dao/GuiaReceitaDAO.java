package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.GuiaReceita;

public class GuiaReceitaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public GuiaReceitaDAO() {
	}

	public void insere(Connection con, String usuario, GuiaReceita obj) {

		try {

			stmtAux = con.prepareStatement("INSERT INTO " + usuario + ".CBPGUIARECEITA "
					+ "( ANO, TIPO, NUMERO, CONTRIBUINTE, DATAGUIA, HISTORICO, VENCIMENTO, RECEBIMENTO, ORIGEM, LANCAMENTO, AUTPAGTO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setString(2, obj.getTipo());
			stmtAux.setInt(3, obj.getNumero());
			stmtAux.setInt(4, obj.getContribuinte());
			stmtAux.setDate(5, obj.getData());
			stmtAux.setString(6, obj.getHistorico());
			stmtAux.setDate(7, obj.getVencimento());
			stmtAux.setDate(8, obj.getRecebimento());
			stmtAux.setString(9, obj.getOrigem());
			stmtAux.setInt(10, obj.getLancamento());
			stmtAux.setInt(11, obj.getAutPagto());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Guia Receita");
			e.printStackTrace();
		}
	}
	
	public int getMax(Connection con, String usuario, Date ano, String tipoGuia) {
		
		int max = 0;

		try {
			stmtAux = con.prepareStatement(
					"Select MAX( NUMERO ) From " + usuario + ".CBPGUIARECEITA Where ANO = ? And TIPO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipoGuia );
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Max Guia Receita");
			e.printStackTrace();
		}
		return max + 1;
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPGUIARECEITA ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Guia Receita");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPGUIARECEITA Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Guia Receita");
			e.printStackTrace();
		}
	}


	public void delete(Connection con, String usuario, Date ano, String origem) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPGUIARECEITA Where ANO = ? and ORIGEM = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, origem);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Guia Receita");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano, String tipo, int numeroGuia) {

		try {
			stmtAux = con.prepareStatement(
					"Delete from " + usuario + ".CBPGUIARECEITA Where ANO = ? And TIPO= ? And NUMERO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipo);
			stmtAux.setInt(3, numeroGuia);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Guia Receita");
			e.printStackTrace();
		}
	}

	public GuiaReceita seleciona(Connection con, String usuario, Date ano, String tipo, int numeroGuia) {

		GuiaReceita guiaRec = new GuiaReceita();

		try {
			stmtAux = con.prepareStatement(
					"Select CONTRIBUINTE, DATAGUIA, HISTORICO, VENCIMENTO, RECEBIMENTO, LANCAMENTO, ORIGEM, AUTPAGTO "
					+ " From " + usuario + ".CBPGUIARECEITA "
					+ " Where ANO = ? "
					+ " And TIPO= ? "
					+ " And NUMERO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipo);
			stmtAux.setInt(3, numeroGuia);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				guiaRec.setAno(ano);
				guiaRec.setTipo(tipo);
				guiaRec.setNumero(numeroGuia);
				guiaRec.setContribuinte(rsAux.getInt(1));
				guiaRec.setData(rsAux.getDate(2));
				guiaRec.setHistorico(rsAux.getString(3));
				guiaRec.setVencimento(rsAux.getDate(4));
				guiaRec.setRecebimento(rsAux.getDate(5));
				guiaRec.setLancamento(rsAux.getInt(6));
				guiaRec.setOrigem(rsAux.getString(7));
				guiaRec.setAutPagto(rsAux.getInt(8));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Guia Receita");
			e.printStackTrace();
		}

		return guiaRec;
	}
}
