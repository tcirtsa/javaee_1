<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html lang="zh-CN">
  <head></head>
  <body>
    <form action="update_pay" method="post"">
      <label>图片<input type="file" name="file" /></label>
      <label>id:<input type="text" name="id" /></label>
      <label>pay:<input type="text" name="pay" /></label>
      <label>description:<input type="text" name="description" /></label>
      <label>who:<input type="text" name="who" /></label>
      <input type="submit" value="上传" />
    </form>
  </body>
  <script>
    // 获取查询字符串
    let queryString = location.search;

    // 创建一个URLSearchParams对象
    let params = new URLSearchParams(queryString);
    document.getElementsByName("id")[0].value = params.get("id");
    document.getElementsByName("who")[0].value = params.get("who");
  </script>
</html>
