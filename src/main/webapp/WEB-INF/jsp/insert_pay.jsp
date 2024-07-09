<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html lang="zh-CN">
    <head></head>
    <body>
        <form action="insert_pay" method="post">
            <label>description:<input type="text" name="description" /></label>
            <label>who:<input type="text" name="who" /></label>
            <button id="insert_button" type="submit">提交</button>
        </form>
    </body>
    <script>
        document.getElementById("insert_button").addEventListener("click", function () {
            fetch("insert_pay", {
                method: "POST",
                body: JSON.stringify({
                    description: document.getElementsByName("description")[0].value,
                    who: document.getElementsByName("who")[0].value
                }),
                headers: {
                    "Content-Type": "application/json"
                }
            })
        });
    </script>
</html>