package model;

import dao.LoginDAO;

public class Login {
	private String user_id;
	private String user_pass;
	
	public Login(String user_id, String user_pass) {
		this.user_id=user_id;
		this.user_pass=user_pass;
	}
	public String getUser_id() {
		return user_id;
	}
	public String getUser_pass() {
		return user_pass;
	}
	public Login execute(Login login) {
		LoginDAO dao = new LoginDAO();
		Login loginUser = dao.findByLogin(login);
		return loginUser;
	}
}