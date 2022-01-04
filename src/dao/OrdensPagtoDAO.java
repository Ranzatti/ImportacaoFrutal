package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.OrdensPagto;

public class OrdensPagtoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public OrdensPagtoDAO() {
	}

	public void insere(Connection con, String usuario, OrdensPagto obj) {

		try {

			stmtAux = con.prepareStatement(
					"insert into " + usuario + ".CBPORDENSPAGTO "
					+ "( ANO, NUMERO, FICHA, FORNECEDOR, DATAOP, HISTORICO, VALOROP, VENCIMENTO, STATUS, ANORESTOS, EMPENHORESTOS, PARCELARESTOS, VALORP, VALORNP, DESCONTO, ANOLANCTO, LANCAMENTO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getNumero());
			stmtAux.setInt(3, obj.getFicha());
			stmtAux.setInt(4, obj.getFornecedor());
			stmtAux.setDate(5, obj.getDataOP());
			stmtAux.setString(6, obj.getHistorico());
			stmtAux.setDouble(7, obj.getValor());
			stmtAux.setDate(8, obj.getVencimento());
			stmtAux.setString(9, obj.getStatus());
			stmtAux.setDate(10, obj.getAnoRestos());
			stmtAux.setInt(11, obj.getEmpenhoRestos());
			stmtAux.setInt(12, obj.getParcelaRestos());
			stmtAux.setDouble(13, obj.getValorP());
			stmtAux.setDouble(14, obj.getValorNP());
			stmtAux.setDouble(15, obj.getDesconto());
			stmtAux.setDate(16, obj.getAnoLancto());
			stmtAux.setInt(17, obj.getLancamento());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Ordens Pagto");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPORDENSPAGTO Where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Ordens Pagto");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano, int numero) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPORDENSPAGTO Where ANO = ? and NUMERO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, numero);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Ordens Pagto");
			e.printStackTrace();
		}
	}
	
	public void deleteRestos(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPORDENSPAGTO Where ANO = ? AND ANORESTOS IS NOT NULL ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Ordens Pagto");
			e.printStackTrace();
		}
	}


	public OrdensPagto seleciona(Connection con, String usuario, Date ano, int numero) {

		OrdensPagto obj = new OrdensPagto();

		try {
			stmtAux = con.prepareStatement(
					"Select FICHA, FORNECEDOR, DATAOP, HISTORICO, VALOROP, VENCIMENTO, STATUS, ANORESTOS, EMPENHORESTOS, PARCELARESTOS, VALORP, VALORNP, DESCONTO, ANOLANCTO, LANCAMENTO " 
							+ "From " + usuario + ".CBPORDENSPAGTO "
							+ "Where ANO = ? " 
							+ "And NUMERO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, numero);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setAno(ano);
				obj.setNumero(numero);
				obj.setFicha(rsAux.getInt(1));
				obj.setFornecedor(rsAux.getInt(2));
				obj.setDataOP(rsAux.getDate(3));
				obj.setHistorico(rsAux.getString(4).trim());
				obj.setValor(rsAux.getDouble(5));
				obj.setVencimento(rsAux.getDate(6));
				obj.setStatus(rsAux.getString(7).trim());
				obj.setAnoRestos(rsAux.getDate(8));
				obj.setEmpenhoRestos(rsAux.getInt(9));
				obj.setParcelaRestos(rsAux.getInt(10));
				obj.setValorP(rsAux.getDouble(11));
				obj.setValorNP(rsAux.getDouble(12));
				obj.setDesconto(rsAux.getDouble(13));
				obj.setAnoLancto(rsAux.getDate(14));
				obj.setLancamento(rsAux.getInt(15));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Ordens Pagto");
			e.printStackTrace();
		}

		return obj;
	}
	
	public int getMax(Connection con, String usuario, Date ano) {
		
		int max = 0;
		
		try {
			stmtAux = con.prepareStatement(
					"Select MAX( NUMERO ) From " + usuario + ".CBPORDENSPAGTO Where ANO = ? ");
			stmtAux.setDate(1, ano);
			rsAux = stmtAux.executeQuery();
			rsAux.next();
			max = rsAux.getInt(1);
			rsAux.close();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Max Ordens Pagto");
			e.printStackTrace();
		}
		return max + 1;
	}
}
