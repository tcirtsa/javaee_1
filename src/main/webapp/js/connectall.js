function makeCellEditable(cell1, cell2) {
  const originalText = cell2.innerText; // 保存原始文本
  cell2.innerHTML = ""; // 清空单元格内容
  const input = document.createElement("input"); // 创建输入框
  input.type = "text";
  input.value = originalText;
  input.style.width = "100%";
  cell2.appendChild(input); // 单元格内添加输入框
  input.focus();

  // 输入框失去焦点时保存更改并恢复到文本模式
  input.addEventListener("blur", () => {
    const newText = input.value.trim();
    cell2.innerHTML = newText; // 更新单元格文本
    // 可在此处发送更新到服务器的请求
    fetch("update_psd", {
      method: "POST", // or 'PUT'
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ account: cell1.innerText, password: newText }),
    })
      .then((response) => {
        if (!response.ok) {
          // 使用 response.text() 来读取错误消息
          return response.text().then((errorMessage) => {
            throw new Error(`请求失败：${response.status} -${errorMessage}`);
          });
        }
        // 使用 response.text() 来读取成功的响应
        return response.text();
      })
      .then((data) => {
        alert("Success:", data);
        fetchData();
      })
      .catch((error) => {
        alert("Error:", error);
      });
  });

  // 输入框内按下回车键时同样保存更改
  input.addEventListener("keydown", (e) => {
    if (e.key === "Enter") {
      input.blur(); // 触发blur事件
    }
  });
}

function makeCellEditable1(cell1, cell2) {
  const originalText = cell2.innerText; // 保存原始文本
  cell2.innerHTML = ""; // 清空单元格内容
  const input = document.createElement("input"); // 创建输入框
  input.type = "text";
  input.value = originalText;
  input.style.width = "100%";
  cell2.appendChild(input); // 单元格内添加输入框
  input.focus();

  // 输入框失去焦点时保存更改并恢复到文本模式
  input.addEventListener("blur", () => {
    const newText = input.value.trim();
    cell2.innerHTML = newText; // 更新单元格文本
    // 可在此处发送更新到服务器的请求
    fetch("update_authority", {
      method: "POST", // or 'PUT'
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ account: cell1.innerText, authority: newText }),
    })
      .then((response) => {
        if (!response.ok) {
          fetchData();
          // 使用 response.text() 来读取错误消息
          return response.text().then((errorMessage) => {
            throw new Error(`请求失败：${response.status} -${errorMessage}`);
          });
        }
        // 使用 response.text() 来读取成功的响应
        return response.text();
      })
      .then((data) => {
        alert("Success:", data);
        fetchData();
      })
      .catch((error) => {
        alert("Error:", error);
      });
  });

  // 输入框内按下回车键时同样保存更改
  input.addEventListener("keydown", (e) => {
    if (e.key === "Enter") {
      input.blur(); // 触发blur事件
    }
  });
}

