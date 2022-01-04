package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexaoDB {

	private static final String URL = "jdbc:oracle:thin:@localhost:1521:LOCAL";
	private static final String USER = "system";
	private static final String PASS = "sicsadm";

	public static Connection getConexaoDB() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception ex) {
			System.err.println("Erro de conex�o no banxo" + ex);
			return null;
		}
	}

	public static void closeConection(Connection con) {

		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				System.err.println("Erro ao fechar conex�o" + ex);
			}
		}

	}

	public static void closeConection(Connection con, PreparedStatement stmt) {

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				System.err.println("Erro ao fechar conex�o" + ex);
			}
		}
		closeConection(con);

	}

	public static void closeConection(Connection con, PreparedStatement stmt, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				System.err.println("Erro ao fechar conex�o" + ex);
			}
		}
		closeConection(con, stmt);

	}

}
