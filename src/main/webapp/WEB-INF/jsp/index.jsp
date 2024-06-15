<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="icon.ico" rel="icon" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="css/table.css">
</head>
<body>
<p>Hello World</p>
<form action="#">
    <label>搜索账号:
        <input id="query_input">
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
        <th>操作1</th>
        <th>操作2</th>
        <th id="image">image</th>
    </tr>
    </thead>
    <tbody>
    <!-- 数据将显示在这里 -->
    </tbody>
</table>
<button id="refreshButton">刷新数据</button>
</body>
<script src="js/connect.js" type="module"></script>
</html>