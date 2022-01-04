import bean.*;
import dao.*;
import db.ConexaoDB;

import java.sql.*;

public class ImportaAnulacaoReceita {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        ResultSet rs = null;
        ResultSet rs2;

        int fichaReceita, fichaBanco, dedutora, anulacao, versaoRecurso, fonteRecurso, caFixo;
        Date dataGuia;
        String fonte, tipoReceita;
        double valorGuia;

        String usuario = "FRUTAL";
        Date anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        Frutal frutalDAO = new Frutal();
        AnulaReceitaDAO anulaReceitaDAO = new AnulaReceitaDAO();
        AnulaRecItensDAO anulaRecItensDAO = new AnulaRecItensDAO();
        AnulaRecContaBancoDAO anulaRecContaBancoDAO = new AnulaRecContaBancoDAO();
        DebitoDAO debitoDAO = new DebitoDAO();
        CreditoDAO creditoDAO = new CreditoDAO();
        SaidasCaixaDAO saidaCaixaDAO = new SaidasCaixaDAO();
        EntradasCaixaDAO entradasCaixaDAO = new EntradasCaixaDAO();

        anulaReceitaDAO.delete(con, usuario, anoAtual);
        anulaRecItensDAO.delete(con, usuario, anoAtual);
        anulaRecContaBancoDAO.delete(con, usuario, anoAtual);
        debitoDAO.deleteAnulacaoReceita(con, usuario, anoAtual);
        creditoDAO.deleteAnuacaoReceita(con, usuario, anoAtual);
        entradasCaixaDAO.deleteAnulacaoReceita(con, usuario, anoAtual);
        saidaCaixaDAO.deleteAnulacaoReceita(con, usuario, anoAtual);

        String teste = "";
        // teste = " and VOUCHER = 2078 ";

