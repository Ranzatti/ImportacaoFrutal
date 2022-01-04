package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.ItensEmpenho;

public class ItensEmpenhoDAO {

	PreparedStatement stmtAux = null;

	public ItensEmpenhoDAO() {
	}

	public void insere(Connection con, String usuario, ItensEmpenho obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPITENSEMPENHO "
					+ "(ANO, EMPENHO, ITEM, TIPO, DESCRICAO, VALORUNITARIO, VALORTOTAL ) values "
					+ "( ?, ?, ?, ?, ?, ?, ? )  ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getItem());
			stmtAux.setString(4, obj.getTipo());
			stmtAux.setString(5, obj.getDescricao());
			stmtAux.setDouble(6, obj.getValorUnitario());
			stmtAux.setDouble(7, obj.getValorTotal());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Itens de Empenhos");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPITENSEMPENHO where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Fv");
			e.printStackTrace();
		}
	}
}
