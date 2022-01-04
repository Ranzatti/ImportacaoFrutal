import bean.*;
import dao.*;
import db.*;

import java.sql.*;

public class ImportaPagamento {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2, stmt3;
        ResultSet rs = null;
        ResultSet rs2, rs3;

        String usuario = "FRUTAL";
        Date anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        int autorizacao, seq, documento, parcela, liquidacao, ficha, fornecedor, liquidante, versaoRecurso, fonteRecurso, caFixo, nroOP, fichaConta, bancoDocNro;
        Date dataAutorizacao, anoRestos, dataConcilia;
        String tipoDoc, docOrcTipo, docOrcNro, docOrc, tipoRestos, historico, cpfLiquidante, comprovanteTipo, comprovanteNro, fonte, bancoTipoDoc;
        double valor, totalDesconto, valorLiquido, valorNP, valorP;

        Frutal frutalDAO = new Frutal();
        AutPagtoDAO autPagtoDAO = new AutPagtoDAO();
        ItensAutPagtoDAO itensAutPagtoDAO = new ItensAutPagtoDAO();
        //AutPagtoFonteRecDAO autPagtoFonteRecDAO = new AutPagtoFonteRecDAO();
        GuiaReceitaDAO guiaRecDAO = new GuiaReceitaDAO();
        ItensGuiaReceitaDAO itemGuiaDAO = new ItensGuiaReceitaDAO();
        DescontosOPDAO descontosOPDAO = new DescontosOPDAO();
        DescontosPagtoDAO descontosPagtoDAO = new DescontosPagtoDAO();
        OrdensPagtoDAO ordensPagtoDAO = new OrdensPagtoDAO();
        PagtoOPDAO pagtoOPDAO = new PagtoOPDAO();
        OPFonteRecursoDAO opFonteDAO = new OPFonteRecursoDAO();
        BaixaRestosDAO baixaRestosDAO = new BaixaRestosDAO();
        LiquidaRestosDAO liquidaRestosDAO = new LiquidaRestosDAO();
        LiqRestosCronograDAO liqRestCronograDAO = new LiqRestosCronograDAO();
        ContasBancariaDAO contaBancoDAO = new ContasBancariaDAO();
        DebitoDAO debitoDAO = new DebitoDAO();
        ChequeDAO chequeDAO = new ChequeDAO();

        autPagtoDAO.delete(con, usuario, anoAtual);
        itensAutPagtoDAO.delete(con, usuario, anoAtual);
        //autPagtoFonteRecDAO.delete(con, usuario, anoAtual);
        itemGuiaDAO.delete(con, usuario, anoAtual, "AUT");
        guiaRecDAO.delete(con, usuario, anoAtual, "AUT");
        descontosPagtoDAO.delete(con, usuario, anoAtual);
        descontosOPDAO.delete(con, usuario, anoAtual);
        debitoDAO.deleteAutorizacao(con, usuario, anoAtual);
        chequeDAO.deleteAutorizacao(con, usuario, anoAtual);
        baixaRestosDAO.delete(con, usuario, anoAtual);
        liquidaRestosDAO.delete(con, usuario, anoAtual);
        liqRestCronograDAO.delete(con, usuario, anoAtual);
        pagtoOPDAO.deleteRestos(con, usuario, anoAtual);
        opFonteDAO.deleteRestos(con, usuario, anoAtual);
        ordensPagtoDAO.deleteRestos(con, usuario, anoAtual);

        String teste = "";
        //teste = " and VOUCHER in( 13 ) ";

        boolean bProcessaEmpenho_OP = true;
        boolean bProcessaRestosPagar = true;

