import bean.*;
import dao.*;
import db.ConexaoDB;

import java.sql.*;

public class ImportaGuiaReceita {

    public static void main(String[] args) {
        init(2005);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        PreparedStatement stmt3;
        ResultSet rs = null;
        ResultSet rs2;
        ResultSet rs3;

        int numeroGuia, seq, fichaReceita, fichaBanco, versaoRecurso, fonteRecurso, caFixo;
        Date dataGuia;
        String fonte, fonte2, receita, tipoReceita;
        double valorGuia;

        String usuario = "FRUTAL";
        Date anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        Frutal frutalDAO = new Frutal();
        ContasBancariaDAO contaBancoDAO = new ContasBancariaDAO();
        GuiaReceitaDAO guiaRecDAO = new GuiaReceitaDAO();
        ItensGuiaReceitaDAO itemGuiaDAO = new ItensGuiaReceitaDAO();
        RecContaBancoDAO recContaBancoDAO = new RecContaBancoDAO();
        CreditoDAO creditoDAO = new CreditoDAO();
        EntradasCaixaDAO entradasCaixaDAO = new EntradasCaixaDAO();

        itemGuiaDAO.delete(con, usuario, anoAtual, "DIG");
        guiaRecDAO.delete(con, usuario, anoAtual, "DIG");
        recContaBancoDAO.delete(con, usuario, anoAtual);
        creditoDAO.deleteGuiaReceita(con, usuario, anoAtual);
        entradasCaixaDAO.deleteGuiaReceita(con, usuario, anoAtual);

        String teste = "";
        // teste = " and VOUCHER = 2078 ";

        System.out.println("INICIANDO IMPORTAÇÃO GUIA RECEITA: " + anoSonner);
        try {
            System.out.println("INICIANDO GUIAS ORÇAMENTÁRIAS");

            tipoReceita = "O";
            stmt = con.prepareStatement(
                    "Select DISTINCT VOUCHER, DATA"
                            + " From " + usuario + ".MOV_FIN_" + anoSonner + " A "
                            + " Where TIPO_VOUCHER = 1 "
                            + " And TRIM(DOC_ORC_TIPO) = 'RO' "
                            + " And exists ( Select * From " + usuario + ".ORCTO_" + anoSonner + " X Where A.FICHA = X.FICHA And TRIM(X.TEM_CONTA_RETIF) is null ) " + teste
                            + " GROUP BY VOUCHER, DATA"
                            + " Order by VOUCHER ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                numeroGuia = rs.getInt(1);
                dataGuia = rs.getDate(2);

                //System.out.println("Guia: " + numeroGuia);

                // Guia de Receitas
                GuiaReceita guiaRec = guiaRecDAO.seleciona(con, usuario, anoAtual, tipoReceita, numeroGuia);
                if (guiaRec.getAno() == null) {
                    guiaReceita(con, usuario, anoAtual, numeroGuia, tipoReceita, dataGuia, "Guia de Receita - ", guiaRecDAO);
                }

                if (anoSonner >= 2017) {
                    stmt2 = con.prepareStatement(
                            "Select DISTINCT SEQ, FICHA, FONTE, COD_CONTA, BANCO_CONTA, DEST_TCE, SUM(VALOR) "
                                    + " From " + usuario + ".MOV_FIN_" + anoSonner + " A "
                                    + " Where TIPO_VOUCHER = 1 "
                                    + " And VOUCHER = ? "
                                    + " And TRIM(DOC_ORC_TIPO) = 'RO' "
                                    + " And exists ( Select * From " + usuario + ".ORCTO_" + anoSonner + " X Where A.FICHA = X.FICHA And TRIM(X.TEM_CONTA_RETIF) is null ) "
                                    + " GROUP BY SEQ, FICHA, FONTE, COD_CONTA, BANCO_CONTA, DEST_TCE "
                                    + " Order by SEQ, FICHA ");
                    stmt2.setInt(1, numeroGuia);
                    rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        seq = rs2.getInt(1);
                        fichaReceita = rs2.getInt(2);
                        fonte = rs2.getString(3).trim();
                        receita = rs2.getString(4).trim();
                        fichaBanco = rs2.getInt(5);
                        fonte2 = rs2.getString(6).trim();
                        valorGuia = rs2.getDouble(7);

                        // Fonte Recurso
                        FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                        versaoRecurso = frutal.getVersaoRecurso();
                        fonteRecurso = frutal.getFonteRecurso();
                        caFixo = frutal.getCAFixo();

                        if (!fonte2.equals("") && !fonte2.equals("*")) {
                            fonteRecurso = Integer.parseInt(fonte2);
                        }

                        if (fonte2.equals("*")) {
                            stmt3 = con.prepareStatement(
                                    "Select DEST_TCE, SUM(VALOR) "
                                            + " From " + usuario + ".RDESPDET_" + anoSonner
                                            + " Where VOUCHER = ? "
                                            + " And SEQ = ? "
                                            + " And FICHA = ? "
                                            + " Group by DEST_TCE "
                                            + " Order by 1 ");
                            stmt3.setInt(1, numeroGuia);
                            stmt3.setInt(2, seq);
                            stmt3.setInt(3, fichaReceita);
                            rs3 = stmt3.executeQuery();
                            while (rs3.next()) {
                                fonteRecurso = Integer.parseInt(rs3.getString(1).trim());
                                valorGuia = rs3.getDouble(2);

                                contaBanco(con, usuario, anoAtual, tipoReceita, numeroGuia, fichaBanco, fichaReceita, receita, versaoRecurso, fonteRecurso, caFixo, dataGuia, valorGuia, contaBancoDAO, creditoDAO, entradasCaixaDAO, itemGuiaDAO, recContaBancoDAO);
                            }
                            stmt3.close();
                            rs3.close();
                        } else {
                            contaBanco(con, usuario, anoAtual, tipoReceita, numeroGuia, fichaBanco, fichaReceita, receita, versaoRecurso, fonteRecurso, caFixo, dataGuia, valorGuia, contaBancoDAO, creditoDAO, entradasCaixaDAO, itemGuiaDAO, recContaBancoDAO);
                        }
                    }
                } else {
                    stmt2 = con.prepareStatement(
                            "Select DISTINCT FICHA, FONTE, COD_CONTA, BANCO_CONTA, SUM(VALOR) "
                                    + " From " + usuario + ".MOV_FIN_" + anoSonner + " A "
                                    + " Where TIPO_VOUCHER = 1 "
                                    + " And VOUCHER = ? "
                                    + " And TRIM(DOC_ORC_TIPO) = 'RO' "
                                    + " And exists ( Select * From " + usuario + ".ORCTO_" + anoSonner + " X Where A.FICHA = X.FICHA And TRIM(X.TEM_CONTA_RETIF) is null ) "
                                    + " GROUP BY FICHA, FONTE, COD_CONTA, BANCO_CONTA "
                                    + " Order by FICHA ");
                    stmt2.setInt(1, numeroGuia);
                    rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        fichaReceita = rs2.getInt(1);
                        fonte = rs2.getString(2).trim();
                        receita = rs2.getString(3).trim();
                        fichaBanco = rs2.getInt(4);
                        valorGuia = rs2.getDouble(5);

                        // Fonte Recurso
                        FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                        versaoRecurso = frutal.getVersaoRecurso();
                        fonteRecurso = frutal.getFonteRecurso();
                        caFixo = frutal.getCAFixo();

                        contaBanco(con, usuario, anoAtual, tipoReceita, numeroGuia, fichaBanco, fichaReceita, receita, versaoRecurso, fonteRecurso, caFixo, dataGuia, valorGuia, contaBancoDAO, creditoDAO, entradasCaixaDAO, itemGuiaDAO, recContaBancoDAO);
                    }
                }
                stmt2.close();
                rs2.close();
            }
            stmt.close();
            rs.close();
            // FINAL DA GUIA OR�AMENTARIA

