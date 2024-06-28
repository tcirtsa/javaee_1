let account = sessionStorage.getItem("account");
window.onload = fetchData;
function fetchData() {
  fetch("get_all_apparatus", {
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
      // 使用 response.text() 来读取成功的响应
      return response.json();
    })
    .then((data) => {
      const tableBody = document
        .getElementById("data-table")
        .getElementsByTagName("tbody")[0];
      tableBody.innerHTML = ""; // 清空现有表格内容
      data.forEach((row) => {
        const tr = document.createElement("tr");

        //创建id单元格
        const idCell = document.createElement("td");
        idCell.innerText = row.id;
        tr.appendChild(idCell);

        //创建name单元格
        const nameCell = document.createElement("td");
        nameCell.innerText = row.name;
        tr.appendChild(nameCell);

        //创建type单元格
        const typeCell = document.createElement("td");
        typeCell.innerText = row.type;
        tr.appendChild(typeCell);

        //创建phone单元格
        const phoneCell = document.createElement("td");
        phoneCell.innerText = row.phone;
        tr.appendChild(phoneCell);

        //创建address单元格
        const addressCell = document.createElement("td");
        addressCell.innerText = row.address;
        tr.appendChild(addressCell);

        //创建description单元格
        const descriptionCell = document.createElement("td");
        descriptionCell.innerText = row.description;
        tr.appendChild(descriptionCell);

        fetch("image_apparatus", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ image: row.image }), // 发送包含图片URL的JSON对象
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
        //创建租借按钮单元格
        const rentCell = document.createElement("td");
        const rentButton = document.createElement("button");
        if (row.status == 0) {
          rentButton.textContent = "租借";
        } else if (row.status == 2) {
          rentButton.textContent = "维修中";
        } else if (row.status == 1) {
          rentButton.textContent = "出租中";
        }
        rentButton.addEventListener("click", function () {
          rent(this);
        });
        rentCell.appendChild(rentButton);
        tr.appendChild(rentCell);

        //创建归还按钮单元格
        const returnCell = document.createElement("td");
        const returnButton = document.createElement("button");
        returnButton.textContent = "归还";
        returnButton.addEventListener("click", function () {
          // 在此处添加归还逻辑
        });
        returnCell.appendChild(returnButton);
        tr.appendChild(returnCell);

        tableBody.appendChild(tr);
      });
    })
    .catch((error) => {
      alert("请求错误:", error);
    });
}

function rent(button) {
  // 在此处添加租借逻辑
  let textContent = button.textContent;
  if (textContent == "租借") {
    fetch("rent_apparatus", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ id: button.id, who: account }),
    })
      .then((response) => response.text())
      .then((text) => {
        alert(text);
        button.textContent = "出租中";
        fetchData();
      })
      .catch((error) => {
        alert("请求错误:", error);
      });
  } else if (textContent == "维修中") {
    alert("该器材正在维修中,别点了捏!!!");
  } else if (textContent == "出租中") {
    alert("该器材正在出租中,别点了捏!!!");
  }
}

function return_apparatus(id, button) {
  // 在此处添加归还逻辑
  if (button.textContent == "出租中") {
    fetch("return_apparatus", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ id: id, who: account }),
    })
      .then((response) => response.text())
      .then((text) => {
        alert(text);
        if (text == "归还成功") {
          button.textContent = "租借";
        } else if (text == "归还失败") {
          alert("不是你借的，你还什么？");
        }
        fetchData();
      })
      .catch((error) => {
        alert("请求错误:", error);
      });
  }
}
