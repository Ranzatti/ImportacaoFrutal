import bean.*;
import dao.*;
import db.ConexaoDB;

import java.sql.*;

public class ImportaContasBancarias {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        ResultSet rs = null;
        ResultSet rs2;

        int ficha, bancoCodigo, index, versaoRecurso, fonteRecurso, codAplic;
        String agenciaCodigo, agenciaNome, conta, contaNome, fonte, nomebanco, dv;
        Date anoAtual, encerramento;
        double saldoInicial;

        String usuario = "FRUTAL";
        anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

//		Agencia agencia = new Agencia();
//		AgenciaDAO agenciaDAO = new AgenciaDAO();
        Frutal frutalDAO = new Frutal();
        ContasBancariaDAO contasBancariaDAO = new ContasBancariaDAO();
        ContasCXPlanoCDAO contasCXPlanoCDAO = new ContasCXPlanoCDAO();
        ContasFonteRecursoDAO contasFonteRecursoDAO = new ContasFonteRecursoDAO();
        ContasCADAO contasCADAO = new ContasCADAO();

//		agenciaDAO.delete(con, usuario);
        contasBancariaDAO.delete(con, usuario);
        contasCXPlanoCDAO.delete(con, usuario);
        contasFonteRecursoDAO.delete(con, usuario);
        contasCADAO.delete(con, usuario);

        try {
            System.out.println("INICIANDO IMPORTAÇÂO CONTA BANCARIA");

            stmt = con.prepareStatement(
                    "Select BANCO_CONTA, BANCO, NOME_BANCO, AGENCIA, NOME_AGENCIA, CONTA_NUMERO, FINALIDADE, FONTE, DT_ENC_CONTA "
                            + " From " + usuario + ".BANCO_" + anoSonner
                            + " Where BANCO > 0 "
                            + " Order by 1");
            rs = stmt.executeQuery();
            while (rs.next()) {

                ficha = rs.getInt(1);
                bancoCodigo = rs.getInt(2);
                nomebanco = rs.getString(3).trim();
                agenciaCodigo = rs.getString(4).trim();
                //agenciaNome = rs.getString(5).trim();
                conta = rs.getString(6).trim();
                contaNome = rs.getString(7).trim();
                fonte = rs.getString(8).trim();
                encerramento = rs.getDate(9);

                // Inserindo Agencia
//				agencia = agenciaDAO.seleciona(con, usuario, bancoCodigo, agenciaCodigo);
//				if (agencia.getBanco() == 0) {
//					agencia.setBanco(bancoCodigo);
//					agencia.setCodigo(agenciaCodigo);
//					agencia.setNome(agenciaNome);
//					agenciaDAO.insere(con, usuario, agencia);
//				}


                dv = "@";
                index = nomebanco.indexOf(conta);
                if (index >= 0) {
                    dv = nomebanco.substring(index, nomebanco.length());
                    index = dv.indexOf("-");
                    dv = dv.substring(index + 1, index + 2);
                }

                conta = conta.replaceAll("[^0-9]", "");

                // Contas Bancarias
                ContasBancarias contaBancaria = contasBancariaDAO.seleciona(con, usuario, ficha);
                if (contaBancaria.getFicha() == 0) {
                    contaBancaria = new ContasBancarias();
                    contaBancaria.setFicha(ficha);
                    contaBancaria.setBanco(bancoCodigo);
                    contaBancaria.setAgencia(agenciaCodigo);
                    contaBancaria.setCodigo(conta);
                    contaBancaria.setDv(dv);
                    contaBancaria.setNome(contaNome);
                    contaBancaria.setTipoConta("M");
                    contaBancaria.setTitular("Prefeitura Municipal de Frutal");
                    contaBancaria.setBancoAudesp(bancoCodigo);
                    contaBancaria.setAgenciaAudesp(agenciaCodigo);
                    contaBancaria.setContaAudesp(conta);
                    contaBancaria.setEncerramento(encerramento);
                    contaBancaria.setEmpresa(1);
                    contasBancariaDAO.insere(con, usuario, contaBancaria);
                }

                ContasCXPlanoC contaCXPlanoC = contasCXPlanoCDAO.seleciona(con, usuario, anoAtual, ficha);
                if (contaCXPlanoC.getAno() == null) {
                    contaCXPlanoC = new ContasCXPlanoC();
                    contaCXPlanoC.setAno(anoAtual);
                    contaCXPlanoC.setBanco(bancoCodigo);
                    contaCXPlanoC.setAgencia(agenciaCodigo);
                    contaCXPlanoC.setConta(conta);
                    contaCXPlanoC.setFichaConta(ficha);
                    contaCXPlanoC.setFichaPC(13);
                    contaCXPlanoC.setFicnaPCAplic(18);
                    contasCXPlanoCDAO.insere(con, usuario, contaCXPlanoC);
                }

                // Buscando Saldo Banc�rio
                saldoInicial = 0;
                stmt2 = con.prepareStatement(
                        "Select ABERTURA "
                                + " From " + usuario + ".CONTAFIN_" + anoSonner
                                + " And COD_REDUZIDO = ? "
                                + " And SISTEMA = 'B' ");
                stmt2.setInt(1, ficha);
                rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    saldoInicial = Math.abs(rs2.getDouble(1));
                }
                stmt2.close();
                rs2.close();

                FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                versaoRecurso = frutal.getVersaoRecurso();
                fonteRecurso = frutal.getFonteRecurso();
                codAplic = frutal.getCAFixo();

                ContasFonteRecurso contaFonteRecurso = new ContasFonteRecurso();
                contaFonteRecurso.setFicha(ficha);
                contaFonteRecurso.setVersaoRecurso(versaoRecurso);
                contaFonteRecurso.setFonteRecurso(fonteRecurso);
                contaFonteRecurso.setSaldoInicial(saldoInicial);
                contasFonteRecursoDAO.insere(con, usuario, contaFonteRecurso);

                ContasCA contasCA = contasCADAO.seleciona(con, usuario, ficha, fonteRecurso, codAplic);
                if (contasCA.getFicha() == 0) {
                    contasCA = new ContasCA();
                    contasCA.setFicha(ficha);
                    contasCA.setVersaoRecurso(1);
                    contasCA.setFonteRecurso(fonteRecurso);
                    contasCA.setCaFixo(codAplic);
                    contasCA.setCaVariavel(0);
                    contasCA.setSaldoInicial(0);
                    contasCADAO.insere(con, usuario, contasCA);
                }
            }

            System.out.println("FINALIZANDO IMPORTAÇÂO CONTA BANCARIA");
        } catch (SQLException e) {
            System.out.println("OPS");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }

    }

}
