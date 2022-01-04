import bean.*;
import dao.*;
import db.ConexaoDB;

import java.sql.*;

public class ImportaEmpenhos {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        PreparedStatement stmt3;
        ResultSet rs = null;
        ResultSet rs2;

        String codigo, tipo, contaDespesa, desdobramento, fonte, numero, seq, historico, cpfLiquidante, comprovanteTipo, comprovanteNro;
        int empenho, parcela, liquidante, ficha, fornecedor, versaoRecurso, fonteRecurso, caFixo, liquidacao;
        double valorEmpenho, valorAnulado;
        Date anoAtual, data, vencimento, dataLiquidacao;

        String usuario = "FRUTAL";
        anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        Frutal frutalDAO = new Frutal();
        EmpenhosDAO empenhosDAO = new EmpenhosDAO();
        EmpFonteRecursoDAO empFonteRecursoDAO = new EmpFonteRecursoDAO();
        ItensEmpenhoDAO itensEmpenhoDAO = new ItensEmpenhoDAO();
        AnulacaoEmpenhoDAO anulacaoEmpenhoDAO = new AnulacaoEmpenhoDAO();
        LiquidacaoEmpenhoDAO liquidacaoEmpDAO = new LiquidacaoEmpenhoDAO();
        PagamentoEmpenhoDAO pagamentoEmpDAO = new PagamentoEmpenhoDAO();
        LiquidaPagtoDAO liquidaPagtoDAO = new LiquidaPagtoDAO();
        AnulacaoLiquidaDAO anulacaoLiquidaDAO = new AnulacaoLiquidaDAO();
        AnulacaoLiqParcDAO anulLiqParcDAO = new AnulacaoLiqParcDAO();
        EmpenhoComplementoDAO empComplementoDAO = new EmpenhoComplementoDAO();
        DescontosTempDAO descontosTempDAO = new DescontosTempDAO();

        empenhosDAO.delete(con, usuario, anoAtual);
        empFonteRecursoDAO.delete(con, usuario, anoAtual);
        itensEmpenhoDAO.delete(con, usuario, anoAtual);
        anulacaoEmpenhoDAO.delete(con, usuario, anoAtual);
        liquidacaoEmpDAO.delete(con, usuario, anoAtual);
        pagamentoEmpDAO.delete(con, usuario, anoAtual);
        liquidaPagtoDAO.delete(con, usuario, anoAtual);
        anulacaoLiquidaDAO.delete(con, usuario, anoAtual);
        anulLiqParcDAO.delete(con, usuario, anoAtual);
        empComplementoDAO.delete(con, usuario, anoAtual);
        descontosTempDAO.delete(con, usuario, anoAtual, "E");

        boolean processaEmpenho = true;
        boolean processaSubEmpenho = true;
        boolean processaEmpComplementar = true;

        String teste = "";
        //teste = " and TRIM(CODIGO) = 'EO00070' ";

