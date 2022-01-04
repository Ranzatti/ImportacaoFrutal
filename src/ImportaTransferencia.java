import bean.*;
import dao.*;
import db.ConexaoDB;

import java.sql.*;

public class ImportaTransferencia {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2, stmt3;
        ResultSet rs = null;
        ResultSet rs2, rs3;

        int voucher, fichaConta, transf, versaoRecurso, fonteRecurso, caFixo;
        Date data;
        String bancoDocumento, fonte;
        double valor;

        String usuario = "FRUTAL";
        Date anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        Frutal frutalDAO = new Frutal();
        DebitoDAO debitoDAO = new DebitoDAO();
        DepositoDAO depositoDAO = new DepositoDAO();
        ContasBancariaDAO contaBancoDAO = new ContasBancariaDAO();
        TransferenciaDAO transferenciaDAO = new TransferenciaDAO();

        debitoDAO.deleteTransferencia(con, usuario, anoAtual);
        depositoDAO.deleteTransferencia(con, usuario, anoAtual);
        transferenciaDAO.delete(con, usuario, anoAtual);

        String teste = "";
        //teste = " and VOUCHER = 934 ";

        try {
            System.out.println("INICIANDO IMPORTAÇÂOO TRANSFERÊNCIA BANCÁRIA: " + anoSonner);
            stmt = con.prepareStatement(
                    "Select distinct VOUCHER, DATA "
                            + " From " + usuario + ".MOV_FIN_" + anoSonner
                            + " Where TIPO_VOUCHER = 3 " + teste
                            + " Order by 1 ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                voucher = rs.getInt(1);
                data = rs.getDate(2);

                //System.out.println("Voucher: " + voucher);

                transf = transferenciaDAO.getMax(con, usuario, anoAtual);
                Transferencia transferencia = new Transferencia();
                transferencia.setAno(anoAtual);
                transferencia.setSequencial(transf);
                transferenciaDAO.insere(con, usuario, transferencia);

                stmt2 = con.prepareStatement(
                        "Select BANCO_CONTA, BANCO_DOCUMENTO, FONTE, VALOR "
                                + " From " + usuario + ".MOV_FIN_" + anoSonner
                                + " Where VOUCHER = ? "
                                + " Order by 1 ");
                stmt2.setInt(1, voucher);
                rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    fichaConta = rs2.getInt(1);
                    bancoDocumento = rs2.getString(2).trim();
                    fonte = rs2.getString(3).trim();
                    valor = rs2.getDouble(4);

                    // Fonte Recurso
                    FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                    versaoRecurso = frutal.getVersaoRecurso();
                    fonteRecurso = frutal.getFonteRecurso();
                    caFixo = frutal.getCAFixo();

                    if (anoSonner >= 2016) {
                        // Pegando a fonte de recurso do empenho, pois so existe essa coluna a partir de 2015
                        stmt3 = con.prepareStatement(
                                "Select DEST_TCE "
                                        + " From " + usuario + ".MOV_FIN_" + anoSonner
                                        + " Where VOUCHER = ? "
                                        + " And BANCO_CONTA = ? "
                                        + " And TRIM(BANCO_DOCUMENTO) = ? "
                                        + " And TRIM(DEST_TCE) is not null ");
                        stmt3.setInt(1, voucher);
                        stmt3.setInt(2, fichaConta);
                        stmt3.setString(3, bancoDocumento);
                        rs3 = stmt3.executeQuery();
                        if (rs3.next()) {
                            fonteRecurso = Integer.parseInt(rs3.getString(1));
                        }
                        stmt3.close();
                        rs3.close();
                    }

                    // Banco
                    ContasBancarias contaBanco = contaBancoDAO.seleciona(con, usuario, fichaConta);

                    if (bancoDocumento.equals("AD") || bancoDocumento.equals("AP")) {
                        Debito debito = new Debito();
                        debito.setFichaConta(fichaConta);
                        debito.setBanco(contaBanco.getBanco());
                        debito.setAgencia(contaBanco.getAgencia());
                        debito.setConta(contaBanco.getCodigo());
                        debito.setData(data);
                        debito.setHistorico("Transferência Bancária - Voucher: " + voucher);
                        debito.setAnoLancto(anoAtual);
                        debito.setLancamento(-1);
                        debito.setTransferencia(transf);
                        debito.setFinalidade("T");
                        debito.setValor(valor);
                        debito.setAutPagto(0);
                        debito.setTipoAnulacaoRec("");
                        debito.setAnulacaoReceita(0);
                        debito.setVersaoRecurso(versaoRecurso);
                        debito.setFonteRecurso(fonteRecurso);
                        debito.setCaFixo(caFixo);
                        debito.setCaVariavel(0);
                        debitoDAO.insere(con, usuario, debito);
                    }

                    if (bancoDocumento.equals("AC") || bancoDocumento.equals("RS")) {
                        Deposito deposito = new Deposito();
                        deposito.setFichaConta(fichaConta);
                        deposito.setBanco(contaBanco.getBanco());
                        deposito.setAgencia(contaBanco.getAgencia());
                        deposito.setConta(contaBanco.getCodigo());
                        deposito.setData(data);
                        deposito.setHistorico("Transferência Bancária - Voucher: " + voucher);
                        deposito.setAnoLancto(anoAtual);
                        deposito.setLancamento(-1);
                        deposito.setTransferencia(transf);
                        deposito.setOrigem("T");
                        deposito.setValor(valor);
                        deposito.setVersaoRecurso(versaoRecurso);
                        deposito.setFonteRecurso(fonteRecurso);
                        deposito.setCaFixo(caFixo);
                        deposito.setCaVariavel(0);
                        depositoDAO.insere(con, usuario, deposito);
                    }
                }
                stmt2.close();
                rs2.close();
            }
            System.out.println("FINALIZANDO IMPORTAÇÂO TRANSFERÊNCIA BANCÁRIA: " + anoSonner);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }
    }
}
