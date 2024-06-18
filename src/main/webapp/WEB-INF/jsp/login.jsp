<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link href="icon.ico" rel="icon" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="css/table.css" />
    <style>
      table {
        width: 100%;
        border-collapse: collapse;
      }
      table,
      th,
      td {
        border: 1px solid black;
      }
      th,
      td {
        padding: 5px;
      }
    </style>
    <script type="text/javascript">
      // 从Flash属性中获取错误消息并弹出
      window.onload = function () {
        var error = "${error}";
        var account = "${account}";
        if (error) {
          alert(error);
          // 清除Flash属性，防止重复弹出
          window.location.href = window.location.pathname;
        } else if (account) {
          alert("欢迎您," + account);
          window.location.href = window.location.pathname;
        }
      };
    </script>
  </head>
  <body>
    <form action="login" method="post">
      <label>账号:<input name="account" /></label>
      <label>密码:<input name="password" /></label>
      <input name="role" type="submit" value="用户登录" />
      <input name="role" type="submit" value="管理员登录" />
    </form>
    <form action="register" method="get">
      <input id="register" type="submit" value="注册" />
    </form>
  </body>
</html>
