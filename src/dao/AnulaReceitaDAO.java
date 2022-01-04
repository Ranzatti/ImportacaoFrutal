package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.AnulaReceita;

public class AnulaReceitaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public AnulaReceitaDAO() {
	}

	public void insere(Connection con, String usuario, AnulaReceita obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CbPAnulaReceitas "
					+ "( ANO, TIPO, ANULACAO, DATAANULACAO, LANCAMENTO, HISTORICO, DEDUTORA ) values "
					+ "( ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setString(2, obj.getTipo());
			stmtAux.setInt(3, obj.getAnulacao());
			stmtAux.setDate(4, obj.getDataAnulacao());
			stmtAux.setInt(5, obj.getLancamento());
			stmtAux.setString(6, obj.getHistorico());
			stmtAux.setInt(7, obj.getDedutora());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Anulacao Receitas");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CbPAnulaReceitas ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Anulacao Receitas");
			e.printStackTrace();
		}
	}
	

	public void delete(Connection con, String usuario, Date ano ) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CbPAnulaReceitas Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Anulacao Receitas");
			e.printStackTrace();
		}
	}

	public AnulaReceita seleciona(Connection con, String usuario, Date ano, String tipo, int anulacao) {

		AnulaReceita anulaReceita = new AnulaReceita();

		try {
			stmtAux = con.prepareStatement(
					"Select HISTORICO, DATAANULACAO, LANCAMENTO, DEDUTORA" 
					+ " From " + usuario + ".CBPANULARECEITAS "
					+ " Where ANO = ? "
					+ " And TIPO = ? "
					+ " And ANULACAO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.setString(2, tipo);
			stmtAux.setInt(3, anulacao);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				anulaReceita.setAno(ano);
				anulaReceita.setTipo(tipo);
				anulaReceita.setAnulacao(anulacao);
				anulaReceita.setHistorico(rsAux.getString(1));
				anulaReceita.setDataAnulacao(rsAux.getDate(2));
				anulaReceita.setLancamento(rsAux.getInt(3));
				anulaReceita.setDedutora(rsAux.getInt(4));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Anulação Receita");
			e.printStackTrace();
		}

		return anulaReceita;
	}
}
