package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.todoDAO;

public class ToDo {
	private int id;
	private String name;
	private String contents;
	private Date limitdate;
	private Timestamp update;
	private String user;
	private String status;
	
	public ToDo() {}
	public ToDo(String name, String contents, String limitdate, String user, String status_str) {
		this.name=name;
		this.contents=contents;
		this.limitdate=limitdate_StrToDate(limitdate);
		this.user=user;
		this.status=status_str;
	}
	public ToDo(int id, String name, String contents, Date limitdate, Timestamp update, String user, int status_code) {
		this.id=id;
		this.name=name;
		this.contents=contents;
		this.limitdate=limitdate;
		this.update=update;
		this.user=user;
		this.status=status_IntToStr(status_code);
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getContents() {
		return contents;
	}
	public Date getLimitdate() {
		return limitdate;
	}
	public Timestamp getUpdate() {
		return update;
	}
	public String getUser() {
		return user;
	}
	public String getStatus() {
		return status;
	}
	
	public static String inputCheck(String name, String contents, String limitdate, String user, String status) {
		String errorMsg = "";
		
		//タスク名称が空白
		if(name.isEmpty()) {
			errorMsg+="タスク名称が空白です。<br>";
		}
		//タスク名称が50文字以上
		else if(name.length() > 50) {
			errorMsg+="タスク名称が長すぎます。<br>";
		}
		//タスク内容が空白
		if(contents.isEmpty()) {
			errorMsg+="タスク内容が空白です。<br>";
		}
		//タスク内容が100文字以上
		else if(contents.length() > 100) {
			errorMsg+="タスク内容が長すぎます。<br>";
		}
		//タスク期限が空白
		if(limitdate.isEmpty()) {
			errorMsg+="タスク期限が空白です。<br>";
		}
		//タスク期限がパターンにマッチしないとき
		else if(!limitdate.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
			errorMsg+="タスク期限の入力形式が違います。<br>";
		}
		//タスク期限が存在しない日付（2025-02-30とか・・・）
		else if(!checkDate(limitdate)) {
			errorMsg+="タスク期限が誤りです。<br>";
		}
		//タスク期限が過去日付（タスク状況が未着手or着手のとき）
		else if(!checkDateAndStatus(limitdate, status)) {
			errorMsg+="タスク期限が過去日付です。<br>";
		}
		//タスク担当者が空白
		if(user.isEmpty()) {
			errorMsg+="タスク担当者が空白です。<br>";
		}
		//タスク担当者が20文字以上
		else if(user.length() > 20) {
			errorMsg+="タスク担当者が長すぎます。<br>";
		}
		
		return errorMsg;
	}
	public static boolean checkDate(String str) {
		try {
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			format.setLenient(false);
			format.parse(str);
			return true;
		}catch (ParseException e) {
			return false;
		}
	}
	public static boolean checkDateAndStatus(String limitdate, String status) {
		if(status.equals("未着手") || status.equals("着手")) {
			LocalDate currentDate = LocalDate.now();
			if(currentDate.isAfter(LocalDate.parse(limitdate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
				return false;
			}
		}
		return true;
	}
	
	public List<ToDo> listing() {
		todoDAO dao = new todoDAO();
		List<ToDo> todoList = dao.findAll();
		return todoList;
	}
	public void adder(ToDo todo) {
		todoDAO dao = new todoDAO();
		dao.create(todo);
	}
	public void updater(int id, ToDo todo) {
		todoDAO dao = new todoDAO();
		dao.renew(id, todo);
	}
	public void erase(int id) {
		todoDAO dao = new todoDAO();
		dao.delete(id);
	}
	
	public Date limitdate_StrToDate(String str) {
		Date limitdate= Date.valueOf(str);;
		return limitdate;
	}
	
	public int status_StrToInt(String str) {
		int status_code=0;
		switch(str) {
		case "未着手":
			status_code=0;
			break;
		case "着手":
			status_code=1;
			break;
		case "完了":
			status_code=2;
			break;
		case "凍結":
			status_code=3;
			break;
		}
		return status_code;
	}
	public String status_IntToStr(int code) {
		String status="未着手";
		switch(code) {
		case 0:
			status="未着手";
			break;
		case 1:
			status="着手";
			break;
		case 2:
			status="完了";
			break;
		case 3:
			status="凍結";
			break;
		}
		return status;
	}
}