<%@
page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="model.Login,model.ToDo,java.util.List"
%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
//セッションスコープに保存された情報を取得
Login loginUser = (Login)session.getAttribute("loginUser");
List<ToDo> todoList = (List<ToDo>)request.getAttribute("todoList");
//リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String)request.getAttribute("errorMsg");
String msg = (String)request.getAttribute("msg");
%>
<!DOCTYPE html>
<html>
<style>
body {
  background-image: url("blue_paintart04.png");
  background-size: cover;
}
.dots {
  border-width: 0 0 8px;
  border-style: solid;
  border-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 2 1"><circle fill="hsla(0, 0%, 65%, 1.0)" cx="1" cy="0.5" r="0.5"/></svg>') 0 0 100% repeat;
   width: 216px;
}
</style>
<head>
<meta charset="UTF-8">
<title>スクラムマネージャー</title>
</head>
<body>
<br>
<br>
<br>
<center>
<table style="width:95%; border:none; border-color:white">
<tr>
<td style="background:white; color:midnightblue;"><b>　ログインユーザー：${loginUser.getUser_id() }さん　</b></td>
<th style="background:#33ccff; color:midnightblue; width:150px"><a href="TodolistServlet">タスク一覧</a></th>
<th style="background:#e0f3f8; color:midnightblue; width:150px"><a href="RegisterServlet">新規登録</a></th>
<th style="background:#e0f3f8; color:midnightblue; width:150px">変更・削除</th>
<th style="background:#e0f3f8; color:midnightblue; width:150px"><form method="post" name="form" action="LogoutServlet"><input type="hidden" name="action" value="logout"></form><a href="javascript:form.submit()">ログアウト</a></th>
<td style="width:150px"><td>
</tr>
</table>
<fieldset style="background:#ffffff; border:4px; border-radius:10px;font-size: 100%; padding: 20px; width:95%; ">
<h1 style="color:midnightblue">スクラムマネージャー</h1>
<hr class="dots">
<p style="color:red">${errorMsg }</p><p style="color:blue">${msg }</p>
【　タスク一覧　】
<table border="3px" style="border-color:midnightblue; border-collapse: collapse">
<tr>
<th style="background:midnightblue; color:white;">　タスクID　</th>
<th style="background:midnightblue; color:white;">　タスク名称　</th>
<th style="background:midnightblue; color:white;">　タスク内容　</th>
<th style="background:midnightblue; color:white;">　タスク期限　</th>
<th style="background:midnightblue; color:white;">　最終更新日　</th>
<th style="background:midnightblue; color:white;">　タスク担当者　</th>
<th style="background:midnightblue; color:white;">　タスク状況　</th>
<th style="background:midnightblue; color:white;">　詳細画面　</th>
</tr>
<c:forEach var="todo" items="${todoList }" >
<p>
<tr>
<td>　${todo.id }　</td>
<td>　${todo.name }　</td>
<td>　${todo.contents }　</td>
<td>　${todo.limitdate }　</td>
<td>　<fmt:formatDate value="${todo.update }" pattern="yyyy-MM-dd" />　</td>
<td>　${todo.user }</td>
<td>　${todo.status }</td>
<td>　<a href="DetailServlet?id=${todo.id }">変更・削除</a>　</td>
</tr>
</c:forEach>
<table>
</fieldset>
</center>
<br>
<br>
<br>
</body>
</html>