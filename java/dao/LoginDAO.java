package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Login;

public class LoginDAO {
	private final String JDBC_URL="jdbc:postgresql://localhost:5432/todotask";
	private final String DB_USER="postgres";
	private final String DB_PASS="sql";

	public Login findByLogin(Login login) {
		Login loginUser = null;
		//JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			//select文を準備
			String sql = "SELECT user_id, user_pass from login where user_id = ? and user_pass = ?";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login.getUser_id());
			pStmt.setString(2, login.getUser_pass());
			
			//select文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();
			
			if(rs.next()) {
				//ユーザーが存在したらデータを取得し、そのユーザーを表すAccountインスタンスを生成
				String user_Id = rs.getString("user_id");
				String user_pass = rs.getString("user_pass");
				loginUser = new Login(user_Id, user_pass);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return loginUser;
	}
}
