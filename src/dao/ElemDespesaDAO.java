package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ElemDespesa;

public class ElemDespesaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ElemDespesaDAO() {
	}

	public void insere(Connection con, String usuario, ElemDespesa obj) {

		try {

			stmtAux = con.prepareStatement("Insert into " + usuario + ".CBPELEMDESPESA "
					+ "( ANO, EMPRESA, FICHA, ORGAO, UNIDADE, SUBUNIDADE, FUNCAO, SUBFUNCAO, PROGRAMA, PROJATIV, CATEGORIA, GRUPO, MODALIDADE, ELEMENTO, DESDOBRAMENTO, ORCADO ) values "
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			stmtAux.setDate(1, obj.getAno());
			stmtAux.setInt(2, obj.getEmpresa());
			stmtAux.setInt(3, obj.getFicha());
			stmtAux.setString(4, obj.getOrgao());
			stmtAux.setString(5, obj.getUnidade());
			stmtAux.setString(6, obj.getSubUnidade());
			stmtAux.setString(7, obj.getFuncao());
			stmtAux.setString(8, obj.getSubFuncao());
			stmtAux.setString(9, obj.getPrograma());
			stmtAux.setString(10, obj.getProjAtiv());
			stmtAux.setString(11, obj.getCategoria());
			stmtAux.setString(12, obj.getGrupo());
			stmtAux.setString(13, obj.getModalidade());
			stmtAux.setString(14, obj.getElemento());
			stmtAux.setString(15, obj.getDesdobramento());
			stmtAux.setDouble(16, obj.getOrcado());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Elem Despesa");
			e.printStackTrace();
		}
	}
	
	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMDESPESA ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Despesa");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario, Date ano) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPELEMDESPESA Where ANO = ?");
			stmtAux.setDate(1, ano);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Elem Despesa");
			e.printStackTrace();
		}
	}
	
	public ElemDespesa seleciona(Connection con, String usuario, Date ano, int ficha) {

		ElemDespesa obj = new ElemDespesa();

		try {
			stmtAux = con.prepareStatement(
					"Select EMPRESA, ORGAO, UNIDADE, SUBUNIDADE, FUNCAO, SUBFUNCAO, PROGRAMA, PROJATIV, CATEGORIA, GRUPO, MODALIDADE, ELEMENTO, DESDOBRAMENTO, ORCADO " 
					+ "From " + usuario	+ ".CBPELEMDESPESA " 
					+ "Where ANO = ? " 
					+ "And FICHA = ? " );
			stmtAux.setDate(1, ano);
			stmtAux.setInt(2, ficha);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				obj.setAno(ano);
				obj.setEmpresa(rsAux.getInt(1));
				obj.setFicha(ficha);
				obj.setOrgao(rsAux.getString(2));
				obj.setUnidade(rsAux.getString(3));
				obj.setSubUnidade(rsAux.getString(4));
				obj.setFuncao(rsAux.getString(5));
				obj.setSubFuncao(rsAux.getString(6));
				obj.setPrograma(rsAux.getString(7));
				obj.setProjAtiv(rsAux.getString(8));
				obj.setCategoria(rsAux.getString(9));
				obj.setGrupo(rsAux.getString(10));
				obj.setModalidade(rsAux.getString(11));
				obj.setElemento(rsAux.getString(12));
				obj.setDesdobramento(rsAux.getString(13));
				obj.setOrcado(rsAux.getDouble(14));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção Elem Despesa");
			e.printStackTrace();
		}
		return obj;
	}

}
