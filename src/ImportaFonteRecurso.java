import bean.FonteRecurso;
import dao.FonteRecursoDAO;
import dao.Frutal;
import db.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportaFonteRecurso {

    public static void main(String[] args) {
        init(2020);
    }

    public static void init(int anoSonner) {

        Connection con = ConexaoDB.getConexaoDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String usuario = "FRUTAL";
        int versaoRecurso;

        FonteRecursoDAO fonteRecursoDAO = new FonteRecursoDAO();
        Frutal frutalDAO = new Frutal();

        System.out.println("INICIANDO IMPORTAÇÂO FONTE RECURSO");
        try {
            versaoRecurso = frutalDAO.versaoRecurso(con, usuario, anoSonner);

            fonteRecursoDAO.delete(con, usuario, versaoRecurso);

            stmt = con.prepareStatement(
                    "Select CODIGO, DESC_PRIM_NPRIM "
                            + " From " + usuario + ".DEST_TCE_" + anoSonner
                            + " Order by 1 ");
            rs = stmt.executeQuery();
            while (rs.next()) {
                FonteRecurso fonteRecurso = new FonteRecurso();
                fonteRecurso.setVersao(versaoRecurso);
                fonteRecurso.setCodigo(Integer.parseInt(rs.getString(1).trim()));
                fonteRecurso.setDescricao(rs.getString(2).trim().toUpperCase());
                fonteRecursoDAO.insere(con, usuario, fonteRecurso);
            }
            stmt.close();
            rs.close();
            System.out.println("FINALIZANDO IMPORTAÇÂO FONTE RECURSO");
        } catch (SQLException e) {
            System.out.println("Ops");
            e.printStackTrace();
        } finally {
            ConexaoDB.closeConection(con, stmt, rs);
        }

    }

}
