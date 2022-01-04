package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.ContasBancarias;

public class ContasBancariaDAO {

	PreparedStatement stmtAux = null;
	ResultSet rsAux = null;

	public ContasBancariaDAO() {
	}

	public void insere(Connection con, String usuario, ContasBancarias obj) {

		try {
			
			stmtAux = con.prepareStatement("insert into " + usuario + ".CBPCONTAS "
					+ "( FICHA, BANCO, AGENCIA, CODIGO, DV, NOME, TIPOCONTA, TITULAR, BANCOAUDESP, AGENCIAAUDESP, CONTAAUDESP, ENCERRAMENTO, EMPRESA ) values"
					+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );
			stmtAux.setInt(1, obj.getFicha());
			stmtAux.setInt(2, obj.getBanco());
			stmtAux.setString(3, obj.getAgencia());
			stmtAux.setString(4, obj.getCodigo());
			stmtAux.setString(5, obj.getDv());
			stmtAux.setString(6, obj.getNome());
			stmtAux.setString(7, obj.getTipoConta());
			stmtAux.setString(8, obj.getTitular());
			stmtAux.setInt(9, obj.getBancoAudesp());
			stmtAux.setString(10, obj.getAgenciaAudesp());
			stmtAux.setString(11, obj.getContaAudesp());
			stmtAux.setDate(12, obj.getEncerramento());
			stmtAux.setInt(13, obj.getEmpresa());
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Inclusão Contas Bancarias");
			e.printStackTrace();
		}
	}

	public void delete(Connection con, String usuario) {

		try {
			stmtAux = con.prepareStatement("Delete from " + usuario + ".CBPCONTAS ");
			stmtAux.executeUpdate();
			stmtAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Exclusão Agencia");
			e.printStackTrace();
		}
	}

	public ContasBancarias seleciona(Connection con, String usuario, int ficha) {

		ContasBancarias contaBancaria = new ContasBancarias();
		contaBancaria.setFicha(0);
		contaBancaria.setBanco(-1);
		contaBancaria.setAgencia(null);
		contaBancaria.setCodigo(null);
		
		if(ficha == 0) {
			return contaBancaria;
		}

		try {
			stmtAux = con.prepareStatement(
					"Select BANCO, AGENCIA, CODIGO From " + usuario + ".CBPCONTAS Where FICHA = ? ");
			stmtAux.setInt(1, ficha);
			rsAux = stmtAux.executeQuery();
			if (rsAux.next()) {
				contaBancaria.setFicha(ficha);
				contaBancaria.setBanco(rsAux.getInt(1));
				contaBancaria.setAgencia(rsAux.getString(2).trim());
				contaBancaria.setCodigo(rsAux.getString(3).trim());
			} 
			stmtAux.close();
			rsAux.close();
		} catch (SQLException e) {
			System.err.println("Erro de Seleção de Conta Bancaria");
			e.printStackTrace();
		}
		return contaBancaria;
	}
}
