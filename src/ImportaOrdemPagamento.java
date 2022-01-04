import bean.FonteRecursoFrutal;
import bean.OPFonteRecurso;
import bean.OrdensPagto;
import bean.PagtoOP;
import dao.*;
import db.ConexaoDB;

import java.sql.*;

public class ImportaOrdemPagamento {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        ResultSet rs = null;
        ResultSet rs2;

        String usuario = "FRUTAL";
        Date anoAtual = java.sql.Date.valueOf(anoSonner + "-01-01");

        int nroOP, versaoRecurso, fonteRecurso, caFixo;
        double valorOP;
        String codigo, fonte, historico;

        Frutal frutalDAO = new Frutal();
        OrdensPagtoDAO opDAO = new OrdensPagtoDAO();
        PagtoOPDAO pagtoOPDAO = new PagtoOPDAO();
        OPFonteRecursoDAO opFonteDAO = new OPFonteRecursoDAO();
        DescontosTempDAO descontosTempDAO = new DescontosTempDAO();

        opDAO.delete(con, usuario, anoAtual);
        opFonteDAO.delete(con, usuario, anoAtual);
        pagtoOPDAO.delete(con, usuario, anoAtual);
        descontosTempDAO.delete(con, usuario, anoAtual, "O");

        String teste = "";
        //teste = " and CODIGO = 'OP00066' ";

        try {
            System.out.println("INICIANDO IMPORTA��O ORDEM PAGAMENTO: " + anoSonner);
            stmt = con.prepareStatement(
                    "Select CODIGO, NUMERO, DATA, FAVORECIDO, CONTAFIN, VALOR, OUTROS_DESC, VENCIMENTO, FONTE "
                            + " From " + usuario + ".EXTRAORC_" + anoSonner
                            + " Where TIPO = 'OP' " + teste
                            + " Order by	1 ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                codigo = rs.getString(1).trim();
                nroOP = Integer.parseInt(rs.getString(2).trim());
                valorOP = rs.getDouble(6);
                fonte = rs.getString(9).trim();

                historico = frutalDAO.historico(con, usuario, anoSonner, codigo);

                OrdensPagto op = new OrdensPagto();
                op.setAno(anoAtual);
                op.setNumero(nroOP);
                op.setDataOP(rs.getDate(3));
                op.setFornecedor(rs.getInt(4));
                op.setFicha(rs.getInt(5));
                op.setValor(valorOP);
                op.setDesconto(rs.getDouble(7));
                op.setVencimento(rs.getDate(8));
                op.setHistorico(historico);
                op.setAnoLancto(anoAtual);
                op.setLancamento(-1);
                opDAO.insere(con, usuario, op);

                PagtoOP pagtoOP = new PagtoOP();
                pagtoOP.setAno(anoAtual);
                pagtoOP.setOp(nroOP);
                pagtoOP.setValorPagto(valorOP);
                pagtoOPDAO.insere(con, usuario, pagtoOP);

                // Fonte Recurso
                FonteRecursoFrutal frutal = frutalDAO.fonteRecurso(con, usuario, anoSonner, fonte);
                versaoRecurso = frutal.getVersaoRecurso();
                fonteRecurso = frutal.getFonteRecurso();
                caFixo = frutal.getCAFixo();

                if (anoSonner >= 2015) {
                    // Pegando a fonte de recurso do empenho, pois so existe essa coluna a partir de 2015
                    stmt2 = con.prepareStatement(
                            "Select DEST_TCE "
                                    + " From " + usuario + ".EXTRAORC_" + anoSonner
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

                OPFonteRecurso opFonte = new OPFonteRecurso();
                opFonte.setAno(anoAtual);
                opFonte.setOp(nroOP);
                opFonte.setVersaoRecurso(versaoRecurso);
                opFonte.setFonteRecurso(fonteRecurso);
                opFonte.setCaFixo(caFixo);
                opFonte.setCaVariavel(0);
                opFonte.setValor(valorOP);
                opFonteDAO.insere(con, usuario, opFonte);

                frutalDAO.descontosTemp(con, usuario, anoSonner, codigo, "O", nroOP, 1);
            }

            System.out.println("FINALIZANDO IMPORTAÇÂO ORDEM PAGAMENTO: " + anoSonner);
        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }
    }

}
