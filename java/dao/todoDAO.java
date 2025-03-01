package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.ToDo;

public class todoDAO {
	private final String JDBC_URL="jdbc:postgresql://localhost:5432/todotask";
	private final String DB_USER="postgres";
	private final String DB_PASS="sql";
	
	public List<ToDo> findAll(){
		List<ToDo> todoList = new ArrayList<>();
		//
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベース接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			//SELECT文の準備
			String sql = "SELECT task_id, task_name, task_contents, task_limitdate, task_update, task_user, task_status FROM todolist ORDER BY task_id DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//SELECT文を実行
			ResultSet rs = pStmt.executeQuery();
			
			//SELECT文の結果をArrayListに格納
			while(rs.next()) {
				int id = rs.getInt("task_id");
				String title = rs.getString("task_name");
				String contents = rs.getString("task_contents");
				Date limitdate = rs.getDate("task_limitdate");
				Timestamp update = rs.getTimestamp("task_update");
				String user = rs.getString("task_user");
				int status_code = rs.getInt("task_status");
				ToDo todo = new ToDo(id, title, contents, limitdate, update, user, status_code);
				todoList.add(todo);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return todoList;
	}
		
	public boolean create(ToDo todo) {
		//JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベース接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			//INSERT文の準備（idは自動連番なので指定しなくてよい）
			String sql = "INSERT INTO todolist(task_name, task_contents, task_limitdate, task_user, task_status) VALUES(?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//INSERT文中の「?」に使用する値を設定してSQL文を完成
			pStmt.setString(1, todo.getName());
			pStmt.setString(2, todo.getContents());
			pStmt.setDate(3, todo.getLimitdate());
			pStmt.setString(4, todo.getUser());
			pStmt.setInt(5, todo.status_StrToInt(todo.getStatus()));
			
			//INSERT文を実行（resultには追加された行数が代入される）
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ToDo pickToDo(int id) {
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベース接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			//SELECT文の準備
			String sql = "SELECT task_id, task_name, task_contents, task_limitdate, task_update, task_user, task_status FROM todolist WHERE task_id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//INSERT文中の「?」に使用する値を設定してSQL文を完成
			pStmt.setInt(1, id);
			
			//SELECT文を実行
			ResultSet rs = pStmt.executeQuery();
			rs.next();
			String name = rs.getString("task_name");
			String contents = rs.getString("task_contents");
			Date limitdate = rs.getDate("task_limitdate");
			Timestamp update = rs.getTimestamp("task_update");
			String user = rs.getString("task_user");
			int status_code = rs.getInt("task_status");
			ToDo todo = new ToDo(id, name, contents, limitdate, update, user, status_code);
			
			return todo;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean renew(int id,ToDo todo) {
		//JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベース接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			//INSERT文の準備（idは自動連番なので指定しなくてよい）
			String sql = "UPDATE todolist SET task_name=?, task_contents=?, task_limitdate=?, task_user=?, task_status=? WHERE task_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//INSERT文中の「?」に使用する値を設定してSQL文を完成
			pStmt.setString(1, todo.getName());
			pStmt.setString(2, todo.getContents());
			pStmt.setDate(3, todo.getLimitdate());
			pStmt.setString(4, todo.getUser());
			pStmt.setInt(5, todo.status_StrToInt(todo.getStatus()));
			pStmt.setInt(6, id);
			
			//INSERT文を実行（resultには追加された行数が代入される）
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(int id) {
		//JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベース接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			//SQL文の準備（idは自動連番なので指定しなくてよい）
			String sql = "DELETE FROM todolist WHERE task_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//文中の「?」に使用する値を設定してSQL文を完成
			pStmt.setInt(1, id);
			
			//SQL文を実行（resultには追加された行数が代入される）
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
