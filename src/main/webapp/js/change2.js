var account = sessionStorage.getItem("account");
var currentScript = document.createElement("script");
currentScript.type = "module";
currentScript.src = "js/connectall.js";
document.body.appendChild(currentScript);
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
      document.getElementById("username").innerText =
        "Hello 最高管理员" + data.name;
    });
};

function loadScript(src) {
  // 如果当前脚本存在，先移除它
  if (currentScript) {
    currentScript.parentNode.removeChild(currentScript);
  }

  // 创建新的script元素
  currentScript = document.createElement("script");
  currentScript.type = "module";
  currentScript.src = src + "?v=" + new Date().getTime(); // 添加时间戳避免缓存

  // 一旦新脚本加载完成，执行初始化操作
  currentScript.onload = function () {
    // 假设新脚本中有一个initialize函数来初始化
    if (window.initialize) {
      window.initialize();
    }
  };

  // 将新script插入到DOM中
  document.body.appendChild(currentScript);
}

function changeToUser(src) {
  document
    .getElementById("changeToUser")
    .addEventListener("click", function () {
      let table = document.getElementById("data-table");
      table.innerHTML = `<thead>
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
      </tbody>`;
    });
  loadScript(src);
}
function changeToApparatus(src) {
  document
    .getElementById("changeToApparatus")
    .addEventListener("click", function () {
      let table = document.getElementById("data-table");
      table.innerHTML = `<thead>
        <tr>
          <th id="id">id</th>
          <th id="name">name</th>
          <th id="type">type</th>
          <th id="phone">phone</th>
          <th id="who">who</th>
          <th id="address">address</th>
          <th id="description">description</th>
          <th>操作1</th>
          <th>操作2</th>
          <th id="image">image</th>
        </tr>
      </thead>
      <tbody>
      </tbody>`;
    });
  loadScript(src);
}
document.getElementById("logout").addEventListener("click", function () {
  sessionStorage.removeItem("account");
  window.location.href = "login";
});
document.getElementById("changeToUser").addEventListener("click", function () {
  changeToUser("js/connectall.js");
});
document
  .getElementById("changeToApparatus")
  .addEventListener("click", function () {
    changeToApparatus("js/apparatus2.js");
  });