            System.out.println("INICIANDO GUIAS EXTRA-ORÇAMENTÁRIAS");

            // INICIO GUIA EXTRA-OR�AMENTARIAS
            tipoReceita = "E";
            stmt = con.prepareStatement(
                    "Select DISTINCT VOUCHER, DATA "
                            + " From " + usuario + ".MOV_FIN_" + anoSonner
                            + " Where TIPO_VOUCHER = 1 "
                            + " And TRIM(DOC_ORC_TIPO) = 'RE' " + teste
                            + " GROUP BY VOUCHER, DATA "
                            + " Order by VOUCHER");
            rs = stmt.executeQuery();
            while (rs.next()) {

                numeroGuia = rs.getInt(1);
                dataGuia = rs.getDate(2);

                // Guia de Receitas
                GuiaReceita guiaRec = guiaRecDAO.seleciona(con, usuario, anoAtual, tipoReceita, numeroGuia);
                if (guiaRec.getAno() == null) {
                    guiaReceita(con, usuario, anoAtual, numeroGuia, tipoReceita, dataGuia, "Guia de Receita Extra Orçamentária - ", guiaRecDAO);
                }

                if (anoSonner >= 2017) {
                    stmt2 = con.prepareStatement(
                            "Select DISTINCT CREDITE, FONTE, BANCO_CONTA, DEST_TCE, SUM(VALOR) "
                                    + " From " + usuario + ".MOV_FIN_" + anoSonner
                                    + " Where TIPO_VOUCHER = 1 "
                                    + " And VOUCHER = ? "
                                    + " And TRIM(DOC_ORC_TIPO) = 'RE' "
                                    + " GROUP BY CREDITE, FONTE, BANCO_CONTA, DEST_TCE "
                                    + " Having SUM(VALOR) > 0 "
                                    + " Order by CREDITE ");
                    stmt2.setInt(1, numeroGuia);
                    rs2 = stmt2.executeQuery();
                    while (rs2.next()) {

                        fichaReceita = rs2.getInt(1);
                        fonte = rs2.getString(2).trim();
                        fichaBanco = rs2.getInt(3);
                        fonte2 = rs2.getString(4).trim();
                        valorGuia = rs2.getDouble(5);

                        receita = ("000" + fichaReceita).substring(3, 6);

                        // Fonte Recurso
                        FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                        versaoRecurso = frutal.getVersaoRecurso();
                        fonteRecurso = frutal.getFonteRecurso();
                        caFixo = frutal.getCAFixo();

                        if (!fonte2.equals("")) {
                            fonteRecurso = Integer.parseInt(fonte2);
                        }

                        contaBanco(con, usuario, anoAtual, tipoReceita, numeroGuia, fichaBanco, fichaReceita, receita, versaoRecurso, fonteRecurso, caFixo, dataGuia, valorGuia, contaBancoDAO, creditoDAO, entradasCaixaDAO, itemGuiaDAO, recContaBancoDAO);
                    }
                    stmt2.close();
                    rs2.close();
                } else {
                    stmt2 = con.prepareStatement(
                            "Select DISTINCT CREDITE, FONTE, BANCO_CONTA, SUM(VALOR) "
                                    + " From " + usuario + ".MOV_FIN_" + anoSonner
                                    + " Where TIPO_VOUCHER = 1 "
                                    + " And VOUCHER = ? "
                                    + " And TRIM(DOC_ORC_TIPO) = 'RE' "
                                    + " GROUP BY CREDITE, FONTE, BANCO_CONTA "
                                    + " Having SUM(VALOR) > 0 "
                                    + " Order by CREDITE ");
                    stmt2.setInt(1, numeroGuia);
                    rs2 = stmt2.executeQuery();
                    while (rs2.next()) {

                        fichaReceita = rs2.getInt(1);
                        fonte = rs2.getString(2).trim();
                        fichaBanco = rs2.getInt(3);
                        valorGuia = rs2.getDouble(4);

                        receita = ("000" + fichaReceita).substring(3, 6);

                        // Fonte Recurso
                        FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                        versaoRecurso = frutal.getVersaoRecurso();
                        fonteRecurso = frutal.getFonteRecurso();
                        caFixo = frutal.getCAFixo();

                        contaBanco(con, usuario, anoAtual, tipoReceita, numeroGuia, fichaBanco, fichaReceita, receita, versaoRecurso, fonteRecurso, caFixo, dataGuia, valorGuia, contaBancoDAO, creditoDAO, entradasCaixaDAO, itemGuiaDAO, recContaBancoDAO);
                    }
                    stmt2.close();
                    rs2.close();
                }
            }
            stmt.close();
            rs.close();
            // FINAL DA GUIA EXTRA-OR�AMENTARIA

