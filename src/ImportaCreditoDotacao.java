import bean.*;
import dao.*;
import db.ConexaoDB;

import java.sql.*;
import java.text.SimpleDateFormat;

public class ImportaCreditoDotacao {

    public static void main(String[] args) {
        init(2010);
    }

    public static void init(int anoSonner) {
        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        ResultSet rs = null;
        ResultSet rs2;
        SimpleDateFormat sdfMes = new SimpleDateFormat("MM");

        String teste, natureza, numero, tipo, lei;
        int mes, nroLei, codCredito, qtdeCredito, qtdeAnulado, ficha, fonteRecurso, versaoRecurso;
        Date anoAtual, data;
        double valor, valorTotalCredito;
        boolean isNumeric;

        String usuario = "FRUTAL";
        anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        Frutal frutalDAO = new Frutal();
        CreditoDotacaoDAO creditoDotDAO = new CreditoDotacaoDAO();
        ElemCreditadosDAO elemCreditadoDAO = new ElemCreditadosDAO();
        ElemCredMesDAO elemCredMesDAO = new ElemCredMesDAO();
        ElemAnuladosDAO elemAnuladosDAO = new ElemAnuladosDAO();
        ElemAnulMesDAO elemAnulMesDAO = new ElemAnulMesDAO();
        ElemCredDistribDAO elemCredDistDAO = new ElemCredDistribDAO();

        creditoDotDAO.delete(con, usuario, anoAtual);
        elemCreditadoDAO.delete(con, usuario, anoAtual);
        elemCredMesDAO.delete(con, usuario, anoAtual);
        elemAnuladosDAO.delete(con, usuario, anoAtual);
        elemAnulMesDAO.delete(con, usuario, anoAtual);
        elemCredDistDAO.delete(con, usuario, anoAtual);

        versaoRecurso = frutalDAO.versaoRecurso(con, usuario, anoSonner);

        teste = "";
        //teste = " AND NUMERO = '09694' ";

        System.out.println("INICIANDO IMPORTAÇÂO CREDITO DOTAÇÂO: " + anoSonner);
        try {
            stmt = con.prepareStatement(
                    "Select distinct NUMERO, TIPO, RECURSOS_ART43 , DATA, NUMERO_LEI "
                            + " From " + usuario + ".MOV_ORC_" + anoSonner
                            + " Where TIPO IN ( 'CE', 'CS', 'CX' ) "
                            + " And RECURSOS_ART43 = 'A' "
                            + " And DATA >= ?" + teste
                            + " Order by 1, 2, 3 ");
            stmt.setDate(1, anoAtual);
            rs = stmt.executeQuery();
            while (rs.next()) {
                numero = rs.getString(1).trim();
                tipo = rs.getString(2).trim();
                natureza = rs.getString(3).trim();
                data = rs.getDate(4);
                lei = rs.getString(5).trim();

                nroLei = (lei.equals("") ? 0 : Integer.parseInt(lei));

                isNumeric = numero.matches("[+-]?\\d*(\\.\\d+)?");
                if (isNumeric) {
                    codCredito = Integer.parseInt(numero);
                    mes = Integer.parseInt(sdfMes.format(data));

                    // ELEM CREDITADOS
                    valorTotalCredito = 0;
                    qtdeCredito = 0;
                    qtdeAnulado = 0;
                    if (anoSonner < 2015) {
                        stmt2 = con.prepareStatement(
                                "Select FICHA, SUM(VALOR) "
                                        + " From " + usuario + ".MOV_ORC_" + anoSonner
                                        + " Where TRIM(NUMERO) = ? "
                                        + " And TRIM(TIPO) = ? "
                                        + " And TRIM(RECURSOS_ART43) = ? "
                                        + " Group by FICHA"
                                        + " Order by 1 ");
                        stmt2.setString(1, numero);
                        stmt2.setString(2, tipo);
                        stmt2.setString(3, natureza);
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            ficha = rs2.getInt(1);
                            fonteRecurso = 100;
                            valor = Math.abs(rs2.getDouble(2));

                            elemCreditados(con, usuario, anoAtual, codCredito, ficha, versaoRecurso, fonteRecurso, mes, valor, elemCreditadoDAO, elemCredMesDAO);

                            valorTotalCredito = valorTotalCredito + valor;
                            qtdeCredito++;
                        }
                        stmt2.close();
                        rs2.close();

                        // ELEM ANULADOS
                        stmt2 = con.prepareStatement(
                                "Select FICHA, SUM(VALOR) "
                                        + " From " + usuario + ".MOV_ORC_" + anoSonner
                                        + " Where NUMERO = ? "
                                        + " And TIPO = 'A' "
                                        + " group by FICHA "
                                        + " Order by 1  ");
                        stmt2.setString(1, numero);
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            ficha = rs2.getInt(1);
                            fonteRecurso = 100;
                            valor = Math.abs(rs2.getDouble(2));

                            elemAnulados(con, usuario, anoAtual, codCredito, ficha, versaoRecurso, fonteRecurso, mes, valor, elemAnuladosDAO, elemAnulMesDAO);

                            qtdeAnulado++;
                        }
                        stmt2.close();
                        rs2.close();
                    } else {
                        // ELEM CREDITADOS
                        stmt2 = con.prepareStatement(
                                "Select FICHA, DEST_TCE, SUM(VALOR) "
                                        + " From " + usuario + ".MOV_ORC_" + anoSonner
                                        + " Where TRIM(NUMERO) = ? "
                                        + " And TRIM(TIPO) = ? "
                                        + " And TRIM(RECURSOS_ART43) = ? "
                                        + " Group by FICHA, DEST_TCE "
                                        + " Order by 1,2 ");
                        stmt2.setString(1, numero);
                        stmt2.setString(2, tipo);
                        stmt2.setString(3, natureza);
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            ficha = rs2.getInt(1);
                            fonteRecurso = rs2.getInt(2);
                            valor = Math.abs(rs2.getDouble(3));

                            elemCreditados(con, usuario, anoAtual, codCredito, ficha, versaoRecurso, fonteRecurso, mes, valor, elemCreditadoDAO, elemCredMesDAO);

                            valorTotalCredito = valorTotalCredito + valor;
                            qtdeCredito++;
                        }
                        stmt2.close();
                        rs2.close();

                        // ELEM ANULADOS
                        stmt2 = con.prepareStatement(
                                "Select FICHA, DEST_TCE, SUM(VALOR) "
                                        + " From " + usuario + ".MOV_ORC_" + anoSonner
                                        + " Where NUMERO = ? "
                                        + " And TIPO = 'A' "
                                        + " group by FICHA, DEST_TCE "
                                        + " Order by 1, 2 ");
                        stmt2.setString(1, numero);
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            ficha = rs2.getInt(1);
                            fonteRecurso = rs2.getInt(2);
                            valor = Math.abs(rs2.getDouble(3));

                            elemAnulados(con, usuario, anoAtual, codCredito, ficha, versaoRecurso, fonteRecurso, mes, valor, elemAnuladosDAO, elemAnulMesDAO);

                            qtdeAnulado++;
                        }
                        stmt2.close();
                        rs2.close();
                    }

