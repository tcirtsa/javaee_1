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
        let error = "${error}";
        let account = "${account}";
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
    <label>账号:<input name="account" id="account" /></label>
    <label>密码:<input name="password" id="password" /></label>
    <button id="login_user">用户登录</button>
    <button id="login_admin">管理员登录</button>
    <form action="register" method="get">
      <input id="register" type="submit" value="注册" />
    </form>
  </body>
  <script type="text/javascript">
    let account = document.querySelector("#account");
    let password = document.querySelector("#password");
    function login_user() {
      fetch("login_check", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          account: account.value,
          password: password.value,
        }),
      }).then((response) => {
        if (!response.ok) {
          // 使用 response.text() 来读取错误消息
          return response.text().then((errorMessage) => {
            throw new Error(`请求失败：${response.status} -${errorMessage}`);
          });
        }
        sessionStorage.setItem("account", account.value);
        window.location.href = "/user";
      });
    }

    function login_admin() {
      fetch("login_check", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          account: account.value,
          password: password.value,
        }),
      })
        .then((response) => {
          if (!response.ok) {
            // 使用 response.text() 来读取错误消息
            return response.text().then((errorMessage) => {
              throw new Error(`请求失败：${response.status} -${errorMessage}`);
            });
          }
          sessionStorage.setItem("account", account.value);
          return response.text();
        })
        .then((data) => {
          if (data == "3") {
            window.location.href = "/controlall";
          } else if (data == "2") {
            window.location.href = "/repair";
          } else if (data == "1") {
            window.location.href = "/control";
          } else {
            alert("您不是管理员，将为您登录用户界面");
            window.location.href = "/user";
          }
        });
    }
    document.querySelector("#login_user").addEventListener("click", login_user);
    document
      .querySelector("#login_admin")
      .addEventListener("click", login_admin);
  </script>
</html>
