package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Login;
import model.ToDo;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public LoginServlet() {
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		String user_pass = request.getParameter("user_pass");
		
		Login login = new Login(user_id, user_pass);
		Login loginUser = login.execute(login);
		
		//ログイン処理の成否によって処理を分岐
		if(loginUser != null) {	//ログイン成功時
			//todolistを取得
			ToDo todo = new ToDo();
			List<ToDo> todoList = todo.listing();
			
			//リクエストスコープにtodolistを保存
			request.setAttribute("todoList", todoList);
			//セッションスコープにユーザーIDを保存
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/todolist.jsp");
			dispatcher.forward(request, response);
		}
		//ログイン失敗時
		else {
			//requestスコープにメッセージを保存
			request.setAttribute("msg", "IDとパスワードが一致しませんでした。");
			//リダイレクト
			response.sendRedirect("LoginServlet");
		}
	}
}