package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.DescontosOP;

public class DescontosOPDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public DescontosOPDAO() {
	}

	public void insere(Connection con, String usuario, DescontosOP obj) {

		try {

			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPDESCONTOSOP "
					+ "( ANO, OP, PARCELA, TIPOGUIA, GUIARECEITA  ) values " 
					+ "( ?, ?, ?, ?, ? ) ");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getOp());
			stmtAux.setInt(3, obj.getParcela());
			stmtAux.setString(4, obj.getTipoGuia());
			stmtAux.setInt(5, obj.getGuiaReceita());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Descontos OP");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPDESCONTOSOP Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Descontos OP");
			e.printStackTrace();
		}
	}
}