        System.out.println("INICIANDO IMPORTAÇÃO ANULAÇÃO DE RECEITA: " + anoSonner);
        try {
            System.out.println("INICIANDO ORÇAMENTÁRIAS");

            tipoReceita = "O";
            stmt = con.prepareStatement(
                    "Select DISTINCT VOUCHER, DATA, FICHA, FONTE, BANCO_CONTA, SUM(VALOR) "
                            + " From " + usuario + ".MOV_FIN_" + anoSonner + " A "
                            + " Where TIPO_VOUCHER = 1 "
                            + " And DOC_ORC_TIPO = 'RO' "
                            + " And exists ( Select * From " + usuario + ".ORCTO_" + anoSonner + " X Where A.FICHA = X.FICHA And TRIM(X.TEM_CONTA_RETIF) is not null ) " + teste
                            + " GROUP BY VOUCHER, DATA, FICHA, FONTE, BANCO_CONTA "
                            + " Having SUM(VALOR) > 0 "
                            + " Order by VOUCHER ");
            rs = stmt.executeQuery();
            while (rs.next()) {

                anulacao = rs.getInt(1);
                dataGuia = rs.getDate(2);
                fichaReceita = rs.getInt(3);
                fonte = rs.getString(4).trim();
                fichaBanco = rs.getInt(5);
                valorGuia = rs.getDouble(6);

                // Guia de Receitas
                AnulaReceita anulaReceita = anulaReceitaDAO.seleciona(con, usuario, anoAtual, tipoReceita, anulacao);
                if (anulaReceita.getAno() == null) {
                    anulaReceita = new AnulaReceita();
                    anulaReceita.setAno(anoAtual);
                    anulaReceita.setTipo(tipoReceita);
                    anulaReceita.setAnulacao(anulacao);
                    anulaReceita.setDataAnulacao(dataGuia);
                    anulaReceita.setLancamento(-1);
                    anulaReceita.setHistorico("Anulação de Receita Orçamentária Dedutora - " + anulacao);
                    anulaReceita.setDedutora(1);
                    anulaReceitaDAO.insere(con, usuario, anulaReceita);
                }

                // Fonte Recurso
                FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                versaoRecurso = frutal.getVersaoRecurso();
                fonteRecurso = frutal.getFonteRecurso();
                caFixo = frutal.getCAFixo();

                // Itens da Anulacao
                itensAnulacao(con, usuario, anoAtual, anulacao, tipoReceita, fichaReceita, versaoRecurso, fonteRecurso, caFixo, valorGuia, anulaRecItensDAO);

                if (fichaBanco == 0) {
                    entradasCaixa(con, usuario, anoAtual, anulacao, dataGuia, tipoReceita, versaoRecurso, fonteRecurso, valorGuia, entradasCaixaDAO);
                } else {
                    credito(con, usuario, anoAtual, fichaBanco, anulacao, dataGuia, versaoRecurso, fonteRecurso, caFixo, valorGuia, creditoDAO);
                }

                // Rec Conta banco
                anulaRecContaBanco(con, usuario, anoAtual, tipoReceita, fichaReceita, fichaBanco, anulacao, versaoRecurso, fonteRecurso, caFixo, valorGuia, anulaRecContaBancoDAO);

            }
            stmt.close();
            rs.close();
            // FINAL DA ANULAÇÕESES ORÇAMENTARIA

            // INICIO ANULAÇ~ESES ORÇAMENTARIAS
            tipoReceita = "O";
            stmt = con.prepareStatement(
                    "Select DISTINCT VOUCHER, DATA, FICHA, FONTE, BANCO_CONTA, SUM(VALOR) "
                            + " From " + usuario + ".MOV_FIN_" + anoSonner
                            + " Where TIPO_VOUCHER = 1 "
                            + " And DOC_ORC_TIPO = 'RO' "
                            + " GROUP BY VOUCHER, DATA, FICHA, FONTE, BANCO_CONTA "
                            + " Having SUM(VALOR) < 0 "
                            + " Order by VOUCHER ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                anulacao = rs.getInt(1);
                dataGuia = rs.getDate(2);
                fichaReceita = rs.getInt(3);
                fonte = rs.getString(4).trim();
                fichaBanco = rs.getInt(5);
                valorGuia = Math.abs(rs.getDouble(6));

                // Anulacao Receita
                AnulaReceita anulaReceita = anulaReceitaDAO.seleciona(con, usuario, anoAtual, tipoReceita, anulacao);
                if (anulaReceita.getAno() == null) {
                    dedutora = 0;
                    stmt2 = con.prepareStatement(
                            "Select * "
                                    + " From " + usuario + ".ORCTO_" + anoSonner
                                    + " Where FICHA = ? "
                                    + " And TRIM(TEM_CONTA_RETIF) is not null ");
                    stmt2.setInt(1, fichaReceita);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        dedutora = 2;
                    }
                    stmt2.close();
                    rs2.close();

                    anulaReceita.setAno(anoAtual);
                    anulaReceita.setTipo(tipoReceita);
                    anulaReceita.setAnulacao(anulacao);
                    anulaReceita.setDataAnulacao(dataGuia);
                    anulaReceita.setHistorico("Anulação de Receita Orçamentária - " + anulacao);
                    anulaReceita.setLancamento(-1);
                    anulaReceita.setDedutora(dedutora);
                    anulaReceitaDAO.insere(con, usuario, anulaReceita);
                }

                // Fonte Recurso
                FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                versaoRecurso = frutal.getVersaoRecurso();
                fonteRecurso = frutal.getFonteRecurso();
                caFixo = frutal.getCAFixo();

                // Itens da Anulacao
                itensAnulacao(con, usuario, anoAtual, anulacao, tipoReceita, fichaReceita, versaoRecurso, fonteRecurso, caFixo, valorGuia, anulaRecItensDAO);

                if (fichaBanco == 0) {
                    saidaCaixa(con, usuario, anoAtual, anulacao, dataGuia, tipoReceita, versaoRecurso, fonteRecurso, valorGuia, saidaCaixaDAO);
                } else {
                    debito(con, usuario, anoAtual, fichaBanco, anulacao, dataGuia, tipoReceita, versaoRecurso, fonteRecurso, caFixo, valorGuia, debitoDAO);
                }

                // Rec Conta banco
                anulaRecContaBanco(con, usuario, anoAtual, tipoReceita, fichaReceita, fichaBanco, anulacao, versaoRecurso, fonteRecurso, caFixo, valorGuia, anulaRecContaBancoDAO);

            }
            stmt.close();
            rs.close();

            // FINAL DA ANULAÇÕES ORÇAMENTARIA

