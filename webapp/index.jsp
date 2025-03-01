<%@
page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%>
<%
//リクエストスコープに保存されたメッセージを取得
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
<fieldset style="background:#ffffff; border:4px; border-radius:10px;font-size: 100%; padding: 20px; width:500px; ">
<h1 style="color:midnightblue">スクラムマネージャー</h1>
<hr class="dots">
<p style="color:blue">${msg }</p><br>
<form action="LoginServlet" method="post">
ユーザーID <input type="text" name="user_id"><br>
パスワード <input type="password" name="user_pass"><br>
<br>
<input type="submit" value="ログイン">
</form>
</fieldset>
</center>
<br>
<br>
<br>
</body>
</html>