        try {
            System.out.println("INICIANDO IMPORTA��O PAGAMENTO: " + anoSonner);
            stmt = con.prepareStatement(
                    "Select distinct VOUCHER, DATA "
                            + " From " + usuario + ".MOV_FIN_" + anoSonner
                            + " Where ( "
                            + "	TRIM(DOC_ORC_TIPO ) in ( Select TRIM(CODIGO) from " + usuario + ".TIPODOC_" + anoSonner + " Where TRIM(SISTEMA) = 'R' ) or "
                            + " TRIM(DOC_ORC_TIPO) in ( 'EO', 'SE', 'OP' ) "
                            + ") "
                            + " And TIPO_VOUCHER = 2 "
                            + " And TIPO_LINHA in ( 'N', 'F' ) " + teste
                            + " Order by 1 ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                autorizacao = rs.getInt(1);
                dataAutorizacao = rs.getDate(2);

                //System.out.println("Autoriza��o - " + autorizacao );

                AutPagto autPagto = autPagtoDAO.seleciona(con, usuario, anoAtual, autorizacao);
                if (autPagto.getAno() == null) {
                    autPagto = new AutPagto();
                    autPagto.setAno(anoAtual);
                    autPagto.setNumero(autorizacao);
                    autPagto.setDataAutorizacao(dataAutorizacao);
                    autPagto.setLancamento(-1);
                    autPagto.setDataPagamento(dataAutorizacao);
                    autPagtoDAO.insere(con, usuario, autPagto);
                }

                // PAGAMENTO DE EMPENHOS, SUBEMPENHOS e OPS
                if (bProcessaEmpenho_OP) {
                    stmt2 = con.prepareStatement(
                            "Select DOC_ORC_TIPO , DOC_ORC_NRO, DOC_ORC, DOC_TIPO, DOC_NRO, VALOR"
                                    + " From " + usuario + ".MOV_FIN_" + anoSonner
                                    + " Where TIPO_VOUCHER = 2 "
                                    + " And TRIM(DOC_ORC_TIPO) in ( 'EO', 'SE', 'OP' ) "
                                    + " And VOUCHER = ? "
                                    + " Order by SEQ ");
                    stmt2.setInt(1, autorizacao);
                    rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        docOrcTipo = rs2.getString(1).trim();
                        docOrcNro = rs2.getString(2).trim();
                        docOrc = rs2.getString(3).trim();
                        comprovanteTipo = rs2.getString(4).trim();
                        comprovanteNro = rs2.getString(5).trim();
                        valor = rs2.getDouble(6);

                        tipoDoc = (docOrcTipo.equals("OP") ? "O" : "E");
                        documento = Integer.parseInt(docOrcNro.substring(0, 5));
                        parcela = (docOrcTipo.equals("SE") ? Integer.parseInt(docOrcNro.substring(5, 8)) : 1);

                        totalDesconto = frutalDAO.totalDescontos(con, usuario, anoSonner, docOrc);
                        valorLiquido = valor - totalDesconto;

                        if (docOrcTipo.equals("EO") || docOrcTipo.equals("SE")) {
                            stmt3 = con.prepareStatement(
                                    "Update " + usuario + ".CBPPAGAMENTOS "
                                            + " Set	DATAPAGAMENTO = ?, VALORPAGAMENTO = ?, DESCONTO = ? "
                                            + " Where ANO = ? "
                                            + " And EMPENHO = ? "
                                            + " And PAGAMENTO = ? ");
                            stmt3.setDate(1, dataAutorizacao);
                            stmt3.setDouble(2, valorLiquido);
                            stmt3.setDouble(3, totalDesconto);
                            stmt3.setDate(4, anoAtual);
                            stmt3.setInt(5, documento);
                            stmt3.setInt(6, parcela);
                            stmt3.executeUpdate();
                            stmt3.close();
                        } else if (docOrcTipo.equals("OP")) {
                            stmt3 = con.prepareStatement(
                                    "Update " + usuario + ".CBPORDENSPAGTO "
                                            + " Set DESCONTO = ? "
                                            + " Where ANO = ? "
                                            + " And NUMERO = ? ");
                            stmt3.setDouble(1, totalDesconto);
                            stmt3.setDate(2, anoAtual);
                            stmt3.setInt(3, documento);
                            stmt3.executeUpdate();
                            stmt3.close();

                            stmt3 = con.prepareStatement(
                                    "Update " + usuario + ".CBPPAGTOOPS "
                                            + " Set DATAPAGTO = ?, VALORPAGTO = ? "
                                            + " Where ANO = ? "
                                            + " And OP = ? ");
                            stmt3.setDate(1, dataAutorizacao);
                            stmt3.setDouble(2, valorLiquido);
                            stmt3.setDate(3, anoAtual);
                            stmt3.setInt(4, documento);
                            stmt3.executeUpdate();
                            stmt3.close();
                        }

                        // Inserindo itens da autorizacao
                        ItensAutPagto itensAutPagto = new ItensAutPagto();
                        itensAutPagto.setAno(anoAtual);
                        itensAutPagto.setAutorizacao(autorizacao);
                        itensAutPagto.setTipoDoc(tipoDoc);
                        itensAutPagto.setDocumento(documento);
                        itensAutPagto.setParcela(parcela);
                        itensAutPagtoDAO.insere(con, usuario, itensAutPagto);

                        // Inserindo documento pagamento
                        insereDocPagto(con, usuario, anoAtual, tipoDoc, documento, parcela, dataAutorizacao, comprovanteTipo, comprovanteNro, valor);

                        // Descontos
                        desconto(con, usuario, anoSonner, autorizacao, dataAutorizacao, tipoDoc, documento, parcela, docOrcTipo, docOrcNro, guiaRecDAO, itemGuiaDAO, descontosOPDAO, descontosPagtoDAO);
                    }
                    stmt2.close();
                    rs2.close();
                }
                // FINAL PAGAMENTO DE EMPENHOS, SUBEMPENHOS e OPS

                // RESTOS A PAGAR
                if (bProcessaRestosPagar) {
                    stmt2 = con.prepareStatement(
                            "Select SEQ, DOC_ORC_TIPO, DOC_ORC_NRO, DOC_ORC, DOC_TIPO, DOC_NRO, FONTE, DEBITE, FAVORECIDO, VALOR"
                                    + " From " + usuario + ".MOV_FIN_" + anoSonner
                                    + " Where TIPO_VOUCHER = 2 "
                                    + " And TRIM(DOC_ORC_TIPO) in ( Select CODIGO from " + usuario + ".TIPODOC_" + anoSonner + " where SISTEMA = 'R' ) "
                                    + " And VOUCHER = ? "
                                    + " Order by SEQ ");
                    stmt2.setInt(1, autorizacao);
                    rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        seq = rs2.getInt(1);
                        docOrcTipo = rs2.getString(2).trim();
                        docOrcNro = rs2.getString(3).trim();
                        docOrc = rs2.getString(4).trim();
                        comprovanteTipo = rs2.getString(5).trim();
                        comprovanteNro = rs2.getString(6).trim();
                        fonte = rs2.getString(7).trim();
                        ficha = rs2.getInt(8);
                        fornecedor = rs2.getInt(9);
                        valor = rs2.getDouble(10);

                        totalDesconto = frutalDAO.totalDescontos(con, usuario, anoSonner, docOrc);
                        valorLiquido = valor - totalDesconto;

                        anoRestos = java.sql.Date.valueOf(2000 + Integer.parseInt(docOrcTipo) + "-01-01");
                        documento = Integer.parseInt(docOrcNro.substring(0, 5));
                        parcela = (docOrcNro.length() > 5 ? Integer.parseInt(docOrcNro.substring(5, docOrcNro.length())) : 1);
                        nroOP = ordensPagtoDAO.getMax(con, usuario, anoAtual);

                        historico = frutalDAO.historico(con, usuario, anoSonner, docOrc);

                        tipoRestos = "N";
                        stmt3 = con.prepareStatement(
                                "Select * "
                                        + " From " + usuario + ".CBPRESTOSPROCPARC "
                                        + " Where ANOEMPENHO = ? "
                                        + " And EMPENHO = ? "
                                        + " And PARCELA = ? ");
                        stmt3.setDate(1, anoRestos);
                        stmt3.setInt(2, documento);
                        stmt3.setInt(3, parcela);
                        rs3 = stmt3.executeQuery();
                        if (rs3.next()) {
                            tipoRestos = "P";
                        }
                        stmt3.close();
                        rs3.close();

                        if (tipoRestos.equals("P")) {
                            valorP = valor;
                            valorNP = 0;
                        } else {
                            valorNP = valor;
                            valorP = 0;

                            liquidante = 0;
                            stmt3 = con.prepareStatement(
                                    "Select LIQUIDANTE "
                                            + " From " + usuario + ".MOV_ORC_" + anoSonner
                                            + " Where TRIM(CODIGO) = ? ");
                            stmt3.setString(1, docOrc);
                            rs3 = stmt3.executeQuery();
                            if (rs3.next()) {
                                liquidante = rs3.getInt(1);
                            }
                            stmt3.close();
                            rs3.close();

                            cpfLiquidante = frutalDAO.cpfLiquidante(con, usuario, anoSonner, liquidante);

                            liquidacao = liquidaRestosDAO.getMax(con, usuario, anoRestos, documento);

                            LiquidaRestos liquidaRestos = new LiquidaRestos();
                            liquidaRestos.setAnoEmpenho(anoRestos);
                            liquidaRestos.setEmpenho(documento);
                            liquidaRestos.setLiquidacao(liquidacao);
                            liquidaRestos.setDataLiquidacao(dataAutorizacao);
                            liquidaRestos.setValor(valorNP);
                            liquidaRestos.setHistorico(historico);
                            liquidaRestos.setVencimento(dataAutorizacao);
                            liquidaRestos.setLiquidante(cpfLiquidante);
                            liquidaRestos.setAnoFato(anoAtual);
                            liquidaRestos.setFato(-1);
                            liquidaRestosDAO.insere(con, usuario, liquidaRestos);

                            LiqRestCronogra liqRestCronogra = new LiqRestCronogra();
                            liqRestCronogra.setAnoEmpenho(anoRestos);
                            liqRestCronogra.setEmpenho(documento);
                            liqRestCronogra.setLiquidacao(liquidacao);
                            liqRestCronogra.setParcela(parcela);
                            liqRestCronogra.setAnoOP(anoAtual);
                            liqRestCronogra.setNroOP(nroOP);
                            liqRestCronograDAO.insere(con, usuario, liqRestCronogra);
                        }

                        OrdensPagto ordensPagto = new OrdensPagto();
                        ordensPagto.setAno(anoAtual);
                        ordensPagto.setNumero(nroOP);
                        ordensPagto.setFicha(ficha);
                        ordensPagto.setFornecedor(fornecedor);
                        ordensPagto.setDataOP(dataAutorizacao);
                        ordensPagto.setHistorico(historico);
                        ordensPagto.setValor(valor);
                        ordensPagto.setVencimento(dataAutorizacao);
                        ordensPagto.setStatus("A");
                        ordensPagto.setAnoRestos(anoRestos);
                        ordensPagto.setEmpenhoRestos(documento);
                        ordensPagto.setParcelaRestos(parcela);
                        ordensPagto.setValorP(valorP);
                        ordensPagto.setValorNP(valorNP);
                        ordensPagto.setDesconto(totalDesconto);
                        ordensPagto.setAnoLancto(anoAtual);
                        ordensPagto.setLancamento(-1);
                        ordensPagtoDAO.insere(con, usuario, ordensPagto);

                        PagtoOP pagtoOP = new PagtoOP();
                        pagtoOP.setAno(anoAtual);
                        pagtoOP.setOp(nroOP);
                        pagtoOP.setValorPagto(valorLiquido);
                        pagtoOP.setDataPagto(dataAutorizacao);
                        pagtoOPDAO.insere(con, usuario, pagtoOP);

                        FonteRecursoFrutal frutal = fonteRecurso2016(con, usuario, anoSonner, autorizacao, seq, fonte);
                        versaoRecurso = frutal.getVersaoRecurso();
                        fonteRecurso = frutal.getFonteRecurso();
                        caFixo = frutal.getCAFixo();

                        OPFonteRecurso opFonte = new OPFonteRecurso();
                        opFonte.setAno(anoAtual);
                        opFonte.setOp(nroOP);
                        opFonte.setVersaoRecurso(versaoRecurso);
                        opFonte.setFonteRecurso(fonteRecurso);
                        opFonte.setCaFixo(caFixo);
                        opFonte.setCaVariavel(0);
                        opFonte.setValor(valor);
                        opFonteDAO.insere(con, usuario, opFonte);

                        BaixaRestos baixaRestos = new BaixaRestos();
                        baixaRestos.setAno(anoRestos);
                        baixaRestos.setEmpenho(documento);
                        baixaRestos.setAnoOP(anoAtual);
                        baixaRestos.setNroOP(nroOP);
                        baixaRestos.setDataBaixa(dataAutorizacao);
                        baixaRestos.setValorBaixa(valor);
                        baixaRestos.setValorNP(valorNP);
                        baixaRestos.setValorP(valorP);
                        baixaRestosDAO.insere(con, usuario, baixaRestos);

                        // Itens da Autorizacao
                        ItensAutPagto itensAutPagto = new ItensAutPagto();
                        itensAutPagto.setAno(anoAtual);
                        itensAutPagto.setAutorizacao(autorizacao);
                        itensAutPagto.setTipoDoc("O");
                        itensAutPagto.setDocumento(nroOP);
                        itensAutPagto.setParcela(parcela);
                        itensAutPagtoDAO.insere(con, usuario, itensAutPagto);

                        // Inserindo documento pagamento
                        insereDocPagto(con, usuario, anoAtual, "O", nroOP, 1, dataAutorizacao, comprovanteTipo, comprovanteNro, valor);

                        // Descontos
                        desconto(con, usuario, anoSonner, autorizacao, dataAutorizacao, "O", nroOP, 1, docOrcTipo, docOrcNro, guiaRecDAO, itemGuiaDAO, descontosOPDAO, descontosPagtoDAO);
                    }
                    stmt2.close();
                    rs2.close();
                }
                // FINAL RESTOS A PAGAR

