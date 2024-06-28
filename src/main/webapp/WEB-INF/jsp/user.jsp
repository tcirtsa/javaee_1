<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
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
      td {
        width: 100px;
        height: 100px;
      }
      img {
        width: 100%; /* 图片宽度设置为单元格宽度的100% */
        height: auto; /* 图片高度根据宽度等比例缩放 */
        object-fit: contain; /* 保持图片的宽高比，不被拉伸 */
      }
    </style>
  </head>
  <body>
    <div class="header">
      <div class="profile">
        <div class="image-container">
          <img id="image" alt="用户头像" class="image" />
        </div>
        <br /><span id="username"></span>
      </div>
      <button id="reset_psd">修改密码</button>
      <button id="reset_head">上传头像</button>
    </div>
    <div class="content">
      <table id="data-table">
        <thead>
          <tr>
            <th id="id">id</th>
            <th id="name">name</th>
            <th id="type">type</th>
            <th id="phone">status</th>
            <th id="who">who</th>
            <th id="address">address</th>
            <th id="description">description</th>
            <th id="time">time</th>
            <th>操作1</th>
            <th>操作2</th>
            <th id="image">image</th>
          </tr>
        </thead>
        <tbody>
          <!-- 数据将显示在这里 -->
        </tbody>
      </table>
    </div>
  </body>
  <script>
    document.getElementById("reset_psd").addEventListener("click", function () {
      window.location.href = "reset_psd";
    });
  </script>
  <script src="js/user.js"></script>
  <script src="js/apparatus.js"></script>
</html>