function fetchData() {
  // 清空表格并重新加载数据
  function loadData() {
    fetch("get_all_accounts", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          // 使用 response.text() 来读取错误消息
          return response.text().then((errorMessage) => {
            throw new Error(`请求失败：${response.status} -${errorMessage}`);
          });
        }
        // 只有当响应是OK时，才尝试解析JSON
        return response.json();
      })
      .then((data) => {
        const tableBody = document
          .getElementById("data-table")
          .getElementsByTagName("tbody")[0];
        tableBody.innerHTML = ""; // 清空现有表格内容

        data.forEach((row) => {
          const tr = document.createElement("tr");

          // 创建account单元格
          const accountCell = document.createElement("td");
          accountCell.innerText = row.account;
          tr.appendChild(accountCell);

          // 创建name单元格
          const nameCell = document.createElement("td");
          nameCell.innerText = row.name;
          tr.appendChild(nameCell);

          // 创建psd单元格
          const psdCell = document.createElement("td");
          psdCell.innerText = row.password;
          psdCell.addEventListener("click", () =>
            makeCellEditable(accountCell, psdCell)
          ); // 点击变为编辑状态
          tr.appendChild(psdCell);

          // 创建phone单元格
          const phoneCell = document.createElement("td");
          phoneCell.innerText = row.phone;
          tr.appendChild(phoneCell);

          // 创建address单元格
          const addressCell = document.createElement("td");
          addressCell.innerText = row.address;
          tr.appendChild(addressCell);

          // 创建authority单元格
          const authorityCell = document.createElement("td");
          authorityCell.innerText = row.authority;
          authorityCell.addEventListener("click", () =>
            makeCellEditable1(accountCell, authorityCell)
          ); // 点击变为编辑状态
          tr.appendChild(authorityCell);

          fetch("image", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ head: row.head }), // 发送包含图片URL的JSON对象
          })
            .then((response) => response.blob()) // 将响应解析为Blob对象
            .then((blob) => {
              // 将Blob对象转换为Image URL
              const imgURL = URL.createObjectURL(blob);

              const imageCell = document.createElement("td");
              const imageInput = document.createElement("img");
              imageInput.src = imgURL;
              imageCell.appendChild(imageInput);
              tr.appendChild(imageCell);
            })
            .catch((error) => {
              alert("加载图片出错:", error);
            });

          // 不可编辑的其他数据单元格...
          const deleteCell = document.createElement("td");
          const deleteButton = document.createElement("button");
          deleteButton.textContent = "删除";
          deleteButton.onclick = function () {
            deleteItem(row.account);
          };
          deleteCell.appendChild(deleteButton);
          tr.appendChild(deleteCell);

          const uploadCell = document.createElement("td");
          const uploadButton = document.createElement("button");
          uploadButton.textContent = "上传图片";
          uploadButton.onclick = function () {
            uploadFile(accountCell);
          };
          uploadCell.appendChild(uploadButton);
          tr.appendChild(uploadCell);

          tableBody.appendChild(tr);
        });
        const tr = document.createElement("tr");
        let a = "";
        let n = "";
        let p = "";
        let ph = "";
        let ad = "";
        let au = "";

        // 创建account单元格
        const accountCell = document.createElement("td");
        const accountInput = document.createElement("input");
        accountInput.innerText = "";
        accountInput.onblur = function (event) {
          a = event.target.value;
        };
        accountCell.appendChild(accountInput);
        tr.appendChild(accountCell);

        // 创建name单元格
        const nameCell = document.createElement("td");
        const nameInput = document.createElement("input");
        nameInput.innerText = "";
        nameInput.onblur = function (event) {
          n = event.target.value;
        };
        nameCell.appendChild(nameInput);
        tr.appendChild(nameCell);

        // 创建psd单元格
        const psdCell = document.createElement("td");
        const psdInput = document.createElement("input");
        psdInput.innerText = "";
        psdInput.onblur = function (event) {
          p = event.target.value;
        };
        psdCell.appendChild(psdInput);
        tr.appendChild(psdCell);

        // 创建phone单元格
        const phoneCell = document.createElement("td");
        const phoneInput = document.createElement("input");
        phoneInput.innerText = "";
        phoneInput.onblur = function (event) {
          ph = event.target.value;
        };
        phoneCell.appendChild(phoneInput);
        tr.appendChild(phoneCell);

        // 创建address单元格
        const addressCell = document.createElement("td");
        const addressInput = document.createElement("input");
        addressInput.innerText = "";
        addressInput.onblur = function (event) {
          ad = event.target.value;
        };
        addressCell.appendChild(addressInput);
        tr.appendChild(addressCell);

        // 创建authority单元格
        const authorityCell = document.createElement("td");
        const authorityInput = document.createElement("input");
        authorityInput.innerText = "";
        authorityInput.onblur = function (event) {
          au = event.target.value;
        };
        authorityCell.appendChild(authorityInput);
        tr.appendChild(authorityCell);

        // 不可编辑的其他数据单元格...
        const insertCell = document.createElement("td");
        const insertButton = document.createElement("button");
        insertButton.textContent = "添加";
        insertButton.onclick = function () {
          insert_user(a, n, p, ph, ad, au);
        };
        insertCell.appendChild(insertButton);
        tr.appendChild(insertCell);

        const nullCell = document.createElement("td");
        const nullButton = document.createElement("button");
        nullButton.textContent = "随便点";
        nullCell.appendChild(nullButton);
        tr.appendChild(nullCell);

        tableBody.appendChild(tr);
      })
      .catch((err) => alert("Error fetching data:", err));
  }

  // 加载数据
  loadData();
}
function insert_user(a, n, p, ph, ad, au) {
  if (a == "" || n == "" || p == "") {
    alert("请至少填上account、name和password");
    return;
  }
  if (au == "") {
    au = 0;
  }
  // 创建一个临时的文件输入元素
  const fileInput = document.createElement("input");
  fileInput.type = "file";
  fileInput.accept = "image/*"; // 接受所有图片类型

  fileInput.addEventListener("change", function (event) {
    const file = event.target.files[0];
    if (!file) {
      alert("请选择一个文件");
      return;
    }

    const formData = new FormData();
    formData.append("image", file);
    formData.append("account", a);
    formData.append("name", n);
    formData.append("password", p);
    formData.append("phone", ph);
    formData.append("address", ad);
    formData.append("authority", au);
    fetch("insert_user", {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        if (!response.ok) {
          // 使用 response.text() 来读取错误消息
          return response.text().then((errorMessage) => {
            throw new Error(`请求失败：${response.status} -${errorMessage}`);
          });
        }
        // 使用 response.text() 来读取成功的响应
        return response.text();
      })
      .then((data) => {
        alert("Success:", data);
        fetchData();
      })
      .catch((error) => {
        alert("Error:", error);
      });
  });
  fileInput.click();
}

