<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2017/8/27
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Download</title>
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="js/download.js"></script>
</head>
<body>
用户名：<input id="name" type="text"/>
密码：<input id="password" type="password"/>
<button type="button" id="but" onclick="login()">登录</button>

组id：<input id="task_id" type="text"/>
<button type="button" id="but2" onclick="download()">下载</button>

</body>
</html>