                    // Elem Anulacao
                    if (qtdeCredito == 1 && qtdeAnulado >= 1) {

                        int fichaCred, fonteCred;

                        stmt2 = con.prepareStatement(
                                "Select FICHA, FONTERECURSO  " +
                                        " From " + usuario + ".CBPELEMCREDITADOS  " +
                                        " Where ANO = ?  " +
                                        " And CODIGO = ? ");
                        stmt2.setDate(1, anoAtual);
                        stmt2.setInt(2, codCredito);
                        rs2 = stmt2.executeQuery();
                        rs2.next();
                        fichaCred = rs2.getInt(1);
                        fonteCred = rs2.getInt(2);
                        stmt2.close();
                        rs2.close();

                        stmt2 = con.prepareStatement(
                                "Select FICHA, FONTERECURSO, VALOR "
                                        + " From " + usuario + ".CBPELEMANULADOS "
                                        + " Where ANO = ? "
                                        + " And CODIGO = ?");
                        stmt2.setDate(1, anoAtual);
                        stmt2.setInt(2, codCredito);
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {

                            ficha = rs2.getInt(1);
                            fonteRecurso = rs2.getInt(2);
                            valor = rs2.getDouble(3);

                            ElemCredDistrib elemCredDist = new ElemCredDistrib();
                            elemCredDist.setAno(anoAtual);
                            elemCredDist.setCodigo(codCredito);
                            elemCredDist.setFichaCredito(fichaCred);
                            elemCredDist.setVersaoRecCredito(versaoRecurso);
                            elemCredDist.setFonteRecursoCredito(fonteCred);
                            elemCredDist.setCaFixoCredito(999);
                            elemCredDist.setCaVariavelCredito(0);
                            elemCredDist.setFichaAnulacao(ficha);
                            elemCredDist.setVersaoRecAnulacao(versaoRecurso);
                            elemCredDist.setFonteRecursoAnulacao(fonteRecurso);
                            elemCredDist.setCaFixoAnulacao(999);
                            elemCredDist.setCaVariavelAnulacao(0);
                            elemCredDist.setValor(valor);
                            elemCredDistDAO.insere(con, usuario, elemCredDist);
                        }
                        stmt2.close();
                        rs2.close();
                    }

