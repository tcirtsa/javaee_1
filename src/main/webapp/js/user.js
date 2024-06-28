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

function uploadFile() {
  // 创建一个临时的文件输入元素
  const fileInput = document.createElement("input");
  fileInput.type = "file";
  fileInput.accept = "image/*"; // 接受所有图片类型

  // 绑定选择文件的事件
  fileInput.addEventListener("change", function (event) {
    const file = event.target.files[0];
    if (!file) {
      alert("请选择一个文件");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("fileName", account);

    fetch("upload", {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.text();
      })
      .then((result) => {
        alert(result);
        alert("上传成功");
        fetchData();
      })
      .catch((error) => {
        alert("上传失败:", error);
        alert("文件上传失败");
      });
  });

  // 触发文件选择器
  fileInput.click();
}
document.getElementById("reset_head").addEventListener("click", uploadFile);