        try {

            if (processaEmpenho) {
                System.out.println("INICIANDO IMPORTAÇÃO EMPENHOS: " + anoSonner);
                stmt = con.prepareStatement(
                        "Select CODIGO, TIPO, NUMERO, FICHA, DATA, FAVORECIDO, VALOR, CONTA, FONTE, VENCIMENTO, DOC_TIPO, DOC_NRO, LIQUIDACAO, LIQUIDANTE "
                                + " From " + usuario + ".MOV_ORC_" + anoSonner
                                + " Where TRIM(TIPO) in ( 'EO', 'EE', 'EG' )  " + teste
                                + " Order by 1 ");
                rs = stmt.executeQuery();
                while (rs.next()) {

                    codigo = rs.getString(1).trim();
                    tipo = rs.getString(2).trim().substring(1, 2);
                    numero = rs.getString(3).trim();
                    empenho = Integer.parseInt(numero);
                    ficha = rs.getInt(4);
                    data = rs.getDate(5);
                    fornecedor = rs.getInt(6);
                    valorEmpenho = rs.getDouble(7);
                    contaDespesa = rs.getString(8).trim();
                    fonte = rs.getString(9).trim();
                    vencimento = rs.getDate(10);
                    comprovanteTipo = rs.getString(11).trim();
                    comprovanteNro = rs.getString(12).trim();
                    dataLiquidacao = rs.getDate(13);
                    liquidante = rs.getInt(14);

                    //System.out.println("Empenho - " + empenho );

                    desdobramento = contaDespesa.length() == 8 ? contaDespesa.substring(6, 8) : "99";

                    Empenhos empenhos = new Empenhos();
                    empenhos.setAno(anoAtual);
                    empenhos.setNumero(empenho);
                    empenhos.setTipo(tipo);
                    empenhos.setFicha(ficha);
                    empenhos.setDataEmpenho(data);
                    empenhos.setVencimento(vencimento);
                    empenhos.setFornecedor(fornecedor);
                    empenhos.setValorEmpenho(valorEmpenho);
                    empenhos.setDesdobramento(desdobramento);
                    empenhosDAO.insere(con, usuario, empenhos);

                    historico = frutalDAO.historico(con, usuario, anoSonner, codigo);

                    ItensEmpenho itensEmpenho = new ItensEmpenho();
                    itensEmpenho.setAno(anoAtual);
                    itensEmpenho.setEmpenho(empenho);
                    itensEmpenho.setItem(1);
                    itensEmpenho.setTipo("P");
                    itensEmpenho.setDescricao(historico);
                    itensEmpenho.setValorUnitario(valorEmpenho);
                    itensEmpenho.setValorTotal(valorEmpenho);
                    itensEmpenhoDAO.insere(con, usuario, itensEmpenho);

                    // Fonte Recurso
                    FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                    versaoRecurso = frutal.getVersaoRecurso();
                    fonteRecurso = frutal.getFonteRecurso();
                    caFixo = frutal.getCAFixo();

                    if (anoSonner >= 2015) {
                        // Pegando a fonte de recurso do empenho, pois so existe essa coluna a partir de 2015
                        stmt2 = con.prepareStatement(
                                "Select DEST_TCE "
                                        + " From " + usuario + ".MOV_ORC_" + anoSonner
                                        + " Where TRIM(CODIGO) = ? "
                                        + " And TRIM(DEST_TCE) is not null ");
                        stmt2.setString(1, codigo);
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            fonteRecurso = Integer.parseInt(rs2.getString(1));
                        }
                        stmt2.close();
                        rs2.close();
                    }

                    EmpFonteRecurso empFonteRecurso = new EmpFonteRecurso();
                    empFonteRecurso.setAno(anoAtual);
                    empFonteRecurso.setEmpenho(empenho);
                    empFonteRecurso.setVersaoRecurso(versaoRecurso);
                    empFonteRecurso.setFonteRecurso(fonteRecurso);
                    empFonteRecurso.setCaFixo(caFixo);
                    empFonteRecurso.setCaVariavel(0);
                    empFonteRecurso.setValor(valorEmpenho);
                    empFonteRecursoDAO.insere(con, usuario, empFonteRecurso);

                    // Parcela
                    PagamentoEmpenho pagamentoEmp = new PagamentoEmpenho();
                    pagamentoEmp.setAno(anoAtual);
                    pagamentoEmp.setEmpenho(empenho);
                    pagamentoEmp.setPagamento(1);
                    pagamentoEmp.setValorParcela(valorEmpenho);
                    pagamentoEmp.setValorPagamento(0);
                    pagamentoEmp.setDataSubEmpenho(data);
                    pagamentoEmp.setVencimento(vencimento);
                    pagamentoEmp.setHistorico(historico);
                    pagamentoEmp.setDesconto(0);
                    pagamentoEmpDAO.insere(con, usuario, pagamentoEmp);

                    // verificando se tem data de pagamento - tem casos que não tem dada de liquidacao no cadastro do empenho
                    if (dataLiquidacao == null) {
                        stmt2 = con.prepareStatement(
                                "Select DATA "
                                        + " From " + usuario + ".MOV_FIN_" + anoSonner
                                        + " Where TRIM(DOC_ORC) = ? ");
                        stmt2.setString(1, codigo);
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            dataLiquidacao = rs2.getDate(1);
                        }
                    }

                    if (dataLiquidacao != null) {
                        cpfLiquidante = frutalDAO.cpfLiquidante(con, usuario, anoSonner, liquidante);

                        LiquidacaoEmpenho liquidacaoEmp = new LiquidacaoEmpenho();
                        liquidacaoEmp.setAno(anoAtual);
                        liquidacaoEmp.setEmpenho(empenho);
                        liquidacaoEmp.setLiquidacao(1);
                        liquidacaoEmp.setDataLiquidacao(dataLiquidacao);
                        liquidacaoEmp.setValorLiquidacao(valorEmpenho);
                        liquidacaoEmp.setHistorico(historico);
                        liquidacaoEmp.setLiquidante(cpfLiquidante);
                        liquidacaoEmp.setLancamento(-1);
                        liquidacaoEmpDAO.insere(con, usuario, liquidacaoEmp);

                        LiquidaPagto liquidaPagto = new LiquidaPagto();
                        liquidaPagto.setAno(anoAtual);
                        liquidaPagto.setEmpenho(empenho);
                        liquidaPagto.setLiquidacao(1);
                        liquidaPagto.setPagamento(1);
                        liquidaPagtoDAO.insere(con, usuario, liquidaPagto);
                    }

                    // Documento Pagamento
                    insereDocPagto(con, usuario, anoAtual, empenho, 1, data, comprovanteTipo, comprovanteNro, valorEmpenho);

                    // ANULA��O DO EMPENHO
                    stmt2 = con.prepareStatement(
                            "Select CODIGO, SEQ, DATA, VALOR "
                                    + " From " + usuario + ".MOV_ORC_" + anoSonner
                                    + " Where TRIM(TIPO) = 'AE' "
                                    + " And NUMERO = ? "
                                    + " Order by 1 ");
                    stmt2.setString(1, numero);
                    rs2 = stmt2.executeQuery();
                    while (rs2.next()) {

                        codigo = rs2.getString(1).trim();
                        parcela = Integer.parseInt(rs2.getString(2));
                        data = rs2.getDate(3);
                        valorAnulado = Math.abs(rs2.getDouble(4));

                        historico = frutalDAO.historico(con, usuario, anoSonner, codigo);

                        AnulacaoEmpenho anulacaoEmpenho = new AnulacaoEmpenho();
                        anulacaoEmpenho.setAno(anoAtual);
                        anulacaoEmpenho.setEmpenho(empenho);
                        anulacaoEmpenho.setAnulacao(parcela);
                        anulacaoEmpenho.setDataAnulacao(data);
                        anulacaoEmpenho.setValorAnulacao(valorAnulado);
                        anulacaoEmpenho.setHistorico(historico);
                        anulacaoEmpenho.setTipo("X");
                        anulacaoEmpenho.setLancamento(-1);
                        anulacaoEmpenhoDAO.insere(con, usuario, anulacaoEmpenho);

                    }
                    stmt2.close();
                    rs2.close();
                }
                stmt.close();
                rs.close();
            }

            if (processaSubEmpenho) {
                System.out.println("INICIANDO IMPORTAÇÂO SUBEMPENHOS: " + anoSonner);
                stmt = con.prepareStatement(
                        "Select CODIGO, NUMERO, SEQ, DATA, VALOR, LIQUIDANTE, VENCIMENTO, LIQUIDACAO, DOC_TIPO, DOC_NRO "
                                + " From " + usuario + ".MOV_ORC_" + anoSonner
                                + " Where TRIM(TIPO) in ( 'SE' )  "
                                + " And LIQUIDACAO IS NOT NULL " + teste
                                + " Order by 1 ");
                rs = stmt.executeQuery();
                while (rs.next()) {

                    codigo = rs.getString(1).trim();
                    numero = rs.getString(2).trim();
                    empenho = Integer.parseInt(numero);
                    seq = rs.getString(3).trim();
                    parcela = Integer.parseInt(seq);
                    data = rs.getDate(4);
                    valorEmpenho = rs.getDouble(5);
                    liquidante = rs.getInt(6);
                    vencimento = rs.getDate(7);
                    dataLiquidacao = rs.getDate(8);
                    comprovanteTipo = rs.getString(9).trim();
                    comprovanteNro = rs.getString(10).trim();

                    //System.out.println("SubEmpenho - " + empenho + "-" + parcela);

                    historico = frutalDAO.historico(con, usuario, anoSonner, codigo);
                    cpfLiquidante = frutalDAO.cpfLiquidante(con, usuario, anoSonner, liquidante);

                    liquidacao = liquidacaoEmpDAO.getMax(con, usuario, anoAtual, empenho);

                    LiquidacaoEmpenho liquidacaoEmp = new LiquidacaoEmpenho();
                    liquidacaoEmp.setAno(anoAtual);
                    liquidacaoEmp.setEmpenho(empenho);
                    liquidacaoEmp.setLiquidacao(liquidacao);
                    liquidacaoEmp.setDataLiquidacao(dataLiquidacao);
                    liquidacaoEmp.setValorLiquidacao(valorEmpenho);
                    liquidacaoEmp.setHistorico(historico);
                    liquidacaoEmp.setLiquidante(cpfLiquidante);
                    liquidacaoEmp.setLancamento(-1);
                    liquidacaoEmpDAO.insere(con, usuario, liquidacaoEmp);

                    PagamentoEmpenho pagamentoEmp = new PagamentoEmpenho();
                    pagamentoEmp.setAno(anoAtual);
                    pagamentoEmp.setEmpenho(empenho);
                    pagamentoEmp.setPagamento(parcela);
                    pagamentoEmp.setValorParcela(valorEmpenho);
                    pagamentoEmp.setValorPagamento(0);
                    pagamentoEmp.setDataSubEmpenho(data);
                    pagamentoEmp.setVencimento(vencimento);
                    pagamentoEmp.setHistorico(historico);
                    pagamentoEmp.setDesconto(0);
                    pagamentoEmpDAO.insere(con, usuario, pagamentoEmp);

                    LiquidaPagto liquidaPagto = new LiquidaPagto();
                    liquidaPagto.setAno(anoAtual);
                    liquidaPagto.setEmpenho(empenho);
                    liquidaPagto.setLiquidacao(liquidacao);
                    liquidaPagto.setPagamento(parcela);
                    liquidaPagtoDAO.insere(con, usuario, liquidaPagto);

                    frutalDAO.descontosTemp(con, usuario, anoSonner, codigo, "E", empenho, parcela);

                    // Documento Pagamento
                    insereDocPagto(con, usuario, anoAtual, empenho, liquidacao, dataLiquidacao, comprovanteTipo, comprovanteNro, valorEmpenho);

                    // ANULA��O DO SUBEMPENHO
                    stmt2 = con.prepareStatement(
                            "Select CODIGO, DATA, VALOR "
                                    + " From " + usuario + ".MOV_ORC_" + anoSonner
                                    + " Where TRIM(TIPO) = 'AS' "
                                    + " And TRIM(NUMERO) = ? "
                                    + " And TRIM(SEQ) = ? "
                                    + " Order by 1 ");
                    stmt2.setString(1, numero);
                    stmt2.setString(2, seq);
                    rs2 = stmt2.executeQuery();
                    while (rs2.next()) {

                        codigo = rs2.getString(1).trim();
                        data = rs2.getDate(2);
                        valorAnulado = Math.abs(rs2.getDouble(3));

                        historico = frutalDAO.historico(con, usuario, anoSonner, codigo);

                        if (valorEmpenho > valorAnulado) {

                            stmt3 = con.prepareStatement(
                                    "Update " + usuario + ".CBPPAGAMENTOS "
                                            + " Set VALORPARCELA = ? "
                                            + " Where ANO = ? "
                                            + " And EMPENHO = ? "
                                            + " And PAGAMENTO = ? ");
                            stmt3.setDouble(1, valorEmpenho - valorAnulado);
                            stmt3.setDate(2, anoAtual);
                            stmt3.setInt(3, empenho);
                            stmt3.setInt(4, parcela);
                            stmt3.executeUpdate();
                            stmt3.close();

                            stmt3 = con.prepareStatement(
                                    "Update " + usuario + ".CBPLIQUIDACOES "
                                            + " Set VALORLIQUIDACAO = ? "
                                            + " Where ANO = ? "
                                            + " And EMPENHO = ? "
                                            + " And LIQUIDACAO = ? ");
                            stmt3.setDouble(1, valorEmpenho - valorAnulado);
                            stmt3.setDate(2, anoAtual);
                            stmt3.setInt(3, empenho);
                            stmt3.setInt(4, parcela);
                            stmt3.executeUpdate();
                            stmt3.close();

                            liquidacao = 999;
                            parcela = 999;

                            liquidacaoEmp = new LiquidacaoEmpenho();
                            liquidacaoEmp.setAno(anoAtual);
                            liquidacaoEmp.setEmpenho(empenho);
                            liquidacaoEmp.setLiquidacao(liquidacao);
                            liquidacaoEmp.setDataLiquidacao(dataLiquidacao);
                            liquidacaoEmp.setValorLiquidacao(valorAnulado);
                            liquidacaoEmp.setHistorico(historico);
                            liquidacaoEmp.setLiquidante(cpfLiquidante);
                            liquidacaoEmp.setLancamento(-1);
                            liquidacaoEmpDAO.insere(con, usuario, liquidacaoEmp);

                            pagamentoEmp = new PagamentoEmpenho();
                            pagamentoEmp.setAno(anoAtual);
                            pagamentoEmp.setEmpenho(empenho);
                            pagamentoEmp.setDataSubEmpenho(dataLiquidacao);
                            pagamentoEmp.setPagamento(parcela);
                            pagamentoEmp.setValorParcela(valorAnulado);
                            pagamentoEmp.setValorPagamento(0);
                            pagamentoEmp.setDataPagamento(null);
                            pagamentoEmp.setVencimento(vencimento);
                            pagamentoEmp.setHistorico(historico);
                            pagamentoEmp.setDesconto(0);
                            pagamentoEmpDAO.insere(con, usuario, pagamentoEmp);

                            liquidaPagto = new LiquidaPagto();
                            liquidaPagto.setAno(anoAtual);
                            liquidaPagto.setEmpenho(empenho);
                            liquidaPagto.setLiquidacao(liquidacao);
                            liquidaPagto.setPagamento(parcela);
                            liquidaPagtoDAO.insere(con, usuario, liquidaPagto);

                            data = dataLiquidacao;

                        }

                        AnulacaoLiquida anulacaoLiquida = new AnulacaoLiquida();
                        anulacaoLiquida.setAno(anoAtual);
                        anulacaoLiquida.setEmpenho(empenho);
                        anulacaoLiquida.setLiquidacao(liquidacao);
                        anulacaoLiquida.setAnulacao(1);
                        anulacaoLiquida.setData(data);
                        anulacaoLiquida.setHistorico(historico);
                        anulacaoLiquida.setValor(valorAnulado);
                        anulacaoLiquida.setLancamento(-1);
                        anulacaoLiquida.setHistorico(historico);
                        anulacaoLiquidaDAO.insere(con, usuario, anulacaoLiquida, false);

                        AnulacaoLiqParc anulLiqParc = new AnulacaoLiqParc();
                        anulLiqParc.setAno(anoAtual);
                        anulLiqParc.setEmpenho(empenho);
                        anulLiqParc.setLiquidacao(liquidacao);
                        anulLiqParc.setParcela(parcela);
                        anulLiqParcDAO.insere(con, usuario, anulLiqParc);
                    }
                    stmt2.close();
                    rs2.close();
                }
                stmt.close();
                rs.close();
            }

            if (processaEmpComplementar) {
                System.out.println("INICIANDO IMPORTAÇÃO COMPLEMENTO DE EMPENHOS: " + anoSonner);
                stmt = con.prepareStatement(
                        "Select CODIGO, NUMERO, SEQ, DATA, VALOR "
                                + " From " + usuario + ".MOV_ORC_" + anoSonner
                                + " Where TRIM(TIPO) in ( 'EC' ) " + teste
                                + " Order by 1 ");
                rs = stmt.executeQuery();
                while (rs.next()) {

                    codigo = rs.getString(1).trim();
                    empenho = Integer.parseInt(rs.getString(2).trim());
                    parcela = Integer.parseInt(rs.getString(3).trim());
                    data = rs.getDate(4);
                    valorEmpenho = rs.getDouble(5);

                    //System.out.println("Empenho Complementar - " + empenho + "-" + parcela);

                    historico = frutalDAO.historico(con, usuario, anoSonner, codigo);

                    EmpenhoComplemento empComplemento = new EmpenhoComplemento();
                    empComplemento.setAno(anoAtual);
                    empComplemento.setEmpenho(empenho);
                    empComplemento.setComplemento(parcela);
                    empComplemento.setData(data);
                    empComplemento.setValor(valorEmpenho);
                    empComplemento.setDescricao(historico);
                    empComplemento.setLancamento(-1);
                    empComplementoDAO.insere(con, usuario, empComplemento);
                }
                stmt.close();
                rs.close();
            }

        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            System.out.println("FINALIZANDO IMPORTAÇÃO EMPENHOS: " + anoSonner);
            ConexaoDB.closeConection(con, stmt, rs);
        }
    }

    private static void insereDocPagto(Connection con, String usuario, Date anoAtual, int documento, int liquidacao, Date dataEmissao, String comprovanteTipo, String comprovanteNro, double valor) {

        if (!comprovanteTipo.equals("")) {

            int tipoDocPagto = (comprovanteTipo.equals("NF") ? 1 : 999);

            DocPagtoDAO docPagtoDAO = new DocPagtoDAO();
            DocPagto docPagto = new DocPagto();
            docPagto.setAno(anoAtual);
            docPagto.setTipoDoc("E");
            docPagto.setDocumento(documento);
            docPagto.setParcela(liquidacao);
            docPagto.setItem(1);
            docPagto.setTipoDocPagto(tipoDocPagto);
            docPagto.setDataEmissao(dataEmissao);
            docPagto.setDescricaoAdicional("Comprovante de Pagamento");
            docPagto.setDocumentoPagto(comprovanteNro);
            docPagto.setValor(valor);
            docPagtoDAO.delete(con, usuario, anoAtual, "E", documento, liquidacao);
            docPagtoDAO.insere(con, usuario, docPagto);
        }
    }
}
