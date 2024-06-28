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
    <p>Hello 维修员</p>
    <form action="#">
      <label
        >搜索仪器:
        <input id="query_input" />
      </label>
      <button id="query" type="button">搜索</button>
    </form>
    <table id="data-table">
      <thead>
        <tr>
          <th id="account">account</th>
          <th id="name">name</th>
          <th id="password">password</th>
          <th id="phone">phone</th>
          <th id="address">address</th>
          <th id="authority">authority</th>
          <th id="image">image</th>
          <th>操作1</th>
          <th>操作2</th>
        </tr>
      </thead>
      <tbody>
        <!-- 数据将显示在这里 -->
      </tbody>
    </table>
    <button id="refreshButton">刷新数据</button>
  </body>
  <script src="js/apparatus.js"></script>
</html>
