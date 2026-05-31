function sendDataToBackend() {
    
    // get form values
    var empId = document.getElementById("empId").value;
    var empName = document.getElementById("empName").value;
    var titleInput = document.getElementById("taskTitle").value;
    var descInput = document.getElementById("taskDesc").value;
    var statusInput = document.getElementById("taskStatus").value;
    var messagePara = document.getElementById("resultMessage");

    // validation
    if (empId == "" || empName == "" || titleInput == "" || descInput == "" || statusInput == "") {
        messagePara.style.color = "red";
        messagePara.innerText = "Error: Please fill all fields!";
        return;
    }

    // append query parameters
    var urlParams = "employeeId=" + encodeURIComponent(empId) + 
                    "&employeeName=" + encodeURIComponent(empName) + 
                    "&title=" + encodeURIComponent(titleInput) + 
                    "&description=" + encodeURIComponent(descInput) +
                    "&status=" + encodeURIComponent(statusInput);

    // send requests to servlet
    fetch('http://localhost:8080/TaskTracker/api/tasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: urlParams
    })
    .then(function(response) {
        return response.json();
    })
    .then(function(data) {
        if (data.status == "success") {
            messagePara.style.color = "green";
            messagePara.innerText = data.message;
            
            // clear form fields
            document.getElementById("empId").value = "";
            document.getElementById("empName").value = "";
            document.getElementById("taskTitle").value = "";
            document.getElementById("taskDesc").value = "";
            document.getElementById("taskStatus").value = "";
        } else {
            messagePara.style.color = "red";
            messagePara.innerText = "Server Error: " + data.message;
        }
    })
    .catch(function(error) {
        messagePara.style.color = "red";
        messagePara.innerText = "Network Error!";
    });
}