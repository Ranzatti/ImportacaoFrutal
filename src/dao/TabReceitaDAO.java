package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.TabReceitas;

public class TabReceitaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public TabReceitaDAO() {
	}

	public void insere(Connection con, String usuario, TabReceitas obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPTABRECEITAS "
					+ "( ANO, RECEITA, TIPOCONTA, NOME, NATUREZA, ORIGEMNALEI, PERCENTUAL, RECDIVIDAATIVA, DEDUCAO) values " 
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setString(2, obj.getReceita());
			stmtAux.setString(3, obj.getTipoConta());
			stmtAux.setString(4, obj.getNome());
			stmtAux.setString(5, obj.getNatureza());
			stmtAux.setString(6, obj.getOrigemNaLei());
			stmtAux.setDouble(7, obj.getPercentual());
			stmtAux.setInt(8, obj.getRecDividaAtiva());
			stmtAux.setInt(9, obj.getDeducao());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Tab Receitas");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPTABRECEITAS ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Tab Receitas");
			e.printStackTrace();
		}
	}
	

	public void delete(Connection con, String usuario, Date ano, String tipoConta) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPTABRECEITAS Where ANO = ? and TIPOCONTA = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipoConta);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Tab Receitas");
			e.printStackTrace();
		}
	}
	
	public TabReceitas seleciona(Connection con, String usuario, Date ano, String receita) {

		TabReceitas tabReceita = new TabReceitas();

		try {
			stmtAux = con.prepareStatement(
					"Select TIPOCONTA, NOME, NATUREZA, ORIGEMNALEI, PERCENTUAL, RECDIVIDAATIVA, DEDUCAO "
					+ " From " + usuario + ".CBPTABRECEITAS "
					+ " Where ANO = ? "
					+ " And RECEITA= ? ");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, receita);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				tabReceita.setAno(ano);
				tabReceita.setReceita(receita);
				tabReceita.setTipoConta(rsAux.getString(1));
				tabReceita.setNome(rsAux.getString(2));
				tabReceita.setNatureza(rsAux.getString(3));
				tabReceita.setOrigemNaLei(rsAux.getString(4));
				tabReceita.setPercentual(rsAux.getDouble(5));
				tabReceita.setRecDividaAtiva(rsAux.getInt(6));
				tabReceita.setDeducao(rsAux.getInt(6));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Tab Receita");
			e.printStackTrace();
		}
		return tabReceita;
	}
}