                // MOVIMENTO FINANCEIRO
                stmt2 = con.prepareStatement(
                        "Select distinct SEQ, BANCO_CONTA, BANCO_TIPO_DOC, NVL(TRIM(BANCO_DOC_NRO),0), DATA_CONCILIA, FONTE, VALOR "
                                + " From " + usuario + ".MOV_FIN_" + anoSonner
                                + " Where VOUCHER = ? "
                                + " And TIPO_VOUCHER = 2 "
                                + " And TRIM(BANCO_TIPO_DOC) in ( 'AD', 'CH' ) "
                                + " Order by SEQ ");
                stmt2.setInt(1, autorizacao);
                rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    seq = rs2.getInt(1);
                    fichaConta = rs2.getInt(2);
                    bancoTipoDoc = rs2.getString(3).trim();
                    bancoDocNro = (bancoTipoDoc.equals("CH") ? Integer.parseInt(rs2.getString(4).trim()) : 0);
                    dataConcilia = rs2.getDate(5);
                    fonte = rs2.getString(6).trim();
                    valor = rs2.getDouble(7);

                    historico = "Pagamento Autorização - " + autorizacao;

                    FonteRecursoFrutal frutal = fonteRecurso2016(con, usuario, anoSonner, autorizacao, seq, fonte);
                    versaoRecurso = frutal.getVersaoRecurso();
                    fonteRecurso = frutal.getFonteRecurso();
                    caFixo = frutal.getCAFixo();