                    if (qtdeCredito > 1 && qtdeAnulado == 1) {

                        int fichaAnul, fonteAnul;

                        stmt2 = con.prepareStatement(
                                "Select FICHA, FONTERECURSO "
                                        + " From " + usuario + ".CBPELEMANULADOS "
                                        + " Where ANO = ? "
                                        + " And CODIGO = ? ");
                        stmt2.setDate(1, anoAtual);
                        stmt2.setInt(2, codCredito);
                        rs2 = stmt2.executeQuery();
                        rs2.next();
                        fichaAnul = rs2.getInt(1);
                        fonteAnul = rs2.getInt(2);
                        stmt2.close();
                        rs2.close();

                        stmt2 = con.prepareStatement(
                                "Select FICHA, FONTERECURSO, VALOR "
                                        + " From " + usuario + ".CBPELEMCREDITADOS "
                                        + " Where ANO = ? "
                                        + " And CODIGO = ? ");
                        stmt2.setDate(1, anoAtual);
                        stmt2.setInt(2, codCredito);
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {

                            ficha = rs2.getInt(1);
                            fonteRecurso = rs2.getInt(2);
                            valor = rs2.getDouble(3);

                            ElemCredDistrib elemCredDist = new ElemCredDistrib();
                            elemCredDist.setAno(anoAtual);
                            elemCredDist.setCodigo(codCredito);
                            elemCredDist.setFichaCredito(fichaAnul);
                            elemCredDist.setVersaoRecCredito(versaoRecurso);
                            elemCredDist.setFonteRecursoCredito(fonteAnul);
                            elemCredDist.setCaFixoCredito(999);
                            elemCredDist.setCaVariavelCredito(0);
                            elemCredDist.setFichaAnulacao(ficha);
                            elemCredDist.setVersaoRecAnulacao(versaoRecurso);
                            elemCredDist.setFonteRecursoAnulacao(fonteRecurso);
                            elemCredDist.setCaFixoAnulacao(999);
                            elemCredDist.setCaVariavelAnulacao(0);
                            elemCredDist.setValor(valor);
                            elemCredDistDAO.insere(con, usuario, elemCredDist);
                        }
                        stmt2.close();
                        rs2.close();
                    }

