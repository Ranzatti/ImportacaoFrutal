package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.Empenhos;

public class EmpenhosDAO {

	PreparedStatement stmtAux = null;

	public EmpenhosDAO() {
	}

	public void insere(Connection con, String usuario, Empenhos obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPEMPENHOS "
					+ "( ANO, NUMERO, TIPO, FICHA, DATAEMPENHO, VENCIMENTO, FORNECEDOR, VALOREMPENHO, DESDOBRAMENTO, DESCONTO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getNumero());
			stmtAux.setString(3, obj.getTipo());
			stmtAux.setInt(4, obj.getFicha());
			stmtAux.setDate(5, obj.getDataEmpenho());
			stmtAux.setDate(6, obj.getVencimento());
			stmtAux.setInt(7, obj.getFornecedor());
			stmtAux.setDouble(8, obj.getValorEmpenho());
			stmtAux.setString(9, obj.getDesdobramento());
			stmtAux.setDouble(10, obj.getDesconto());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Empenhos");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPEMPENHOS where ANO = ? ");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Empenhos");
			e.printStackTrace();
		}
	}
}
