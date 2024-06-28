<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Forget Password</title>
    <link href="icon.ico" rel="icon" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="css/table.css" />
  </head>
  <body>
    //原密码
    <label>原密码<input type="text" name="oldpassword" required /></label>
    //新密码
    <label>新密码<input type="text" name="password" required /></label>
    //确认密码
    <label>确认密码<input type="text" name="repassword" required /></label>
    <button id="reset_button">重置</button>
    <form action="login" method="post">
      <input type="submit" value="Back" />
    </form>
  </body>
  <script>
    let reset_button = document.getElementById("reset_button");
    let account=sessionStorage.getItem("account");
    reset_button.onclick = function () {
      if (
        document.getElementsByName("password")[0].value !==
        document.getElementsByName("repassword")[0].value
      ) {
        alert("两次密码不一致");
        return;
      } else {
        fetch("reset_psd", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            account: account,
            password: document.getElementsByName("oldpassword")[0].value,
            address: document.getElementsByName("password")[0].value,
          }),
        })
          .then((response) => {
            if (!response.ok) {
              // 使用 response.text() 来读取错误消息
              return response.text().then((errorMessage) => {
                throw new Error(
                  `请求失败：${response.status} -${errorMessage}`
                );
              });
            }
            return response.json();
          })
          .then((data) => {
            alert(data);
            window.location.href = "login";
          })
          .catch((error) => {
            alert("请求错误:", error);
          });
      }
    };
  </script>
</html>
