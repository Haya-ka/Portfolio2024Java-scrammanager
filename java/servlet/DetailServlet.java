package servlet;

import java.io.IOException;
import java.util.List;

import dao.todoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ToDo;

@WebServlet("/DetailServlet")
public class DetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public DetailServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//idを取得
		String idStr = request.getParameter("id");
		int id = 1;
		if (idStr != null) {
			id = Integer.parseInt(idStr);
        }
		todoDAO dao = new todoDAO();
		ToDo todo = dao.pickToDo(id);
		
		//sessionスコープにtodoを保存
		HttpSession session = request.getSession();
		session.setAttribute("todo", todo);
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/tododetail.jsp");
		dispatcher.forward(request, response);
	}
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		HttpSession session = request.getSession();
		ToDo todo = (ToDo)session.getAttribute("todo");
		
		request.setCharacterEncoding("UTF-8");
		
		int id = todo.getId();
		String name = request.getParameter("name");
		String contents = request.getParameter("contents");
		String limitdate = request.getParameter("limitdate");
		String user = request.getParameter("user");
		String status = request.getParameter("status");
		
		String select = request.getParameter("select");
		
		if(select.equals("変更する")) {
			String errorMsg = ToDo.inputCheck(name, contents, limitdate, user, status);
			
			if(errorMsg.isEmpty()) {
				todo = new ToDo(name, contents, limitdate, user, status);
				//新規todoを追加
				todo.updater(id,todo);
				//todolistを取得
				List<ToDo> todoList = todo.listing();
				
				request.setAttribute("todoList", todoList);
				request.setAttribute("msg", "タスクの情報を更新しました。");
				//フォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/todolist.jsp");
				dispatcher.forward(request, response);
			}
				else {
					//リクエストスコープにerrorMsgを保存
					request.setAttribute("errorMsg", errorMsg);
					//sessionスコープにtodoを保存
					session.setAttribute("todo", todo);
					//フォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/tododetail.jsp");
					dispatcher.forward(request, response);
			}
		}
		else if(select.equals("削除する")) {
			todo = new ToDo(name, contents, limitdate, user, status);
			//指定idのtodoを削除
			todo.erase(id);
			
			//todolistを取得
			List<ToDo> todoList = todo.listing();
			//requestに保存
			request.setAttribute("todoList", todoList);
			request.setAttribute("msg", "タスクを削除しました。");
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/todolist.jsp");
			dispatcher.forward(request, response);
		}
	}
}
