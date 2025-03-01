package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ToDo;

@WebServlet("/TodolistServlet")
public class TodolistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public TodolistServlet() {
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//todolistを取得
		ToDo todo = new ToDo();
		List<ToDo> todoList = todo.listing();
		
		//リクエストスコープにtodolistを保存
		request.setAttribute("todoList", todoList);
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/todolist.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
