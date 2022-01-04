import bean.ContaExtra;
import bean.ContaExtraFonteRec;
import dao.ContaExtraDAO;
import dao.ContaExtraFonteRecDAO;
import db.ConexaoDB;

import java.sql.*;

public class ImportaContaExtra {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Date anoAtual, anoRestos;
        String nome, tipoSaldo;
        int codigo;
        double saldo;

        String usuario = "FRUTAL";
        anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        ContaExtraDAO contaExtraDAO = new ContaExtraDAO();
        ContaExtraFonteRecDAO contaExtraFRDAO = new ContaExtraFonteRecDAO();

        contaExtraDAO.delete(con, usuario, anoAtual);
        contaExtraFRDAO.delete(con, usuario, anoAtual);

        try {
            System.out.println("INICIANDO IMPORTAÇÃOO CONTA EXTRA");
            stmt = con.prepareStatement(
                    "Select COD_REDUZIDO, NOME, ABERTURA "
                            + " From " + usuario + ".CONTAFIN_" + anoSonner
                            + " Where TRIM(SISTEMA) = 'E' "
                            + " Order by 1 ");
            rs = stmt.executeQuery();
            while (rs.next()) {

                codigo = rs.getInt(1);
                nome = rs.getString(2).trim().toUpperCase();
                saldo = rs.getDouble(3);

                anoRestos = null;
                if (nome.contains("RESTOS A PAGAR DE")) {
                    anoRestos = java.sql.Date.valueOf(nome.substring(nome.length() - 4) + "-01-01");
                }

                ContaExtra contaExtra = new ContaExtra();
                contaExtra.setAno(anoAtual);
                contaExtra.setContaExtra(codigo);
                contaExtra.setNome(nome);
                contaExtra.setAnoRestos(anoRestos);
                contaExtra.setEmpresa(1);
                contaExtraDAO.insere(con, usuario, contaExtra);

                tipoSaldo = (saldo >= 0 ? "C" : "D");
                saldo = Math.abs(saldo);

                ContaExtraFonteRec contaExtraFR = new ContaExtraFonteRec();
                contaExtraFR.setAno(anoAtual);
                contaExtraFR.setContaExtra(codigo);
                contaExtraFR.setVersaoRecurso(1);
                contaExtraFR.setFonteRecurso(100);
                contaExtraFR.setTipoSaldo(tipoSaldo);
                contaExtraFR.setSaldo(saldo);
                contaExtraFRDAO.insere(con, usuario, contaExtraFR);
            }
            stmt.close();
            rs.close();
            System.out.println("FINALIZANDO IMPORTAÇÃO CONTA EXTRA");
        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }

    }

}