function deleteItem(a) {
  fetch("delete_account", {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ account: a, password: "" }),
  })
    .then((response) => {
      if (!response.ok) {
        // 使用 response.text() 来读取错误消息
        return response.text().then((errorMessage) => {
          throw new Error(`请求失败：${response.status} -${errorMessage}`);
        });
      }
      // 只有当响应是OK时，才尝试解析JSON
      return response.text();
    })
    .then((result) => {
      if (result) {
        alert("Success:", result);
        // 删除成功，重新从服务器刷新数据
        fetchData();
      } else {
        alert("删除失败:", result);
      }
    })
    .catch((error) => {
      alert("请求错误:", error);
    });
}

// 页面刚加载完毕时自动获取数据
document.addEventListener("DOMContentLoaded", fetchData);

// 添加点击事件来手动刷新表格数据
document.getElementById("refreshButton").addEventListener("click", fetchData);

const query_account = document.querySelector("#query_input");
const query_button = document.querySelector("#query");
query_button.addEventListener("click", () => {
  fetch("query", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ account: query_account.value }),
  })
    .then((response) => {
      if (!response.ok) {
        // 使用 response.text() 来读取错误消息
        return response.text().then((errorMessage) => {
          throw new Error(`请求失败：${response.status} -${errorMessage}`);
        });
      }
      // 只有当响应是OK时，才尝试解析JSON
      return response.json();
    })
    .then((data) => {
      const tableBody = document
        .getElementById("data-table")
        .getElementsByTagName("tbody")[0];
      tableBody.innerHTML = ""; // 清空现有表格内容

      const tr = document.createElement("tr");

      // 创建account单元格
      const accountCell = document.createElement("td");
      accountCell.innerText = data.account;
      tr.appendChild(accountCell);

      // 创建name单元格
      const nameCell = document.createElement("td");
      nameCell.innerText = data.name;
      tr.appendChild(nameCell);

      // 创建psd单元格
      const psdCell = document.createElement("td");
      psdCell.innerText = data.password;
      psdCell.addEventListener("click", () =>
        makeCellEditable(accountCell, psdCell)
      ); // 点击变为编辑状态
      tr.appendChild(psdCell);

      // 创建phone单元格
      const phoneCell = document.createElement("td");
      phoneCell.innerText = data.phone;
      tr.appendChild(phoneCell);

      // 创建address单元格
      const addressCell = document.createElement("td");
      addressCell.innerText = data.address;
      tr.appendChild(addressCell);

      // 创建authority单元格
      const authorityCell = document.createElement("td");
      authorityCell.innerText = data.authority;
      authorityCell.addEventListener("click", () =>
        makeCellEditable1(accountCell, authorityCell)
      ); // 点击变为编辑状态
      tr.appendChild(authorityCell);

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

          const imageCell = document.createElement("td");
          const imageInput = document.createElement("img");
          imageInput.src = imgURL;
          imageCell.appendChild(imageInput);
          tr.appendChild(imageCell);
        })
        .catch((error) => {
          alert("加载图片出错:", error);
        });

      // 不可编辑的其他数据单元格...
      const deleteCell = document.createElement("td");
      const deleteButton = document.createElement("button");
      deleteButton.textContent = "删除";
      deleteButton.onclick = function () {
        deleteItem(row.account);
      };
      deleteCell.appendChild(deleteButton);
      tr.appendChild(deleteCell);

      const uploadCell = document.createElement("td");
      const uploadButton = document.createElement("button");
      uploadButton.textContent = "上传图片";
      uploadButton.onclick = function () {
        uploadFile(accountCell);
      };
      uploadCell.appendChild(uploadButton);
      tr.appendChild(uploadCell);

      tableBody.appendChild(tr);
    })
    .catch((err) => alert("Error fetching data:", err));
});

