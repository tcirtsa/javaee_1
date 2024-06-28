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
      document.getElementById("username").innerText =
        "你好 维修员 " + data.name;
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
  fetchData1();
};
function fetchData1() {
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
        // 创建repair单元格
        const repairCell = document.createElement("td");
        const repairButton = document.createElement("button");
        if (row.status == 0 || row.status == 1) {
          repairButton.textContent = "正常";
        } else {
          repairButton.textContent = "维修中";
        }
        repairButton.addEventListener("click", () =>
          repair(row.id, repairButton)
        );
        repairCell.appendChild(repairButton);
        tr.appendChild(repairCell);

        tableBody.appendChild(tr);
      });
    })
    .catch((error) => {
      alert("请求错误:", error);
    });
}

function repair(id, button) {
  let content = button.textContent;
  if (content == "正常") {
    fetch("ToRepair_apparatus", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ id: id, who: account }),
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
        alert(data);
        fetchData1();
      })
      .catch((error) => {
        alert("请求错误:", error);
      });
    alert("确定坏了吧？那就给我修！！！没坏的话给我变回去啊！");
  } else if (content == "维修中") {
    fetch("repair_apparatus", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ id: id }),
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
        alert(data);
        fetchData1();
      })
      .catch((error) => {
        alert("请求错误:", error);
      });
  }
}
