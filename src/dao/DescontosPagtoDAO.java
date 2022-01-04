package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.DescontosPagto;

public class DescontosPagtoDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public DescontosPagtoDAO() {
	}

	public void insere(Connection con, String usuario, DescontosPagto obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPDESCONTOSPAGTO "
					+ "( ANO, EMPENHO, PARCELA, TIPOGUIA, GUIARECEITA  ) values " 
					+ "( ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpenho());
			stmtAux.setInt(3, obj.getParcela());
			stmtAux.setString(4, obj.getTipoGuia());
			stmtAux.setInt(5, obj.getGuiaReceita());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Descontos Pagto");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDESCONTOSPAGTO Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Descontos Pagto");
			e.printStackTrace();
		}
	}
}