                    // Banco
                    ContasBancarias contaBanco = contaBancoDAO.seleciona(con, usuario, fichaConta);

                    if (bancoTipoDoc.equals("AD")) {
                        Debito debito = new Debito();
                        debito.setFichaConta(fichaConta);
                        debito.setBanco(contaBanco.getBanco());
                        debito.setAgencia(contaBanco.getAgencia());
                        debito.setConta(contaBanco.getCodigo());
                        debito.setData(dataAutorizacao);
                        debito.setHistorico(historico);
                        debito.setAnoLancto(anoAtual);
                        debito.setLancamento(-1);
                        debito.setAnulacaoReceita(0);
                        debito.setValor(valor);
                        debito.setVersaoRecurso(versaoRecurso);
                        debito.setFonteRecurso(fonteRecurso);
                        debito.setCaFixo(caFixo);
                        debito.setCaVariavel(0);
                        debito.setFinalidade("P");
                        debito.setAutPagto(autorizacao);
                        debito.setTransferencia(0);
                        debitoDAO.insere(con, usuario, debito);
                    }

                    if (bancoTipoDoc.equals("CH")) {
                        Cheque cheque = new Cheque();
                        cheque.setFichaConta(fichaConta);
                        cheque.setBanco(contaBanco.getBanco());
                        cheque.setAgencia(contaBanco.getAgencia());
                        cheque.setConta(contaBanco.getCodigo());
                        cheque.setNumero(bancoDocNro);
                        cheque.setData(dataAutorizacao);
                        cheque.setHistorico(historico);
                        cheque.setAnoLancto(anoAtual);
                        cheque.setLancamento(-1);
                        cheque.setAnulacaoReceita(0);
                        cheque.setTransferencia(0);
                        cheque.setValor(valor);
                        cheque.setVersaoRecurso(versaoRecurso);
                        cheque.setFonteRecurso(fonteRecurso);
                        cheque.setCaFixo(caFixo);
                        cheque.setCaVariavel(0);
                        cheque.setFinalidade("P");
                        cheque.setAutPagto(autorizacao);
                        cheque.setDataEmissao(dataAutorizacao);
                        cheque.setDataBaixa(dataConcilia);
                        chequeDAO.insere(con, usuario, cheque);
                    }
                }
                stmt2.close();
                rs2.close();

