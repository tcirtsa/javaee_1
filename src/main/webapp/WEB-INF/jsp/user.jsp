<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8" />
    <title>用户界面示例</title>
    <style>
      body {
        font-family: Arial, sans-serif;
      }
      .header {
        height: 200px;
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
      .image-container {
        width: 150px; /* 或者设置任何你想要的宽度 */
        height: 150px; /* 或者设置任何你想要的高度 */
        overflow: hidden; /* 隐藏超出容器的部分 */
      }

      .image {
        width: 100%; /* 或者设置任何你想要的宽度 */
        height: 100%; /* 或者设置任何你想要的宽度 */
        object-fit: cover; /* 等比例裁切图片 */
      }
      span {
        font-weight: bold;
      }
    </style>
  </head>
  <body>
    <div class="header">
      <div class="profile">
        <div class="image-container">
          <img src="${user.head}" alt="用户头像" class="image" />
        </div>
        <br /><span>你好:${user.name}</span>
      </div>
    </div>
    <div class="content">
      <!-- 其他内容在这里 -->
      这里是其他内容。
    </div>
  </body>
</html>