            System.out.println("INICIANDO EXTRA-ORÇAMENTÁRIAS");
            // INICIO ANULAÇOES EXTRA-ORÇAMENTARIAS
            tipoReceita = "E";
            stmt = con.prepareStatement(
                    "Select DISTINCT VOUCHER, DATA, CREDITE, FONTE, BANCO_CONTA, SUM(VALOR) "
                            + " From " + usuario + ".MOV_FIN_" + anoSonner
                            + " Where TIPO_VOUCHER = 1 "
                            + " And DOC_ORC_TIPO = 'RE' " + teste
                            + " GROUP BY VOUCHER, DATA, CREDITE, FONTE, BANCO_CONTA "
                            + " Having SUM(VALOR) < 0 "
                            + " Order by VOUCHER ");
            rs = stmt.executeQuery();
            while (rs.next()) {

                anulacao = rs.getInt(1);
                dataGuia = rs.getDate(2);
                fichaReceita = rs.getInt(3);
                fonte = rs.getString(4).trim();
                fichaBanco = rs.getInt(5);
                valorGuia = Math.abs(rs.getDouble(6));

                // Anula��o de Receita
                AnulaReceita anulaReceita = anulaReceitaDAO.seleciona(con, usuario, anoAtual, tipoReceita, anulacao);
                if (anulaReceita.getAno() == null) {
                    anulaReceita.setAno(anoAtual);
                    anulaReceita.setTipo(tipoReceita);
                    anulaReceita.setAnulacao(anulacao);
                    anulaReceita.setDataAnulacao(dataGuia);
                    anulaReceita.setHistorico("Anulação de Receita Extra-Orçamentária - " + anulacao);
                    anulaReceita.setLancamento(-1);
                    anulaReceita.setDedutora(0);
                    anulaReceitaDAO.insere(con, usuario, anulaReceita);
                }

                // Fonte Recurso
                FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                versaoRecurso = frutal.getVersaoRecurso();
                fonteRecurso = frutal.getFonteRecurso();
                caFixo = frutal.getCAFixo();

                // Itens da Anulacao
                itensAnulacao(con, usuario, anoAtual, anulacao, tipoReceita, fichaReceita, versaoRecurso, fonteRecurso, caFixo, valorGuia, anulaRecItensDAO);

                if (fichaBanco == 0) {
                    saidaCaixa(con, usuario, anoAtual, anulacao, dataGuia, tipoReceita, versaoRecurso, fonteRecurso, valorGuia, saidaCaixaDAO);
                } else {
                    debito(con, usuario, anoAtual, fichaBanco, anulacao, dataGuia, tipoReceita, versaoRecurso, fonteRecurso, caFixo, valorGuia, debitoDAO);
                }

                // Rec Conta banco
                anulaRecContaBanco(con, usuario, anoAtual, tipoReceita, fichaReceita, fichaBanco, anulacao, versaoRecurso, fonteRecurso, caFixo, valorGuia, anulaRecContaBancoDAO);

            }
            stmt.close();
            rs.close();
            // FINAL DA ANULAÇOESES EXTRA-ORÇAMENTARIA

