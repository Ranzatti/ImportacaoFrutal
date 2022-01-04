package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.DescontosTemp;
import bean.FonteRecursoFrutal;

public class Frutal {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public String historico(Connection con, String usuario, int anoSonner, String codigo) {

		codigo = codigo.trim();

		String historico = "";

		if (codigo == null) {
			historico = "";
		} else {
			try {
				int i = 0;
				stmtAux = con.prepareStatement(
						"Select HISTORICO From " + usuario + ".HISTORIC_" + anoSonner + " Where TRIM(CODIGO) = ? Order by LINHA ");
				stmtAux.setString(1, codigo);
				rsAux = stmtAux.executeQuery();
				while (rsAux.next()) {
					if(i>0) {
						historico += " ";
					}
					historico += rsAux.getString(1).trim();
					i++;
				}
				stmtAux.close();
				rsAux.close();
			} catch (SQLException e) {
				System.err.println("Erro de Sele��o de Historico");
				e.printStackTrace();
			}
		}
		return historico;
	}

	public int fichaDespesa(Connection con, String usuario, Date anoAtual, String chave, int ficha) {

		try {
			stmtAux = con.prepareStatement(
					"Select FICHA From " + usuario + ".FRUTALDESPESA Where ANO = ? And CHAVE = ? ");
			stmtAux.setDate(1, anoAtual);
			stmtAux.setString(2, chave);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				ficha = rsAux.getInt(1);
			} else {
				stmtAux = con.prepareStatement("insert into " + usuario + ".FRUTALDESPESA "
						+ "( ANO, CHAVE, FICHA ) values " 
						+ "( ?, ?, ? ) ");
				stmtAux.setDate(1, anoAtual);
				stmtAux.setString(2, chave);
				stmtAux.setInt(3, ficha);
				stmtAux.executeUpdate();
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ficha + 0;
	}

	public void deleteFichaDespesa(Connection con, String usuario, Date anoAtual) {

		try {
			stmtAux = con.prepareStatement(
					"Delete from " + usuario + ".FRUTALDESPESA Where ANO = ? ");
			stmtAux.setDate(1, anoAtual);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclus�o Ficha Despesa");
			e.printStackTrace();
		}
	}

	public String cpfLiquidante(Connection con, String usuario, int anoSonner, int liquidante) {

		String cpf = "";
		
		if(liquidante == 0) {
			return cpf;
		}

		if(anoSonner >= 2011) {
			try {
				stmtAux = con.prepareStatement(
						"Select CPF From " + usuario + ".OPER_" + anoSonner + " Where CODIGO = ? ");
				stmtAux.setInt(1, liquidante);
				rsAux = stmtAux.executeQuery();
				if (rsAux.next()) {
					cpf = rsAux.getString(1).trim();
				}
				stmtAux.close();
				rsAux.close();
			} catch (SQLException e) {
				System.err.println("Erro de Sele��o de Liquidante");
				e.printStackTrace();
			}
		}

		return cpf.replaceAll("[^0-9]+", "");
	}
	
	public String nomeFornecedor(Connection con, String usuario, int fornecedor) {
		
		String nomeCredor = "N�O ENCONTRADO";
		try {
			stmtAux = con.prepareStatement(
					"Select NOME From " + usuario + ".FORN Where CODIGO = ? " );
			stmtAux.setInt(1, fornecedor);
			rsAux = stmtAux.executeQuery();
			if(rsAux.next()) {
				nomeCredor = rsAux.getString(1).trim();
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Sele��o de Nome do Fornecedor");
			e.printStackTrace();
		}
		return nomeCredor;
	}

	public FonteRecursoFrutal fonteRecurso(Connection con, String usuario, int anoSonner, String fonte) {

		fonte = fonte.trim();
		FonteRecursoFrutal fonteRecurso = new FonteRecursoFrutal();
		fonteRecurso.setFonteRecurso(100);
		fonteRecurso.setCAFixo(999);
		
		int versaoRecurso = versaoRecurso(con, usuario, anoSonner);
		fonteRecurso.setVersaoRecurso(versaoRecurso);
		
		if(fonte.equals("") || fonte.equals("0")) {
			return fonteRecurso;
		} 

		// FONTE DE RECURSO
		if (anoSonner > 2011) {
			try {
				stmtAux = con.prepareStatement(
						"Select DEST_REC_TCE From " + usuario + ".FONTE_" + anoSonner + " Where TRIM(CODIGO) = ? " );
				stmtAux.setString(1, fonte);
				rsAux = stmtAux.executeQuery();
				if (rsAux.next()) {
					fonteRecurso.setFonteRecurso(rsAux.getInt(1));
				}
				stmtAux.close();
				rsAux.close();
			} catch (SQLException e) {
				System.err.println("Erro de Sele��o de Fonte Recurso");
				e.printStackTrace();
			}
		}

		// CODIGO DE APLICACAO
		try {
			stmtAux = con.prepareStatement(
					"Select CODAPLICACAO From " + usuario + ".CBPCODIGOAPLICACAO2 Where TRIM(CODIGO) = ? ");
			stmtAux.setString(1, fonte);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				fonteRecurso.setCAFixo(rsAux.getInt(1));
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Código de Aplicção");
			e.printStackTrace();
		}
		
		return fonteRecurso;
	}
	
	public int versaoRecurso(Connection con, String usuario, int anoSonner) {
		
		int versaoRecurso = 0;

		// VERS�O RECURSO
		Date anoAtual = java.sql.Date.valueOf( anoSonner + "-01-01");
		try {
			stmtAux = con.prepareStatement(
					"Select VERSAO From " + usuario + ".CBPRECURSOVERSAO Where ( INICIO <= ? And FIM Is Null ) Or ( INICIO <= ? And FIM >= ? )" );
			stmtAux.setDate(1, anoAtual);
			stmtAux.setDate(2, anoAtual);
			stmtAux.setDate(3, anoAtual);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				versaoRecurso = rsAux.getInt(1);
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleçãoo de Versão de Fonte Recurso");
			e.printStackTrace();
		}
		
		return versaoRecurso;
	}

	public double totalDescontos(Connection con, String usuario, int anoSonner, String codigo) {

		codigo = codigo.trim();
		
		try {
			stmtAux = con.prepareStatement(
					"Select SUM(VALOR) From " + usuario + ".DESCONTO_" + anoSonner + " Where TRIM(DOCUMENTO) = ?" );
			stmtAux.setString(1, codigo);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				return rsAux.getDouble(1);
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Total Desconto");
			e.printStackTrace();
		}
		return 0;
	}

	public void descontosTemp(Connection con, String usuario, int anoSonner, String codigo, String tipoDoc, int documento, int parcela) {

		Date anoAtual = java.sql.Date.valueOf( anoSonner + "-01-01");
		codigo = codigo.trim();
		DescontosTemp descontosTemp = new DescontosTemp();
		DescontosTempDAO descontosTempDAO = new DescontosTempDAO();

		descontosTempDAO.delete(con, usuario, anoAtual, tipoDoc, documento, parcela);
		
		int versaoRecurso = versaoRecurso(con, usuario, anoSonner);

		try {
			stmtAux = con.prepareStatement(
					"Select RECEITA, NUMERO, SUM( VALOR ) From " + usuario + ".DESCONTO_" + anoSonner + " Where TRIM(DOCUMENTO) = ? group by RECEITA, NUMERO Order by 1, 2 ");
			stmtAux.setString(1, codigo);
			rsAux = stmtAux.executeQuery();
			while (rsAux.next()) {
				descontosTemp.setAno(anoAtual);
				descontosTemp.setTipoDoc(tipoDoc);
				descontosTemp.setDocumento(documento);
				descontosTemp.setParcela(parcela);
				descontosTemp.setTipoDesc(rsAux.getString(1).trim());
				descontosTemp.setFicha(rsAux.getInt(2));
				descontosTemp.setVersaoRecurso(versaoRecurso);
				descontosTemp.setFonteRecurso(100);
				descontosTemp.setCaFixo(999);
				descontosTemp.setCaVariavel(0);
				descontosTemp.setContribuinte(1);
				descontosTemp.setValor(rsAux.getDouble(3));
				descontosTempDAO.insere(con, usuario, descontosTemp);
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclus�o de Desconto");
			e.printStackTrace();
		}
	}
}
