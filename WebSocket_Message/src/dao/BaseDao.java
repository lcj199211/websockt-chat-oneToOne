package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConn() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		try {
			conn = DriverManager.getConnection(url, "scott", "scott");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
