import bean.FonteRecursoFrutal;
import bean.RestosFonteRec;
import bean.RestosInscritos;
import bean.RestosProcParc;
import dao.Frutal;
import dao.RestosFonteRecDAO;
import dao.RestosInscritosDAO;
import dao.RestosProcParcDAO;
import db.ConexaoDB;

import java.sql.*;
import java.text.SimpleDateFormat;

public class ImportaRestosInscritos {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        ResultSet rs = null;
        ResultSet rs2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        Date anoAtual, anoEmpenho, dataEmpenho, dataLiquidacao, vencimento, dataFinal;
        String codigo, tipo, numero, fonte, desdobramento, nomeCredor;
        int empenho, parcela, fornecedor, ficha, anoResto, versaoRecurso, fonteRecurso, caFixo;
        double valor, valorProc, valorNProc, valorEmpenho;

        String usuario = "FRUTAL";
        anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        Frutal frutalDAO = new Frutal();
        RestosInscritosDAO restosDAO = new RestosInscritosDAO();
        RestosFonteRecDAO restosFonteRecDAO = new RestosFonteRecDAO();
        RestosProcParcDAO restosProcPactDAO = new RestosProcParcDAO();

        restosDAO.delete(con, usuario, anoAtual);
        restosFonteRecDAO.delete(con, usuario, anoAtual);
        restosProcPactDAO.delete(con, usuario, anoAtual);

        String teste = "";
        //teste = " and NUMERO like '00586%' ";

        try {
            System.out.println("INICIANDO IMPORTAÇÂO RESTOS INSCRITOS: " + anoSonner);
            stmt = con.prepareStatement(
                    "Select CODIGO, TIPO, NUMERO, DATA, FAVORECIDO, VALOR, VENCIMENTO, LIQUIDACAO "
                            + " From " + usuario + ".EXTRAORC_" + anoSonner
                            + " Where TRIM(TIPO) != 'OP' " + teste
                            + " Order by 1, 2 ");
            rs = stmt.executeQuery();
            while (rs.next()) {

                codigo = rs.getString(1).trim();
                tipo = rs.getString(2).trim();
                numero = rs.getString(3).trim();
                dataEmpenho = rs.getDate(4);
                fornecedor = rs.getInt(5);
                valor = rs.getDouble(6);
                vencimento = rs.getDate(7);
                dataLiquidacao = rs.getDate(8);

                anoResto = Integer.parseInt(tipo) > 50 ? 1900 + Integer.parseInt(tipo) : 2000 + Integer.parseInt(tipo);
                anoEmpenho = java.sql.Date.valueOf(anoResto + "-01-01");
                dataFinal = java.sql.Date.valueOf(anoResto + "-12-31");
                empenho = Integer.parseInt(numero.substring(0, 5));
                parcela = (numero.length() > 5 ? Integer.parseInt(numero.substring(5)) : 1);

                //System.out.println("Ano/Empenho: " + anoResto + "/" + empenho);

                // Restos Inscritos
                RestosInscritos restos = restosDAO.seleciona(con, usuario, anoEmpenho, empenho);
                if (restos.getAno() == null) {

                    valorProc = valor;
                    valorNProc = 0;
                    if (dataLiquidacao != null && sdf.format(anoEmpenho).equals(sdf.format(dataLiquidacao))) {
                        valorProc = 0;
                        valorNProc = valor;
                    }

                    // Pegando Dados do Empenho
                    ficha = 0;
                    fonte = "";
                    valorEmpenho = valorProc + valorNProc;
                    if (anoResto >= 2005) {
                        stmt2 = con.prepareStatement(
                                "Select FICHA, FONTE, VALOR "
                                        + " From " + usuario + ".MOV_ORC_" + anoResto
                                        + " Where TRIM(NUMERO) = ? ");
                        stmt2.setString(1, numero);
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            ficha = rs2.getInt(1);
                            fonte = rs2.getString(2).trim();
                            valorEmpenho = rs2.getDouble(3);
                        }
                        stmt2.close();
                        rs2.close();
                    }

                    // Desdobramento
                    desdobramento = "99";
                    if (anoSonner >= 2008) {
                        stmt2 = con.prepareStatement(
                                "Select CONTA "
                                        + " From " + usuario + ".MOV_ORC_" + anoSonner
                                        + " Where TRIM(NUMERO) = ? ");
                        stmt2.setString(1, numero);
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            desdobramento = rs2.getString(1).trim();
                        }
                        stmt2.close();
                        rs2.close();

                        if (!desdobramento.equals("")) {
                            if (desdobramento.length() == 8) {
                                desdobramento = desdobramento.substring(6, 8);
                            } else {
                                desdobramento = "99";
                            }
                        }
                    }

                    nomeCredor = frutalDAO.nomeFornecedor(con, usuario, fornecedor);

                    restos = new RestosInscritos();
                    restos.setAno(anoEmpenho);
                    restos.setEmpenho(empenho);
                    restos.setFicha(ficha);
                    restos.setOrigem("A");
                    restos.setCodCredor(fornecedor);
                    restos.setCredor(nomeCredor);
                    restos.setDataEmpenho(dataEmpenho);
                    restos.setValorEmpenho(valorEmpenho);
                    restos.setValor(valorProc);
                    restos.setValorNaoProc(valorNProc);
                    restos.setSubElemento(desdobramento);
                    restosDAO.insere(con, usuario, restos);

                    // Fonte Recurso
                    FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                    versaoRecurso = frutal.getVersaoRecurso();
                    fonteRecurso = frutal.getFonteRecurso();
                    caFixo = frutal.getCAFixo();

                    if (anoSonner >= 2015) {
                        stmt2 = con.prepareStatement(
                                "Select DEST_TCE "
                                        + " From " + usuario + ".EXTRAORC_" + anoSonner
                                        + " Where TRIM(CODIGO) = ? ");
                        stmt2.setString(1, codigo);
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            fonteRecurso = Integer.parseInt(rs2.getString(1));
                        }
                        stmt2.close();
                        rs2.close();
                    }

                    RestosFonteRec restosFonteRec = restosFonteRecDAO.seleciona(con, usuario, anoEmpenho, empenho, fonteRecurso);
                    if (restosFonteRec.getAno() == null) {
                        restosFonteRec = new RestosFonteRec();
                        restosFonteRec.setAno(anoEmpenho);
                        restosFonteRec.setEmpenho(empenho);
                        restosFonteRec.setVersaoRecurso(versaoRecurso);
                        restosFonteRec.setRecurso(fonteRecurso);
                        restosFonteRec.setCaFixo(caFixo);
                        restosFonteRec.setCaVariavel(0);
                        restosFonteRec.setValor(valor);
                        restosFonteRecDAO.insere(con, usuario, restosFonteRec);
                    }

                    if (valorProc > 0) {
                        RestosProcParc restosProcParc = restosProcPactDAO.seleciona(con, usuario, anoEmpenho, empenho, parcela);
                        if (restosProcParc.getAnoEmpenho() == null) {
                            vencimento = (vencimento == null ? dataFinal : vencimento);
                            restosProcParc = new RestosProcParc();
                            restosProcParc.setAnoEmpenho(anoEmpenho);
                            restosProcParc.setEmpenho(empenho);
                            restosProcParc.setParcela(parcela);
                            restosProcParc.setVencimento(vencimento);
                            restosProcParc.setValor(valorProc);
                            restosProcPactDAO.insere(con, usuario, restosProcParc);
                        }
                    }
                }
            }
            System.out.println("FINALIZANDO IMPORTAÇÂO RESTOSINSCRITOS: " + anoSonner);
        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }

    }

}
