<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/26
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${requestScope.message}
<form action="query.do" method="post">
    <input type="submit" value="查询余额"><br>
</form>
${requestScope.money}
<form action="saving.do" method="post">
    money<input type="text" name="money"><br>
    <input type="submit" value="存款"><br>
</form>
<form action="withdraw.do" method="post">
    money<input type="text" name="money"><br>
    <input type="submit" value="取款"><br>
</form>
<form action="transfer.do" method="post">
    money<input type="text" name="money"><br>
    targetId<input type="text" name="targetId"><br>
    <input type="submit" value="转账"><br>
</form>
</body>
</html>