                    // CRDANL
                    stmt2 = con.prepareStatement(
                            "Select * From " + usuario + ".CBPCRDANL Where ANO = ? And CODIGO = ? ");
                    stmt2.setDate(1, anoAtual);
                    stmt2.setInt(2, codCredito);
                    rs2 = stmt2.executeQuery();
                    if (!rs2.next()) {

                        stmt2 = con.prepareStatement(
                                "Select CODIGO From " + usuario + ".MOV_ORC_" + anoSonner
                                        + " Where NUMERO = ? "
                                        + " And TIPO = ? "
                                        + " Order by 1 ");
                        stmt2.setString(1, numero);
                        stmt2.setString(2, tipo);
                        rs2 = stmt2.executeQuery();
                        rs2.next();

                        tipo = (tipo.equals("CS") ? "S" : tipo);
                        tipo = (tipo.equals("CX") ? "X" : tipo);
                        tipo = (tipo.equals("CE") ? "E" : tipo);

                        CreditoDotacao creditoDot = new CreditoDotacao();
                        creditoDot.setAno(anoAtual);
                        creditoDot.setCodigo(codCredito);
                        creditoDot.setDataCredito(data);
                        creditoDot.setTipo(tipo);
                        creditoDot.setHistorico(frutalDAO.historico(con, usuario, anoSonner, rs2.getString(1).trim()));
                        creditoDot.setNatureza(natureza);
                        creditoDot.setUsaOrcamento("X");
                        creditoDot.setLei(nroLei);
                        creditoDot.setTransposicao(0);
                        creditoDot.setTransposicaoFonte(0);
                        creditoDot.setLancamento(-1);
                        creditoDotDAO.insere(con, usuario, creditoDot);
                    }
                    stmt2.close();
                    rs2.close();
                }
            }
            System.out.println("FINALIZANDO IMPORTAÇÂO CREDITO DOTAÇÂO: " + anoSonner);
        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }
    }

    private static void elemAnulados(Connection con, String usuario, Date anoAtual, int codCredito, int ficha, int versaoRecurso, int fonteRecurso, int mes, double valor,
                                     ElemAnuladosDAO elemAnuladosDAO, ElemAnulMesDAO elemAnulMesDAO) {

        ElemAnulados elemAnul = new ElemAnulados();
        elemAnul.setAno(anoAtual);
        elemAnul.setCodigo(codCredito);
        elemAnul.setFicha(ficha);
        elemAnul.setVersaoRecurso(versaoRecurso);
        elemAnul.setFonteRecurso(fonteRecurso);
        elemAnul.setCaFixo(999);
        elemAnul.setCaVariavel(0);
        elemAnul.setValor(valor);
        elemAnuladosDAO.insere(con, usuario, elemAnul);


        ElemAnulMes elemAnulMes = new ElemAnulMes();
        elemAnulMes.setAno(anoAtual);
        elemAnulMes.setAnulado(codCredito);
        elemAnulMes.setFicha(ficha);
        elemAnulMes.setVersaoRecurso(versaoRecurso);
        elemAnulMes.setRecurso(fonteRecurso);
        elemAnulMes.setCaFixo(999);
        elemAnulMes.setCaVariavel(0);
        elemAnulMes.setMes(mes);
        elemAnulMes.setValor(valor);
        elemAnulMesDAO.insere(con, usuario, elemAnulMes);
    }

    private static void elemCreditados(Connection con, String usuario, Date anoAtual, int codCredito, int ficha, int versaoRecurso, int fonteRecurso, int mes, double valor,
                                       ElemCreditadosDAO elemCreditadosDAO, ElemCredMesDAO elemCredMesDAO) {

        ElemCreditados elemCred = new ElemCreditados();
        elemCred.setAno(anoAtual);
        elemCred.setCodigo(codCredito);
        elemCred.setFicha(ficha);
        elemCred.setVersaoRecurso(versaoRecurso);
        elemCred.setFonteRecurso(fonteRecurso);
        elemCred.setCaFixo(999);
        elemCred.setCaVariavel(0);
        elemCred.setValor(valor);
        elemCreditadosDAO.insere(con, usuario, elemCred);

        ElemCredMes elemCredMes = new ElemCredMes();
        elemCredMes.setAno(anoAtual);
        elemCredMes.setCredito(codCredito);
        elemCredMes.setFicha(ficha);
        elemCredMes.setVersaoRecurso(versaoRecurso);
        elemCredMes.setRecurso(fonteRecurso);
        elemCredMes.setCaFixo(999);
        elemCredMes.setCaVariavel(0);
        elemCredMes.setMes(mes);
        elemCredMes.setValor(valor);
        elemCredMesDAO.insere(con, usuario, elemCredMes);
    }
}
