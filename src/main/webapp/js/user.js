let account = sessionStorage.getItem("account");
window.onload = function () {
  fetch("query", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ account: account }),
  })
    .then((response) => {
      if (!response.ok) {
        // 使用 response.text() 来读取错误消息
        return response.text().then((errorMessage) => {
          throw new Error(`请求失败：${response.status} -${errorMessage}`);
        });
      }
      return response.json();
    })
    .then((data) => {
      document.getElementById("username").innerText = "你好：" + data.name;
      fetch("image", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ head: data.head }), // 发送包含图片URL的JSON对象
      })
        .then((response) => response.blob()) // 将响应解析为Blob对象
        .then((blob) => {
          // 将Blob对象转换为Image URL
          const imgURL = URL.createObjectURL(blob);

          document.getElementById("image").src = imgURL;
        })
        .catch((error) => {
          alert("加载图片出错:", error);
        });
    });
};
