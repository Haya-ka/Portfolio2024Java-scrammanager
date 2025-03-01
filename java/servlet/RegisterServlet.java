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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public RegisterServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/todoregister.jsp");
		dispatcher.forward(request, response);
	}
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String contents = request.getParameter("contents");
		String limitdate = request.getParameter("limitdate");
		String user = request.getParameter("user");
		String status = request.getParameter("status");
		
		String errorMsg = ToDo.inputCheck(name, contents, limitdate, user, status);
		
		if(errorMsg.isEmpty()) {
			ToDo todo = new ToDo(name, contents, limitdate, user, status);
			//新規todoを追加
			todo.adder(todo);
			//todolistを取得
			List<ToDo> todoList = todo.listing();
		
			//リクエストスコープにtodolistを保存
			request.setAttribute("todoList", todoList);
			request.setAttribute("msg", "新規タスクの登録が完了しました。");
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/todolist.jsp");
			dispatcher.forward(request, response);
		}
		else {
			//リクエストスコープにerrorMsgを保存
			request.setAttribute("errorMsg", errorMsg);
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/todoregister.jsp");
			dispatcher.forward(request, response);
		}
	}
}
