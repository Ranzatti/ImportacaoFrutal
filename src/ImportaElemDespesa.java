import bean.*;
import dao.*;
import db.ConexaoDB;

import java.sql.*;

public class ImportaElemDespesa {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        ResultSet rs = null;

        int fonteRecurso, ficha, codAplic, versaoRecurso;
        double orcado, valor, subTotal;
        String usuario, fonte, chave, orgao, unidade, subUnidade, funcao, subFuncao, programa, projAtiv, categoria, grupo, modalidade, elemento;
        Date anoAtual;

        usuario = "FRUTAL";
        anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        Frutal frutalDAO = new Frutal();
        ElemDespesaDAO elemDespDAO = new ElemDespesaDAO();
        ElemDespRecursoDAO elemDespRecDAO = new ElemDespRecursoDAO();
        ElemDespMensalDAO elemDespMensalDAO = new ElemDespMensalDAO();
        ElemDespCADAO elemDespCADAO = new ElemDespCADAO();

        elemDespDAO.delete(con, usuario, anoAtual);
        elemDespRecDAO.delete(con, usuario, anoAtual);
        elemDespMensalDAO.delete(con, usuario, anoAtual);
        elemDespCADAO.delete(con, usuario, anoAtual);
        frutalDAO.deleteFichaDespesa(con, usuario, anoAtual);

        String teste = "";
        //teste = " and FICHA = 153 ";

