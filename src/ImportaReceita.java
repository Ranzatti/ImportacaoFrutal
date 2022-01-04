import bean.*;
import dao.*;
import db.ConexaoDB;

import java.sql.*;

public class ImportaReceita {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        ResultSet rs = null;
        ResultSet rs2;

        int versaoRecurso, fonteRecurso, ficha, caFixo;
        Double orcado, valor, subTotal;
        String usuario, receitaCodigo, receitaCodigoOld, retificadora, fonte, nomeReceita;
        Date anoAtual;

        usuario = "FRUTAL";
        anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        Frutal frutalDAO = new Frutal();
        ReceitaDAO receitaDAO = new ReceitaDAO();
        ReceitaFonteRecursoDAO receitaFonteRecDAO = new ReceitaFonteRecursoDAO();
        ReceitaMensalDAO receitaMensalDAO = new ReceitaMensalDAO();
        TabReceitaDAO tabReceitaDAO = new TabReceitaDAO();
        ReceitasCADAO receitasCADAO = new ReceitasCADAO();

        tabReceitaDAO.delete(con, usuario, anoAtual, "A");
        receitaDAO.delete(con, usuario, anoAtual);
        receitaFonteRecDAO.delete(con, usuario, anoAtual);
        receitaMensalDAO.delete(con, usuario, anoAtual);
        receitasCADAO.delete(con, usuario, anoAtual);

        try {
            System.out.println("INICIANDO IMPORTAÇÂO RECEITA: " + anoSonner);
            stmt = con.prepareStatement(
                    "Select FICHA, COD_CONTA, VALOR, FONTE, TEM_CONTA_RETIF "
                            + " From " + usuario + ".ORCTO_" + anoSonner
                            + " Where TRIM(COD_UNIDADE) is null "
                            + " Order by 1 ");
            rs = stmt.executeQuery();
            while (rs.next()) {

                ficha = rs.getInt(1);
                receitaCodigo = rs.getString(2).trim();
                receitaCodigoOld = rs.getString(2).trim();
                orcado = rs.getDouble(3);
                fonte = rs.getString(4).trim();
                retificadora = rs.getString(5).trim();

                anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

                if (!retificadora.equals("")) {
                    if (retificadora.equals("1")) {
                        receitaCodigo = "95" + receitaCodigo.substring(0, receitaCodigo.length() - 2);
                    } else if (retificadora.equals("3")) {
                        receitaCodigo = "92" + receitaCodigo.substring(0, receitaCodigo.length() - 2);
                    } else if (retificadora.equals("6")) {
                        receitaCodigo = "98" + receitaCodigo.substring(0, receitaCodigo.length() - 2);
                    } else if (retificadora.equals("9")) {
                        receitaCodigo = "99" + receitaCodigo.substring(0, receitaCodigo.length() - 2);
                    }
                }

                // Tab Receitas
                TabReceitas tabReceita = tabReceitaDAO.seleciona(con, usuario, anoAtual, receitaCodigo);
                if (tabReceita.getAno() == null) {

                    nomeReceita = "NÂO ENCONTRADO";
                    stmt2 = con.prepareStatement(
                            "Select DESCRICAO "
                                    + " From " + usuario + ".CONTA_" + anoSonner
                                    + " Where TRIM(CONTA_STN) = ? ");
                    stmt2.setString(1, receitaCodigoOld);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        nomeReceita = rs2.getString(1).trim().toUpperCase();
                    }
                    stmt2.close();
                    rs2.close();

                    tabReceita = new TabReceitas();
                    tabReceita.setAno(anoAtual);
                    tabReceita.setReceita(receitaCodigo);
                    tabReceita.setTipoConta("A");
                    tabReceita.setNome(nomeReceita);
                    tabReceita.setOrigemNaLei("S");
                    tabReceita.setNatureza("I");
                    tabReceitaDAO.insere(con, usuario, tabReceita);
                }

                Receitas receita = new Receitas();
                receita.setAno(anoAtual);
                receita.setEmpresa(1);
                receita.setFicha(ficha);
                receita.setReceita(receitaCodigo);
                receita.setOrcado(orcado);
                receitaDAO.insere(con, usuario, receita);

                // Fonte Recurso
                FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                versaoRecurso = frutal.getVersaoRecurso();
                fonteRecurso = frutal.getFonteRecurso();
                caFixo = frutal.getCAFixo();

                ReceitaFonteRecurso receitaFonteRec = new ReceitaFonteRecurso();
                receitaFonteRec.setAno(anoAtual);
                receitaFonteRec.setFichaReceita(ficha);
                receitaFonteRec.setVersaoRecurso(versaoRecurso);
                receitaFonteRec.setFonteRecurso(fonteRecurso);
                receitaFonteRec.setOrcado(orcado);
                receitaFonteRecDAO.insere(con, usuario, receitaFonteRec);

                ReceitasCA receitasCA = receitasCADAO.seleciona(con, usuario, anoAtual, ficha, fonteRecurso, caFixo);
                if (receitasCA.getAno() == null) {
                    receitasCA = new ReceitasCA();
                    receitasCA.setAno(anoAtual);
                    receitasCA.setFicha(ficha);
                    receitasCA.setVersaoRecurso(versaoRecurso);
                    receitasCA.setRecurso(fonteRecurso);
                    receitasCA.setCaFixo(caFixo);
                    receitasCA.setCaVariavel(0);
                    receitasCA.setOrcado(0);
                    receitasCADAO.insere(con, usuario, receitasCA);
                }

                subTotal = 0.00;
                valor = truncate(orcado / 12);
                for (int mes = 1; mes <= 12; mes++) {
                    if (mes == 12) {
                        valor = orcado - subTotal;
                    } else {
                        subTotal += valor;
                    }

                    ReceitaMensal receitaMensal = new ReceitaMensal();
                    receitaMensal.setAno(anoAtual);
                    receitaMensal.setFichaReceita(ficha);
                    receitaMensal.setVersaoRecurso(versaoRecurso);
                    receitaMensal.setFonteRecurso(fonteRecurso);
                    receitaMensal.setCaFixo(caFixo);
                    receitaMensal.setCaVariavel(0);
                    receitaMensal.setMes(mes);
                    receitaMensal.setOrcado(valor);
                    receitaMensalDAO.insere(con, usuario, receitaMensal);
                }

            }

            System.out.println("FINALIZANDO IMPORTAÇÂO RECEITA: " + anoSonner);
        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }

    }

    public static double truncate(double value) {
        return Math.round(value * 100) / 100d;
    }

}
