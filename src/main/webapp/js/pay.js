function fetchData3() {
  fetch("get_all_pay", {
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
        // 创建id单元格
        const idCell = document.createElement("td");
        idCell.innerText = row.id;
        tr.appendChild(idCell);
        // 创建description单元格
        const descriptionCell = document.createElement("td");
        descriptionCell.innerText = row.description;
        tr.appendChild(descriptionCell);
        // 创建who单元格
        const whoCell = document.createElement("td");
        whoCell.innerText = row.who;
        tr.appendChild(whoCell);

        //创建pay单元格
        const payCell = document.createElement("td");
        payCell.innerText = row.pay;
        tr.appendChild(payCell);

        //创建删除单元格
        const deleteCell = document.createElement("td");
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "删除";
        deleteButton.addEventListener("click", () => {
          fetch("delete_pay", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ id: row.id }),
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
              // 使用 response.text() 来读取成功的响应
              return response.json();
            })
            .then((data) => {
              fetchData3();
            })
            .catch((error) => {
              console.error(error);
            });
        });
        deleteCell.appendChild(deleteButton);
        tr.appendChild(deleteCell);

        tableBody.appendChild(tr);
      });
    })
    .catch((error) => {
      console.error(error);
    });
}
document.getElementById("refreshButton").addEventListener("click", fetchData3);
window.onload = fetchData3;
