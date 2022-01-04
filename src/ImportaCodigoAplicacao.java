import bean.CodigoAplicacao;
import dao.CodigoAplicacaoDAO;
import db.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportaCodigoAplicacao {

    public static void main(String[] args) {
        init(2005);
    }

    public static void init(int anoSonner) {
        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        PreparedStatement stmt2;
        ResultSet rs = null;

        String usuario = "FRUTAL";

        CodigoAplicacaoDAO codAplicaDAO = new CodigoAplicacaoDAO();

        //codAplicaDAO.delete(con, usuario);

        System.out.println("INICIANDO IMPORTA��O CODIGO APLICACAO");
        try {

            int indice = 1;
            stmt = con.prepareStatement(
                    "Select NOME "
                            + " From " + usuario + ".CBPCODIGOAPLICACAO2 "
                            + " Order by NOME");
            rs = stmt.executeQuery();
            while (rs.next()) {
                stmt2 = con.prepareStatement(
                        "Update " + usuario + ".CBPCODIGOAPLICACAO2 "
                                + "Set CODAPLICACAO = ? "
                                + "Where NOME = ? ");
                stmt2.setInt(1, indice);
                stmt2.setString(2, rs.getString(1));
                stmt2.executeUpdate();
                stmt2.close();
                indice++;
            }
            stmt.close();
            rs.close();

//			stmt = con.prepareStatement("Select RECORD_NUMBER, CODIGO, DESCRICAO " + " From " + usuario + ".FONTE_" + anoSonner + " Order by 1");
//			rs = stmt.executeQuery();
//			while (rs.next()) {
//				codAplic = new CodigoAplicacao();
//				codAplic.setCodAplicacao(rs.getInt(1));
//				codAplic.setNome(rs.getString(2).trim().toUpperCase() + " - " + rs.getString(3).trim().toUpperCase());
//				codAplic.setFixo(1);
//				codAplic.setCodigoGeral(0);
//				codAplic.setCodigo(rs.getString(2).trim().toUpperCase());
//				codAplicaDAO.insere(con, usuario, codAplic);
//			}

            if (anoSonner == 2020) {
                CodigoAplicacao codAplic = new CodigoAplicacao();
                codAplic.setCodAplicacao(999);
                codAplic.setNome("GERAL");
                codAplic.setFixo(1);
                codAplic.setCodigoGeral(0);
                codAplicaDAO.insere(con, usuario, codAplic);
            }

            System.out.println("FINALIZANDO IMPORTA��O CODIGO APLICACAO");
        } catch (SQLException e) {
            System.out.println("OPS");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }
    }
}