//				AutPagtoFonteRec autPagtoFonteRec = new AutPagtoFonteRec();
//				autPagtoFonteRec.setAno(anoAtual);
//				autPagtoFonteRec.setAutorizacao(autorizacao);
//				autPagtoFonteRec.setFichaBanco(fichaConta);
//				autPagtoFonteRec.setTipoDoc(tipoDoc);
//				autPagtoFonteRec.setDocumento(documento);
//				autPagtoFonteRec.setParcela(parcela);
//				autPagtoFonteRec.setSequencial(indice);
//				autPagtoFonteRec.setVersaoRecurso(versaoRecurso);
//				autPagtoFonteRec.setFonteRecurso(fonteRecurso);
//				autPagtoFonteRec.setCaFixo(caFixo);
//				autPagtoFonteRec.setCaVariavel(0);
//				autPagtoFonteRec.setValor(valor);
//				autPagtoFonteRecDAO.insere(con, usuario, autPagtoFonteRec);
                // FINAL MOVIMENTO FINANCEIRO
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            System.out.println("FINALIZANDO IMPORTA��O PAGAMENTO: " + anoSonner);
            ConexaoDB.closeConection(con, stmt, rs);
        }

    }

    private static void desconto(Connection con, String usuario, int anoSonner, int autorizacao, Date dataAutorizacao, String tipoDoc, int documento, int parcela, String docOrcTipo, String docOrcNro,
                                 GuiaReceitaDAO guiaRecDAO, ItensGuiaReceitaDAO itensGuiaReceitaDAO, DescontosOPDAO descontosOPDAO, DescontosPagtoDAO descontosPagtoDAO) throws SQLException {

        PreparedStatement stmtAux;
        ResultSet rsAux;
        int guia, fichaOrc, fichaExt, ficha, versaoRecurso, fonteRecurso, caFixo;
        String select, tipoGuia, receita, fonte, contaReceita;
        GuiaReceita guiaRec;
        ItensGuiaReceita itemGuia;
        DescontosOP descontosOP;
        DescontosPagto descontosPagto;
        double valor;

        DescontosTempDAO descontosTempDAO = new DescontosTempDAO();
        Frutal frutalDAO = new Frutal();
        FonteRecursoFrutal frutal;

        Date anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        select = "Select DOC_ORC_TIPO, FICHA, CREDITE, COD_CONTA, 0 as FONTE, VALOR";
        if (anoSonner >= 2016) {
            select = "Select DOC_ORC_TIPO, FICHA, CREDITE, COD_CONTA, DEST_TCE as FONTE, VALOR ";
        }
        stmtAux = con.prepareStatement(
                select
                        + " From " + usuario + ".MOV_FIN_" + anoSonner
                        + " Where VOUCHER = ?  "
                        + " And TIPO_VOUCHER = 2 "
                        + " And TRIM(DOC_ORC_TIPO) in ( 'RE', 'RO' ) "
                        + " And TRIM(DOC_TIPO) = ? "
                        + " And TRIM(DOC_NRO) = ? "
                        + " Order by SEQ ");
        stmtAux.setInt(1, autorizacao);
        stmtAux.setString(2, docOrcTipo);
        stmtAux.setString(3, docOrcNro);
        rsAux = stmtAux.executeQuery();
        while (rsAux.next()) {

            descontosTempDAO.delete(con, usuario, anoAtual, tipoDoc, documento, parcela);

            tipoGuia = rsAux.getString(1).trim().substring(1, 2);
            fichaOrc = rsAux.getInt(2);
            fichaExt = rsAux.getInt(3);
            contaReceita = rsAux.getString(4).trim();
            fonte = rsAux.getString(5).trim();
            valor = rsAux.getDouble(6);

            ficha = (tipoGuia.equals("E") ? fichaExt : fichaOrc);
            receita = (tipoGuia.equals("E") ? Integer.toString(fichaExt) : contaReceita);
            guia = guiaRecDAO.getMax(con, usuario, anoAtual, tipoGuia);

            // Guia de Receitas
            guiaRec = guiaRecDAO.seleciona(con, usuario, anoAtual, tipoGuia, guia);
            if (guiaRec.getAno() == null) {
                guiaRec = new GuiaReceita();
                guiaRec.setAno(anoAtual);
                guiaRec.setTipo(tipoGuia);
                guiaRec.setNumero(guia);
                guiaRec.setContribuinte(1);
                guiaRec.setData(dataAutorizacao);
                guiaRec.setHistorico("Guia de Receita de desconto - " + guia);
                guiaRec.setVencimento(dataAutorizacao);
                guiaRec.setRecebimento(dataAutorizacao);
                guiaRec.setLancamento(-1);
                guiaRec.setOrigem("AUT");
                guiaRec.setAutPagto(autorizacao);
                guiaRecDAO.insere(con, usuario, guiaRec);

                if (tipoDoc.equals("O")) {
                    descontosOP = new DescontosOP();
                    descontosOP.setAno(anoAtual);
                    descontosOP.setOp(documento);
                    descontosOP.setParcela(parcela);
                    descontosOP.setTipoGuia(tipoGuia);
                    descontosOP.setGuiaReceita(guia);
                    descontosOPDAO.insere(con, usuario, descontosOP);
                } else {
                    descontosPagto = new DescontosPagto();
                    descontosPagto.setAno(anoAtual);
                    descontosPagto.setEmpenho(documento);
                    descontosPagto.setParcela(parcela);
                    descontosPagto.setTipoGuia(tipoGuia);
                    descontosPagto.setGuiaReceita(guia);
                    descontosPagtoDAO.insere(con, usuario, descontosPagto);
                }
            }

            frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
            versaoRecurso = frutal.getVersaoRecurso();
            fonteRecurso = frutal.getFonteRecurso();
            caFixo = frutal.getCAFixo();

            itemGuia = new ItensGuiaReceita();
            itemGuia.setAno(anoAtual);
            itemGuia.setTipo(tipoGuia);
            itemGuia.setGuia(guia);
            itemGuia.setFicha(ficha);
            itemGuia.setVersaoRecurso(versaoRecurso);
            itemGuia.setFonteRecurso(fonteRecurso);
            itemGuia.setCaFixo(caFixo);
            itemGuia.setCaVariavel(0);
            itemGuia.setReceita(receita);
            itemGuia.setValor(valor);
            itensGuiaReceitaDAO.insere(con, usuario, itemGuia);
        }
        stmtAux.close();
        rsAux.close();
    }

    private static void insereDocPagto(Connection con, String usuario, Date anoAtual, String tipoDoc, int documento, int parcela, Date dataEmissao, String comprovanteTipo, String comprovanteNro, double valor) {

        int tipoDocPagto = (comprovanteTipo.equals("NF") ? 1 : 999);

        if (!comprovanteTipo.equals("")) {

            DocPagtoDAO docPagtoDAO = new DocPagtoDAO();

            DocPagto docPagto = new DocPagto();
            docPagto.setAno(anoAtual);
            docPagto.setTipoDoc(tipoDoc);
            docPagto.setDocumento(documento);
            docPagto.setParcela(parcela);
            docPagto.setItem(1);
            docPagto.setTipoDocPagto(tipoDocPagto);
            docPagto.setDataEmissao(dataEmissao);
            docPagto.setDescricaoAdicional("Comprovante de Pagamento");
            docPagto.setDocumentoPagto(comprovanteNro);
            docPagto.setValor(valor);
            docPagtoDAO.delete(con, usuario, anoAtual, tipoDoc, documento, parcela);
            docPagtoDAO.insere(con, usuario, docPagto);
        }
    }