const sort_account = document.querySelector("#account");
let a = "true";
let p = "true";
function sort(e, b) {
  fetch("sort", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ account: a, password: p }),
  })
    .then((response) => {
      if (!response.ok) {
        // 使用 response.text() 来读取错误消息
        return response.text().then((errorMessage) => {
          throw new Error(`请求失败：${response.status} -${errorMessage}`);
        });
      }
      // 只有当响应是OK时，才尝试解析JSON
      return response.json();
    })
    .then((data) => {
      const tableBody = document
        .getElementById("data-table")
        .getElementsByTagName("tbody")[0];
      tableBody.innerHTML = ""; // 清空现有表格内容

      data.forEach((row) => {
        const tr = document.createElement("tr");

        // 创建account单元格
        const accountCell = document.createElement("td");
        accountCell.innerText = row.account;
        tr.appendChild(accountCell);

        // 创建name单元格
        const nameCell = document.createElement("td");
        nameCell.innerText = row.name;
        tr.appendChild(nameCell);

        // 创建psd单元格
        const psdCell = document.createElement("td");
        psdCell.innerText = row.password;
        psdCell.addEventListener("click", () =>
          makeCellEditable(accountCell, psdCell)
        ); // 点击变为编辑状态
        tr.appendChild(psdCell);

        // 创建phone单元格
        const phoneCell = document.createElement("td");
        phoneCell.innerText = row.phone;
        tr.appendChild(phoneCell);

        // 创建address单元格
        const addressCell = document.createElement("td");
        addressCell.innerText = row.address;
        tr.appendChild(addressCell);

        // 创建authority单元格
        const authorityCell = document.createElement("td");
        authorityCell.innerText = row.authority;
        authorityCell.addEventListener("click", () =>
          makeCellEditable1(accountCell, authorityCell)
        ); // 点击变为编辑状态
        tr.appendChild(authorityCell);

        fetch("image", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ head: row.head }), // 发送包含图片URL的JSON对象
        })
          .then((response) => response.blob()) // 将响应解析为Blob对象
          .then((blob) => {
            // 将Blob对象转换为Image URL
            const imgURL = URL.createObjectURL(blob);

            const imageCell = document.createElement("td");
            const imageInput = document.createElement("img");
            imageInput.src = imgURL;
            imageCell.appendChild(imageInput);
            tr.appendChild(imageCell);
          })
          .catch((error) => {
            alert("加载图片出错:", error);
          });

        // 不可编辑的其他数据单元格...
        const deleteCell = document.createElement("td");
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "删除";
        deleteButton.onclick = function () {
          deleteItem(row.account);
        };
        deleteCell.appendChild(deleteButton);
        tr.appendChild(deleteCell);

        const uploadCell = document.createElement("td");
        const uploadButton = document.createElement("button");
        uploadButton.textContent = "上传图片";
        uploadButton.onclick = function () {
          uploadFile(accountCell);
        };
        uploadCell.appendChild(uploadButton);
        tr.appendChild(uploadCell);

        tableBody.appendChild(tr);
      });
      const tr = document.createElement("tr");
      let a = "";
      let n = "";
      let p = "";
      let ph = "";
      let ad = "";
      let au = "";

      // 创建account单元格
      const accountCell = document.createElement("td");
      const accountInput = document.createElement("input");
      accountInput.innerText = "";
      accountInput.onblur = function (event) {
        a = event.target.value;
      };
      accountCell.appendChild(accountInput);
      tr.appendChild(accountCell);

      // 创建name单元格
      const nameCell = document.createElement("td");
      const nameInput = document.createElement("input");
      nameInput.innerText = "";
      nameInput.onblur = function (event) {
        n = event.target.value;
      };
      nameCell.appendChild(nameInput);
      tr.appendChild(nameCell);

      // 创建psd单元格
      const psdCell = document.createElement("td");
      const psdInput = document.createElement("input");
      psdInput.innerText = "";
      psdInput.onblur = function (event) {
        p = event.target.value;
      };
      psdCell.appendChild(psdInput);
      tr.appendChild(psdCell);

      // 创建phone单元格
      const phoneCell = document.createElement("td");
      const phoneInput = document.createElement("input");
      phoneInput.innerText = "";
      phoneInput.onblur = function (event) {
        ph = event.target.value;
      };
      phoneCell.appendChild(phoneInput);
      tr.appendChild(phoneCell);

      // 创建address单元格
      const addressCell = document.createElement("td");
      const addressInput = document.createElement("input");
      addressInput.innerText = "";
      addressInput.onblur = function (event) {
        ad = event.target.value;
      };
      addressCell.appendChild(addressInput);
      tr.appendChild(addressCell);

      // 创建authority单元格
      const authorityCell = document.createElement("td");
      const authorityInput = document.createElement("input");
      authorityInput.innerText = "";
      authorityInput.onblur = function (event) {
        au = event.target.value;
      };
      authorityCell.appendChild(authorityInput);
      tr.appendChild(authorityCell);

      // 不可编辑的其他数据单元格...
      const insertCell = document.createElement("td");
      const insertButton = document.createElement("button");
      insertButton.textContent = "添加";
      insertButton.onclick = function () {
        insert_user(a, n, p, ph, ad, au);
      };
      insertCell.appendChild(insertButton);
      tr.appendChild(insertCell);

      const nullCell = document.createElement("td");
      const nullButton = document.createElement("button");
      nullButton.textContent = "随便点";
      nullCell.appendChild(nullButton);
      tr.appendChild(nullCell);

      tableBody.appendChild(tr);
    })
    .catch((err) => alert("Error fetching data:", err));
  if (e === "account") a = b === "true" ? "false" : "true";
  else p = b === "true" ? "false" : "true";
}
sort_account.onclick = function () {
  sort(sort_account.innerText, a);
};

function uploadFile(cell) {
  const fileName = cell.textContent || cell.innerText;
  if (!fileName) {
    alert("请输入文件名称");
    return;
  }

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
    formData.append("fileName", fileName);

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