        try {
            System.out.println("INICIANDO IMPORTAÇÂO DESPESA: " + anoSonner);
            stmt = con.prepareStatement(
                    "Select FICHA, ORGAO, UNIDADE, SUB_UNIDADE, FUNCAO, SUB_FUNCAO, PROGRAMA, PROJ_ATIV, D_CATEG_ECONOM, D_GRUPO_NAT, D_MODALI_APLIC, D_ELEM_DESPESA, VALOR, FONTE "
                            + " From " + usuario + ".ORCTO_" + anoSonner
                            + " Where TRIM(COD_UNIDADE) is not null " + teste
                            + " Order by FICHA ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                ficha = rs.getInt(1);
                orgao = rs.getString(2).trim();
                unidade = rs.getString(3).trim();
                subUnidade = rs.getString(4).trim();
                funcao = rs.getString(5).trim();
                subFuncao = rs.getString(6).trim();
                programa = rs.getString(7).trim();
                projAtiv = rs.getString(8).trim().replaceAll("\\.", "");
                categoria = rs.getString(9).trim();
                grupo = rs.getString(10).trim();
                modalidade = rs.getString(11).trim();
                elemento = rs.getString(12).trim();
                orcado = rs.getDouble(13);
                fonte = rs.getString(14).trim();

                chave = orgao + unidade + subUnidade + funcao + subFuncao + programa + projAtiv + categoria + grupo + modalidade + elemento;

                ficha = frutalDAO.fichaDespesa(con, usuario, anoAtual, chave, ficha);

                ElemDespesa elemDesp = elemDespDAO.seleciona(con, usuario, anoAtual, ficha);
                if (elemDesp.getAno() == null) {
                    elemDesp.setAno(anoAtual);
                    elemDesp.setEmpresa(1);
                    elemDesp.setFicha(ficha);
                    elemDesp.setOrgao(orgao);
                    elemDesp.setUnidade(unidade);
                    elemDesp.setSubUnidade(subUnidade);
                    elemDesp.setFuncao(funcao);
                    elemDesp.setSubFuncao(subFuncao);
                    elemDesp.setPrograma(programa);
                    elemDesp.setProjAtiv(projAtiv);
                    elemDesp.setCategoria(categoria);
                    elemDesp.setGrupo(grupo);
                    elemDesp.setModalidade(modalidade);
                    elemDesp.setElemento(elemento);
                    elemDesp.setDesdobramento("00");
                    elemDesp.setOrcado(orcado);
                    elemDespDAO.insere(con, usuario, elemDesp);
                } else {
                    stmt2 = con.prepareStatement(
                            "Update " + usuario + ".CBPELEMDESPESA "
                                    + "Set	ORCADO = ORCADO + ? "
                                    + "Where ANO = ? "
                                    + "And FICHA = ? ");
                    stmt2.setDouble(1, orcado);
                    stmt2.setDate(2, anoAtual);
                    stmt2.setInt(3, ficha);
                    stmt2.executeUpdate();
                    stmt2.close();
                }

                // Fonte Recurso
                FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                versaoRecurso = frutal.getVersaoRecurso();
                fonteRecurso = frutal.getFonteRecurso();
                codAplic = frutal.getCAFixo();

                ElemDespRecurso elemDespRec = elemDespRecDAO.seleciona(con, usuario, anoAtual, ficha, fonteRecurso);
                if (elemDespRec.getAno() == null) {
                    elemDespRec.setAno(anoAtual);
                    elemDespRec.setFicha(ficha);
                    elemDespRec.setVersaoRecurso(versaoRecurso);
                    elemDespRec.setRecurso(fonteRecurso);
                    elemDespRec.setOrcado(orcado);
                    elemDespRecDAO.insere(con, usuario, elemDespRec);
                } else {
                    stmt2 = con.prepareStatement(
                            "Update " + usuario + ".CBPELEMDESPRECURSO "
                                    + "Set	ORCADO = ORCADO + ? "
                                    + "Where ANO = ? "
                                    + "And FICHA = ? "
                                    + "And RECURSO = ? ");
                    stmt2.setDouble(1, orcado);
                    stmt2.setDate(2, anoAtual);
                    stmt2.setInt(3, ficha);
                    stmt2.setInt(4, fonteRecurso);
                    stmt2.executeUpdate();
                    stmt2.close();
                }

                ElemDespCA elemDespCA = elemDespCADAO.seleciona(con, usuario, anoAtual, ficha, fonteRecurso, codAplic);
                if (elemDespCA.getAno() == null) {
                    elemDespCA = new ElemDespCA();
                    elemDespCA.setAno(anoAtual);
                    elemDespCA.setFicha(ficha);
                    elemDespCA.setVersaoRecurso(versaoRecurso);
                    elemDespCA.setRecurso(fonteRecurso);
                    elemDespCA.setCaFixo(codAplic);
                    elemDespCA.setCaVariavel(0);
                    elemDespCA.setOrcado(0);
                    elemDespCADAO.insere(con, usuario, elemDespCA);
                }

                //Distribuição Mensal
                subTotal = 0.00;
                valor = truncate(orcado / 12);
                for (int mes = 1; mes <= 12; mes++) {
                    if (mes == 12) {
                        valor = orcado - subTotal;
                    } else {
                        subTotal += valor;
                    }

                    ElemDespMensal elemDespMensal = elemDespMensalDAO.seleciona(con, usuario, anoAtual, ficha, mes, fonteRecurso, codAplic);
                    if (elemDespMensal.getAno() == null) {
                        elemDespMensal.setAno(anoAtual);
                        elemDespMensal.setFicha(ficha);
                        elemDespMensal.setMes(mes);
                        elemDespMensal.setVersaoRecurso(versaoRecurso);
                        elemDespMensal.setRecurso(fonteRecurso);
                        elemDespMensal.setCaFixo(codAplic);
                        elemDespMensal.setCaVariavel(0);
                        elemDespMensal.setOrcado(valor);
                        elemDespMensalDAO.insere(con, usuario, elemDespMensal);
                    } else {
                        stmt2 = con.prepareStatement(
                                "Update " + usuario + ".CBPELEMDESPMENSAL "
                                        + "Set	ORCADO = ORCADO + ? "
                                        + "Where ANO = ? "
                                        + "And FICHA = ? "
                                        + "And MES = ? "
                                        + "And RECURSO = ? "
                                        + "And CAFIXO = ? ");
                        stmt2.setDouble(1, orcado);
                        stmt2.setDate(2, anoAtual);
                        stmt2.setInt(3, ficha);
                        stmt2.setInt(4, mes);
                        stmt2.setInt(5, fonteRecurso);
                        stmt2.setInt(6, codAplic);
                        stmt2.executeUpdate();
                        stmt2.close();
                    }
                }
            }
            System.out.println("FINALIZANDO IMPORTAÇÂO DESPESA: " + anoSonner);
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
