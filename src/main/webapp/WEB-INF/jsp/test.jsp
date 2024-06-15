<%--
  Created by IntelliJ IDEA.
  User: tcirtsa
  Date: 2024/6/13
  Time: 下午4:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="upload" method="POST" enctype="multipart/form-data">
    <input type="file" name="file" accept="image/*" />
    <input type="text" name="fileName">
    <input type="submit" value="Upload" />
</form>
</body>
</html>
