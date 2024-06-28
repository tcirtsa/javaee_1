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

        //创建who单元格
        const whoCell = document.createElement("td");
        whoCell.innerText = row.who;
        tr.appendChild(whoCell);

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
        repairButton.textContent = "删除";
        repairButton.addEventListener("click", () => delete_apparatus(row.id));
        repairCell.appendChild(repairButton);
        tr.appendChild(repairCell);

        //创建上传图片单元格
        const uploadCell = document.createElement("td");
        const uploadButton = document.createElement("button");
        uploadButton.textContent = "上传图片";
        uploadButton.onclick = function () {
          uploadFile(idCell);
        };
        uploadCell.appendChild(uploadButton);
        tr.appendChild(uploadCell);

        tableBody.appendChild(tr);
      });
      const tr = document.createElement("tr");
      let input_id = "";
      let input_name = "";
      let input_type = "";
      let input_phone = "";
      let input_who = "";
      let input_address = "";
      let input_description = "";

      //创建id单元格
      const idCell = document.createElement("td");
      const idInput = document.createElement("input");
      idInput.innerText = "";
      idInput.onblur = function (event) {
        input_id = event.target.value;
      };
      idCell.appendChild(idInput);
      tr.appendChild(idCell);

      //创建name单元格
      const nameCell = document.createElement("td");
      const nameInput = document.createElement("input");
      nameInput.innerText = "";
      nameInput.onblur = function (event) {
        input_name = event.target.value;
      };
      nameCell.appendChild(nameInput);
      tr.appendChild(nameCell);

      //创建type单元格
      const typeCell = document.createElement("td");
      const typeInput = document.createElement("input");
      typeInput.innerText = "";
      typeInput.onblur = function (event) {
        input_type = event.target.value;
      };
      typeCell.appendChild(typeInput);
      tr.appendChild(typeCell);

      //创建phone单元格
      const phoneCell = document.createElement("td");
      const phoneInput = document.createElement("input");
      phoneInput.innerText = "";
      phoneInput.onblur = function (event) {
        input_phone = event.target.value;
      };
      phoneCell.appendChild(phoneInput);
      tr.appendChild(phoneCell);

      //创建who单元格
      const whoCell = document.createElement("td");
      const whoInput = document.createElement("input");
      whoInput.innerText = "";
      whoInput.onblur = function (event) {
        input_who = event.target.value;
      };
      whoCell.appendChild(whoInput);
      tr.appendChild(whoCell);

      //创建address单元格
      const addressCell = document.createElement("td");
      const addressInput = document.createElement("input");
      addressInput.innerText = "";
      addressInput.onblur = function (event) {
        input_address = event.target.value;
      };
      addressCell.appendChild(addressInput);
      tr.appendChild(addressCell);

      //创建description单元格
      const descriptionCell = document.createElement("td");
      const descriptionInput = document.createElement("input");
      descriptionInput.innerText = "";
      descriptionInput.onblur = function (event) {
        input_description = event.target.value;
      };
      descriptionCell.appendChild(descriptionInput);
      tr.appendChild(descriptionCell);

      const insertCell = document.createElement("td");
      const insertButton = document.createElement("button");
      insertButton.textContent = "添加";
      insertButton.addEventListener("click", () =>
        insert_apparatus(
          input_id,
          input_name,
          input_type,
          input_phone,
          input_who,
          input_address,
          input_description
        )
      );
      insertCell.appendChild(insertButton);
      tr.appendChild(insertCell);

      tableBody.appendChild(tr);
    })
    .catch((error) => {
      alert("请求错误:", error);
    });
}

function delete_apparatus(id) {
  fetch("delete_apparatus", {
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
      fetchData();
    })
    .catch((error) => {
      alert("请求错误:", error);
    });
}

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
  fileInput.addEventListener("change_image", function (event) {
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

function insert_apparatus(id, name, type, phone, who, address, description) {
  fetch("insert_apparatus", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: id,
      name: name,
      type: type,
      phone: phone,
      who: who,
      address: address,
      description: description,
    }),
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
      fetchData();
    })
    .catch((error) => {
      alert("请求错误:", error);
    });
}