            System.out.println("FINALIZANDO IMPORTAÇÂO ANULAÇÂO DE RECEITA: " + anoSonner);
        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }
    }

    private static void entradasCaixa(Connection con, String usuario, Date anoAtual, int anulacao, Date dataGuia, String tipoReceita, int versaoRecurso, int fonteRecurso, double valorGuia, EntradasCaixaDAO entradasCaixaDAO) {
        EntradasCaixa entradasCaixa = new EntradasCaixa();
        entradasCaixa.setData(dataGuia);
        entradasCaixa.setHistorico("Anulação de Receita Orçamentária Dedutora - " + anulacao);
        entradasCaixa.setAnoLancto(anoAtual);
        entradasCaixa.setLancamento(-1);
        entradasCaixa.setTipoGuia(tipoReceita);
        entradasCaixa.setGuia(0);
        entradasCaixa.setTransferencia(0);
        entradasCaixa.setEmpenho(0);
        entradasCaixa.setAnulacao(0);
        entradasCaixa.setAnulRecDedutora(anulacao);
        entradasCaixa.setParcela(0);
        entradasCaixa.setVersaoRecurso(versaoRecurso);
        entradasCaixa.setFonteRecurso(fonteRecurso);
        entradasCaixa.setRegulaFUNDEB(0);
        entradasCaixa.setValor(valorGuia);
        entradasCaixaDAO.insere(con, usuario, entradasCaixa);
    }

    private static void credito(Connection con, String usuario, Date anoAtual, int fichaBanco, int anulacao, Date dataGuia, int versaoRecurso, int fonteRecurso, int caFixo, double valorGuia, CreditoDAO creditoDAO) {

        ContasBancariaDAO contaBancoDAO = new ContasBancariaDAO();
        ContasBancarias contaBanco = contaBancoDAO.seleciona(con, usuario, fichaBanco);

        Credito credito = new Credito();
        credito.setFichaConta(fichaBanco);
        credito.setBanco(contaBanco.getBanco());
        credito.setAgencia(contaBanco.getAgencia());
        credito.setConta(contaBanco.getCodigo());
        credito.setData(dataGuia);
        credito.setHistorico("Anulação de Receita Orçamentária Dedutora - " + anulacao);
        credito.setAnoLancto(anoAtual);
        credito.setLancamento(-1);
        credito.setAnulRecDedutora(anulacao);
        credito.setValor(valorGuia);
        credito.setVersaoRecurso(versaoRecurso);
        credito.setFonteRecurso(fonteRecurso);
        credito.setCaFixo(caFixo);
        credito.setCaVariavel(0);
        creditoDAO.insere(con, usuario, credito);
    }

    private static void saidaCaixa(Connection con, String usuario, Date anoAtual, int anulacao, Date dataGuia, String tipoReceita, int versaoRecurso, int fonteRecurso, double valorGuia, SaidasCaixaDAO saidaCaixaDAO) {

        SaidasCaixa saidaCaixa = new SaidasCaixa();
        saidaCaixa.setData(dataGuia);
        saidaCaixa.setHistorico("Anulação de Receita Orçamentária - " + anulacao);
        saidaCaixa.setAnoLancto(anoAtual);
        saidaCaixa.setLancamento(-1);
        saidaCaixa.setTipoAnulacaoRec(tipoReceita);
        saidaCaixa.setAnulacaoReceita(anulacao);
        saidaCaixa.setTransferencia(0);
        saidaCaixa.setAutPagto(0);
        saidaCaixa.setVersaoRecurso(versaoRecurso);
        saidaCaixa.setFonteRecurso(fonteRecurso);
        saidaCaixa.setValor(valorGuia);
        saidaCaixaDAO.insere(con, usuario, saidaCaixa);
    }

    private static void debito(Connection con, String usuario, Date anoAtual, int fichaBanco, int anulacao, Date dataGuia, String tipoReceita, int versaoRecurso, int fonteRecurso, int caFixo, double valorGuia, DebitoDAO debitoDAO) {

        ContasBancariaDAO contaBancoDAO = new ContasBancariaDAO();
        ContasBancarias contaBanco = contaBancoDAO.seleciona(con, usuario, fichaBanco);

        Debito debito = new Debito();
        debito.setFichaConta(fichaBanco);
        debito.setBanco(contaBanco.getBanco());
        debito.setAgencia(contaBanco.getAgencia());
        debito.setConta(contaBanco.getCodigo());
        debito.setData(dataGuia);
        debito.setHistorico("Anulação de Receita Orçamentária - " + anulacao);
        debito.setAnoLancto(anoAtual);
        debito.setLancamento(-1);
        debito.setTipoAnulacaoRec(tipoReceita);
        debito.setAnulacaoReceita(anulacao);
        debito.setAutPagto(0);
        debito.setFinalidade("P");
        debito.setTransferencia(0);
        debito.setValor(valorGuia);
        debito.setVersaoRecurso(versaoRecurso);
        debito.setFonteRecurso(fonteRecurso);
        debito.setCaFixo(caFixo);
        debito.setCaVariavel(0);
        debitoDAO.insere(con, usuario, debito);
    }

    private static void anulaRecContaBanco(Connection con, String usuario, Date anoAtual, String tipoReceita, int fichaReceita, int fichaBanco, int anulacao, int versaoRecurso, int fonteRecurso, int caFixo, double valorGuia, AnulaRecContaBancoDAO anulaRecContaBancoDAO) {

        AnulaRecContaBanco anulaRecContaBanco = new AnulaRecContaBanco();
        anulaRecContaBanco.setAno(anoAtual);
        anulaRecContaBanco.setAnulacao(anulacao);
        anulaRecContaBanco.setTipo(tipoReceita);
        anulaRecContaBanco.setFichaReceita(fichaReceita);
        anulaRecContaBanco.setFichaBanco(fichaBanco);
        anulaRecContaBanco.setVersaoRecurso(versaoRecurso);
        anulaRecContaBanco.setFonteRecurso(fonteRecurso);
        anulaRecContaBanco.setCaFixo(caFixo);
        anulaRecContaBanco.setCaVariavel(0);
        anulaRecContaBanco.setVersaoRecursoBanco(versaoRecurso);
        anulaRecContaBanco.setFonteRecursoBanco(fonteRecurso);
        anulaRecContaBanco.setCaFixoBanco(caFixo);
        anulaRecContaBanco.setCaVariavelBanco(0);
        anulaRecContaBanco.setItem(1);
        anulaRecContaBanco.setValor(valorGuia);
        anulaRecContaBancoDAO.insere(con, usuario, anulaRecContaBanco);
    }

    private static void itensAnulacao(Connection con, String usuario, Date anoAtual, int anulacao, String tipoReceita, int fichaReceita, int versaoRecurso, int fonteRecurso, int caFixo, double valorGuia, AnulaRecItensDAO anulaRecItensDAO) {

        AnulaRecItens anulaRecItens = new AnulaRecItens();
        anulaRecItens.setAno(anoAtual);
        anulaRecItens.setAnulacao(anulacao);
        anulaRecItens.setTipo(tipoReceita);
        anulaRecItens.setFicha(fichaReceita);
        anulaRecItens.setVersaoRecurso(versaoRecurso);
        anulaRecItens.setFonteRecurso(fonteRecurso);
        anulaRecItens.setCaFixo(caFixo);
        anulaRecItens.setCaVariavel(0);
        anulaRecItens.setValor(valorGuia);
        anulaRecItensDAO.insere(con, usuario, anulaRecItens);
    }
}
