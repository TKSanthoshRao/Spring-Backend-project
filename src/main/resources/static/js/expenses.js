function fetchExpences(){
    return fetch("/api/expenses")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch expenses");
            }
            return response.json();
        });
}

function loadExpenses(method) {
     var x = method();
        x.then(data => {
            const tableBody = document.querySelector("#expenseTable tbody");
            tableBody.innerHTML = ""; // clear old rows

            if (data.length === 0) {
                const row = document.createElement("tr");
                row.innerHTML = `<td colspan="3">No expenses found</td>`;
                tableBody.appendChild(row);
                return;
            }

            data.forEach(expense => {
                const row = document.createElement("tr");
                const button = document.createElement("button");
                button.type = "button";
                button.innerText = "Delete";
                button.className = "delete-btn";

                button.onclick = function () {
                    deleteExpense(expense.id);
                };
                row.innerHTML = `
                    <td>${expense.category}</td>
                    <td>${expense.amount} Rs</td>
                    <td>${expense.date}</td>
                    <td></td>  
                `;
                row.children[3].appendChild(button);
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error fetching expenses:", error));
}

window.onload = function () {
    loadExpenses(fetchExpences);
    loadHabitTracker();
    fetchALLUsers();
};



function submitExpense() {
    const category = document.getElementById("title").value;
    const amount = parseFloat(document.getElementById("amount").value);
    const date = document.getElementById("date").value;

    if (!category || !amount || amount <= 0 || !date) {
        alert("Please enter valid expense details");
        return;
    }

    const expense = { category, amount, date };

    fetch("/api/expense", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(expense)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to save expense");
            }
            return response.json();
        })
        .then(data => {
            alert("Expense added successfully");

            // clear form
            document.getElementById("title").value = "";
            document.getElementById("amount").value = "";
            document.getElementById("date").value = "";

            // reload table
            loadExpenses(fetchExpences);
        })
        .catch(error => {
            console.error(error);
            alert("Backend error while saving expense");
        });
}

function SubtractFromSalary() {
    const salary = parseFloat(document.getElementById("salary").value);
    if (isNaN(salary) || salary <= 0) {
        alert("Please enter a valid salary");
        return;
    }

    fetchExpences()
        .then(data => {
            const totalExpense = data.reduce((sum, expense) => {
                return sum + expense.amount;
            }, 0);

            const remainingSalary = salary - totalExpense;

            document.getElementById("remainingSalary").innerText =
                `Remaining Salary: ${remainingSalary} Rs`;
        })
        .catch(error => {
            console.error(error);
            alert("Some error while calculating your current salary");
        });
}

function deleteExpense(id){
    if(!confirm("Are You sure")) return;
    fetch(`/api/expense/${id}`,{
        method : "DELETE",
        headers: { "Content-Type": "application/json" }
    }).then(data => {
        loadExpenses(fetchExpences);
    }).catch(error => {
        alert(`Some Error Occured in deleting`);
    })
}


function filterByDate(){
    var Startdate = document.getElementById("Startdate").value;
    alert(Startdate);
    var EndDate = document.getElementById("Enddate").value;
    return fetch(`/api/expense/filter?Startdate=${Startdate}&EndDate=${EndDate}`)
        .then(response => {
        if (!response.ok) {
            throw new Error("Failed to fetch expenses");
        }
        return response.json();
    }).catch(error => alert("this error occured "+error));
}


function FetchFilteredExpenses(){
    alert("Entered den");
    loadExpenses(filterByDate);
}





function loadHabitTracker() {
    const container = document.getElementById("habitTracker");
    container.innerHTML = "";

    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth();

    const daysInMonth = new Date(year, month + 1, 0).getDate();

    for (let day = 1; day <= daysInMonth; day++) {
        const div = document.createElement("div");
        div.className = "habit-day";
        div.innerText = day;

        div.onclick = () => toggleHabit(div);

        container.appendChild(div);
    }
}
function toggleHabit(element) {
    if (element.classList.contains("green")) {
        element.classList.remove("green");
        element.classList.add("red");
    } else if (element.classList.contains("red")) {
        element.classList.remove("red");
    } else {
        element.classList.add("green");
    }
}


function uploadFile(){
    var fileInput = document.getElementById("file");
    if (fileInput.files.length === 0) {
        alert("Please select a file");
        return;
    }

}


function SignupUser()
{
    var username = document.getElementById("username").value;
    var password  = document.getElementById("password").value;

    const user = {username,password};
    alert(JSON.stringify(user));

    fetch("/user/signup",{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body: JSON.stringify(user)
    }).then(response => {
            if(!response.ok){
                alert(JSON.stringify(response));
                throw new Error("Failed to post signup request");
            }
            alert("Success!! signup request successful");
    }).catch(error => alert("arrey yaar error aya "+error));
}


function fetchALLUsers(){
    fetch("/admin/users",{
        method : "GET",
        headers : {"CONENT-TYPE" : "application/json"}
    }).then(response => {
        if(!response.ok){
            throw new Error("Failed to fetch user's data from API");
        }
        return response.json();
    }).then(users => {
        const tbody = document.querySelector("#usersTable tbody");
        tbody.innerHTML = ""; // clear existing rows

        users.forEach(user => {
            const row = document.createElement("tr");

            // username cell
            const usernameTd = document.createElement("td");
            usernameTd.innerText = user.username;

            // password cell
            const passwordTd = document.createElement("td");
            passwordTd.innerText = user.password;

            const RolesTd = document.createElement("td");
            var str = "";
            for (let i = 0; i < user.roles.length; i++) {
                str+=user.roles[i].name+" ";
            }
            RolesTd.innerText = str;
            // action cell
            const actionTd = document.createElement("td");
            const deleteBtn = document.createElement("button");
            deleteBtn.innerText = "Delete";
            deleteBtn.onclick = () => deleteUser(user.id);

            actionTd.appendChild(deleteBtn);

            row.appendChild(usernameTd);
            row.appendChild(passwordTd);
            row.appendChild(RolesTd);
            row.appendChild(actionTd);

            tbody.appendChild(row);
        });
    }).catch(error => alert("arrey tere paas admin role hi nhi hai"+error));

}

function deleteUser(userId) {
    if (!confirm("Are you sure you want to delete this user?")) return;

    fetch(`/admin/user/${userId}`, {
        method: "DELETE"
    })
        .then(res => {
            if (!res.ok) throw new Error("Delete failed");
            fetchALLUsers();
        })
        .catch(err => alert(err.message));
}
