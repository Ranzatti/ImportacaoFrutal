package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.DescontosTemp;
import bean.FonteRecursoFrutal;

public class FrutalBKP {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public String historico(Connection con, String usuario, int anoSonner, String codigo) {

		codigo = codigo.trim();

		String historico = "";

		if (codigo == null) {
			historico = "";
		} else {
			try {
				stmtAux = con.prepareStatement(
						"Select HISTORICO "
						+ "From " + usuario	+ ".HISTORIC_SONNER "
						+ "Where ANOSONNER = ? "
						+ "And TRIM(CODIGO) = ? "
						+ "Order by LINHA ");
				stmtAux.setInt(1, anoSonner);
				stmtAux.setString(2, codigo);
				rsAux = stmtAux.executeQuery();
				while (rsAux.next()) {
					historico += " " + rsAux.getString(1);
				}
				stmtAux.close();
				rsAux.close();
			} catch (SQLException e) {
				System.err.println("Erro de Seleção de Historico");
				e.printStackTrace();
			}
		}
		return historico;
	}
	
	public int fichaDespesa(Connection con, String usuario, Date anoAtual, String chave, int ficha) {
		
		try {
			stmtAux = con.prepareStatement(
					"Select FICHA "
					+ "From " + usuario	+ ".FRUTALDESPESA "
					+ "Where ANO = ? "
					+ "And CHAVE = ? ");
			stmtAux.setDate(1, anoAtual );
			stmtAux.setString(2, chave );
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				ficha = rsAux.getInt(1);
			} else {
				stmtAux = con.prepareStatement(
						"insert into " + usuario + ".FRUTALDESPESA "
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
			stmtAux = con.prepareStatement("Delete from " + usuario + ".FRUTALDESPESA Where ANO = ?");
			stmtAux.setDate(1, anoAtual);
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Ficha Despesa");
			e.printStackTrace();
		}
	}
	
	public String liquidante(Connection con, String usuario, int anoSonner, int liquidante) {

		String cpf = "";
		
		try {
			stmtAux = con.prepareStatement(
					"Select CPF From " + usuario + ".OPER_SONNER "
					+ "Where ANOSONNER = ? "
					+ "And CODIGO = ? ");
			stmtAux.setInt(1, anoSonner);
			stmtAux.setInt(2, liquidante);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				cpf = rsAux.getString(1);
			}
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Liquidante");
			e.printStackTrace();
		}

		return cpf.replaceAll("[^0-9]+", "");
	}

	public FonteRecursoFrutal fonteRecurso(Connection con, String usuario, int anoSonner, String fonte) {

		fonte = fonte.trim();

		FonteRecursoFrutal fonteRecurso = new FonteRecursoFrutal();

		if (fonte.equals("")) {
			fonteRecurso.setFonteRecurso(100);
			fonteRecurso.setCAFixo(999);
		} else {
			if(anoSonner <= 2011) {
				try {
					fonteRecurso.setFonteRecurso(100);
					fonteRecurso.setCAFixo(999);
//					stmtAux = con.prepareStatement(
//							"Select RECORD_NUMBER "
//									+ " From " + usuario	+ ".FONTE_" + anoSonner
//									+ " Where TRIM(CODIGO) = ? "
//									+ "");
					stmtAux = con.prepareStatement(
							"Select CODAPLICACAO "
									+ " From " + usuario + ".CBPCODIGOAPLICACAO2 " 
									+ " Where TRIM(CODIGO) = ? "
									+ "");
					stmtAux.setString(1, fonte);
					rsAux = stmtAux.executeQuery();
					if (rsAux.next()) {
						fonteRecurso.setCAFixo(rsAux.getInt(1));
					}
					stmtAux.close();
					rsAux.close();
				} catch (SQLException e) {
					System.err.println("Erro de Seleção de Fonte Recurso");
					e.printStackTrace();
				}
			} else {
				try {
					fonteRecurso.setFonteRecurso(100);
					fonteRecurso.setCAFixo(999);
					stmtAux = con.prepareStatement(
							"Select DEST_REC_TCE, RECORD_NUMBER "
									+ " From " + usuario	+ ".FONTE_" + anoSonner
									+ " Where TRIM(CODIGO) = ? "
									+ " ");
					stmtAux.setString(1, fonte);
					rsAux = stmtAux.executeQuery();
					if (rsAux.next()) {
						fonteRecurso.setFonteRecurso(rsAux.getInt(1));
						fonteRecurso.setCAFixo(rsAux.getInt(2));
					}
					stmtAux.close();
					rsAux.close();
				} catch (SQLException e) {
					System.err.println("Erro de Seleção de Fonte Recurso");
					e.printStackTrace();
				}
			}
		}
		

		return fonteRecurso;
	}

	public double totalDescontos(Connection con, String usuario, int anoSonner, String codigo) {

		try {
			stmtAux = con.prepareStatement(
					"Select SUM(VALOR) "
					+ "From " + usuario + ".DESCONTO_SONNER "
					+ "Where ANOSONNER = ? "
					+ "And TRIM(DOCUMENTO) = ?");
			stmtAux.setInt(1, anoSonner);
			stmtAux.setString(2, codigo);
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

	public void descontosTemp(Connection con, String codigo, String usuario, int anoSonner, Date ano, String tipoDoc,
			int documento, int parcela) {

		DescontosTemp descontosTemp = new DescontosTemp();
		DescontosTempDAO descontosTempDAO = new DescontosTempDAO();
		
		descontosTempDAO.delete(con, usuario, ano, tipoDoc, documento, parcela);

		try {

			stmtAux = con.prepareStatement(
					"Select RECEITA, NUMERO, SUM( VALOR ) "
					+ "From " + usuario + ".DESCONTO_SONNER "
					+ "Where ANOSONNER = ? "
					+ "And TRIM(DOCUMENTO) = ? " 
					+ "group by RECEITA, NUMERO "
					+ "Order by 1, 2 ");
			stmtAux.setInt(1, anoSonner);
			stmtAux.setString(2, codigo);
			rsAux = stmtAux.executeQuery();
			while (rsAux.next()) {
				descontosTemp.setAno(ano);
				descontosTemp.setTipoDoc(tipoDoc);
				descontosTemp.setDocumento(documento);
				descontosTemp.setParcela(parcela);
				descontosTemp.setTipoDesc(rsAux.getString(1));
				descontosTemp.setFicha(rsAux.getInt(2));
				descontosTemp.setVersaoRecurso(1);
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
			System.err.println("Erro de Inclusão de Desconto");
			e.printStackTrace();
		}
	}
}
