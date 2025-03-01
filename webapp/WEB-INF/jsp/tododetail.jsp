<%@
page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="model.Login,model.ToDo,java.util.List"
%>
<%
//sessionスコープに保存されたメッセージを取得
ToDo todo = (ToDo)session.getAttribute("todo");
//リクエストスコープに保存されたメッセージを取得
String msg = (String)request.getAttribute("msg");
String errorMsg = (String)request.getAttribute("errorMsg");
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
<th style="background:#e0f3f8; color:midnightblue; width:150px"><a href="TodolistServlet">タスク一覧</a></th>
<th style="background:#e0f3f8; color:midnightblue; width:150px"><a href="RegisterServlet">新規登録</a></th>
<th style="background:#33ccff; color:midnightblue; width:150px">変更・削除</th>
<th style="background:#e0f3f8; color:midnightblue; width:150px"><form method="post" name="form" action="LogoutServlet"><input type="hidden" name="action" value="logout"></form><a href="javascript:form.submit()">ログアウト</a></th>
<td style="width:150px"><td>
</tr>
</table>
<fieldset style="background:white; border:4px; border-radius:10px;font-size: 100%; padding: 20px; width:95%; ">
<h1 style="color:midnightblue">スクラムマネージャー</h1>
<hr class="dots">
<p style="color:red">${errorMsg }</p><p style="color:blue">${msg }</p>
【　変更・削除　】<br>
<br>
<form action="DetailServlet" method="post">
<table border="0">
<tr>
<th style="background:midnightblue; color:white;">タスクID</th><td>${todo.getId() }</td>
</tr>
<tr>
<th style="background:midnightblue; color:white;">タスク名称</th><td><input type="text" name="name" style="width:500px" value="${todo.getName() }"></td>
</tr>
<tr>
<th style="background:midnightblue; color:white;">タスク内容</th><td><input type="text" name="contents" style="width:500px" value="${todo.getContents() }"></td>
</tr>
<tr>
<th style="background:midnightblue; color:white;">タスク期限</th><td><input type="text" name="limitdate" style="width:200px" value="${todo.getLimitdate() }"></td>
</tr>
<tr>
<th style="background:midnightblue; color:white;">　タスク担当者　</th><td><input type="text" name="user" style="width:150px" value="${todo.getUser() }"></td>
</tr>
<tr>
<th style="background:midnightblue; color:white;">タスク状況</th>
<td><select name="status" id="status" value="${todo.getStatus() }">
<option value="未着手">未着手</option>
<option value="着手">着手</option>
<option value="完了">完了</option>
<option value="凍結">凍結</option>
</select></td>
</tr>
<table>
<br>
<input type="submit" name="select" value="変更する">
　　　<input type="submit" name="select" value="削除する">
</form>
</fieldset>
</center>
<br>
<br>
<br>
</body>
</html>