//
//	public static String Right(int campo, int length) {
//		String param = "0".repeat(length) + Integer.toString(campo);
//		return param.substring(param.length() - length);
//	}

    private static FonteRecursoFrutal fonteRecurso2016(Connection con, String usuario, int anoSonner, int voucher, int seq, String fonte) throws SQLException {
        PreparedStatement stmtAux;
        ResultSet rsAux;

        Frutal frutalDAO = new Frutal();
        FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
        int versaoRecurso = frutal.getVersaoRecurso();
        int fonteRecurso = frutal.getFonteRecurso();
        int caFixo = frutal.getCAFixo();

        //" And TRIM(DEST_TCE) != '*' "

        if (anoSonner >= 2016) {
            stmtAux = con.prepareStatement(
                    "Select TRIM(DEST_TCE) "
                            + " From " + usuario + ".MOV_FIN_" + anoSonner
                            + " Where VOUCHER = ? "
                            + " And SEQ = ? "
                            + " And TRIM(DEST_TCE) is not null ");
            stmtAux.setInt(1, voucher);
            stmtAux.setInt(2, seq);
            rsAux = stmtAux.executeQuery();
            if (rsAux.next()) {
                fonteRecurso = Integer.parseInt(rsAux.getString(1).trim());
            }
            stmtAux.close();
            rsAux.close();
        }

        frutal.setVersaoRecurso(versaoRecurso);
        frutal.setFonteRecurso(fonteRecurso);
        frutal.setCAFixo(caFixo);

        return frutal;
    }

}
