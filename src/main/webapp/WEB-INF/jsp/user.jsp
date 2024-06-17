<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8" />
    <title>用户界面示例</title>
    <style>
      body {
        font-family: Arial, sans-serif;
      }
      .header {
        background-color: #333;
        color: #fff;
        padding: 10px;
      }
      .profile {
        float: right;
        margin-top: 5px;
      }
      .content {
        margin-top: 20px;
      }
    </style>
  </head>
  <body>
    <div class="header">
      <div class="profile">
        <img src="${user.head}" alt="用户头像" width="40" height="40" />
        <span>${user.name}</span>
      </div>
    </div>
    <div class="content">
      <!-- 其他内容在这里 -->
      这里是其他内容。
    </div>
  </body>
</html>
