<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link href="icon.ico" rel="icon" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="css/table.css" />
  </head>
  <body>
    <form action="register" method="post" enctype="multipart/form-data">
      <input type="file" name="image" accept="image/*" />
      <label>账号:<input name="account" required /></label>
      <label
        >密码:<input
          name="password"
          pattern="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()])[A-Za-z\d!@#$%^&*()]{8,}"
          title="密码必须至少8个字符,至少包含英文、数字和特殊字符"
          required
      /></label>
      <label>姓名:<input name="name" required /> </label>
      <label
        >电话:<input
          name="phone"
          placeholder="非必填,要求11位中国手机号码"
          pattern="^1[3-9]\d{9}$
        "
      /></label>
      <label>地址:<input name="address" placeholder="非必填" /></label>
      <input id="register" type="submit" value="注册" />
    </form>
  </body>
</html>