            System.out.println("FINALIZANDO IMPORTAÇÂO GUIAS RECEITA: " + anoSonner);
        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }

    }

    private static void guiaReceita(Connection con, String usuario, Date anoAtual, int numeroGuia, String tipoReceita, Date dataGuia, String historico, GuiaReceitaDAO guiaRecDAO) {
        GuiaReceita guiaRec = new GuiaReceita();
        guiaRec.setAno(anoAtual);
        guiaRec.setTipo(tipoReceita);
        guiaRec.setNumero(numeroGuia);
        guiaRec.setContribuinte(1);
        guiaRec.setData(dataGuia);
        guiaRec.setHistorico(historico + numeroGuia);
        guiaRec.setVencimento(dataGuia);
        guiaRec.setRecebimento(dataGuia);
        guiaRec.setLancamento(-1);
        guiaRec.setOrigem("DIG");
        guiaRecDAO.insere(con, usuario, guiaRec);
    }

    private static void contaBanco(Connection con, String usuario, Date anoAtual, String tipoReceita, int numeroGuia, int fichaBanco, int fichaReceita, String receita, int versaoRecurso, int fonteRecurso, int caFixo, Date dataGuia, double valorGuia,
                                   ContasBancariaDAO contaBancoDAO, CreditoDAO creditoDAO, EntradasCaixaDAO entradasCaixaDAO, ItensGuiaReceitaDAO itemGuiaDAO, RecContaBancoDAO recContaBancoDAO) {

        ContasBancarias contaBanco;
        Credito credito;
        EntradasCaixa entradasCaixa;
        RecContaBanco recContaBanco;
        ItensGuiaReceita itemGuia;

        itemGuia = new ItensGuiaReceita();
        itemGuia.setAno(anoAtual);
        itemGuia.setTipo(tipoReceita);
        itemGuia.setGuia(numeroGuia);
        itemGuia.setFicha(fichaReceita);
        itemGuia.setVersaoRecurso(versaoRecurso);
        itemGuia.setFonteRecurso(fonteRecurso);
        itemGuia.setCaFixo(caFixo);
        itemGuia.setCaVariavel(0);
        itemGuia.setReceita(receita);
        itemGuia.setValor(valorGuia);
        itemGuiaDAO.insere(con, usuario, itemGuia);

        // Banco
        contaBanco = contaBancoDAO.seleciona(con, usuario, fichaBanco);

        if (fichaBanco == 0) {
            entradasCaixa = new EntradasCaixa();
            entradasCaixa.setData(dataGuia);
            entradasCaixa.setHistorico("Recebimento de Guia de Receita Orçamentária - " + numeroGuia);
            entradasCaixa.setAnoLancto(anoAtual);
            entradasCaixa.setLancamento(-1);
            entradasCaixa.setTipoGuia(tipoReceita);
            entradasCaixa.setGuia(numeroGuia);
            entradasCaixa.setTransferencia(0);
            entradasCaixa.setValor(valorGuia);
            entradasCaixa.setEmpenho(0);
            entradasCaixa.setAnulacao(0);
            entradasCaixa.setAnulRecDedutora(0);
            entradasCaixa.setParcela(0);
            entradasCaixa.setVersaoRecurso(versaoRecurso);
            entradasCaixa.setFonteRecurso(fonteRecurso);
            entradasCaixa.setRegulaFUNDEB(0);
            entradasCaixaDAO.insere(con, usuario, entradasCaixa);
        } else {
            credito = new Credito();
            credito.setFichaConta(fichaBanco);
            credito.setBanco(contaBanco.getBanco());
            credito.setAgencia(contaBanco.getAgencia());
            credito.setConta(contaBanco.getCodigo());
            credito.setData(dataGuia);
            credito.setHistorico("Recebimento de Guia de Receita Orçamentária - " + numeroGuia);
            credito.setAnoLancto(anoAtual);
            credito.setLancamento(-1);
            credito.setTipoGuia(tipoReceita);
            credito.setGuia(numeroGuia);
            credito.setValor(valorGuia);
            credito.setVersaoRecurso(versaoRecurso);
            credito.setFonteRecurso(fonteRecurso);
            credito.setCaFixo(caFixo);
            credito.setCaVariavel(0);
            creditoDAO.insere(con, usuario, credito);
        }

        recContaBanco = new RecContaBanco();
        recContaBanco.setAno(anoAtual);
        recContaBanco.setTipo(tipoReceita);
        recContaBanco.setGuia(numeroGuia);
        recContaBanco.setFicha(fichaReceita);
        recContaBanco.setFichaBanco(fichaBanco);
        recContaBanco.setVersaoRecurso(versaoRecurso);
        recContaBanco.setRecurso(fonteRecurso);
        recContaBanco.setCaFixo(caFixo);
        recContaBanco.setCaVariavel(0);
        recContaBanco.setVersaoRecursoBanco(versaoRecurso);
        recContaBanco.setFonteRecursoBanco(fonteRecurso);
        recContaBanco.setCaFixoBanco(caFixo);
        recContaBanco.setCaVariavelBanco(0);
        recContaBanco.setItem(1);
        recContaBanco.setValor(valorGuia);
        recContaBancoDAO.insere(con, usuario, recContaBanco);
    }
}
