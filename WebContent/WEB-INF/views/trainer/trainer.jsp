<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/gymSystem.css">
<link rel="stylesheet" type="text/css" href="../css/trainer.css">
<script src="../js/jquery-3.2.1.min.js" type="text/javascript"></script>
<title>Trainer UI</title>
</head>
<body>
<div style="width:100%; height:40px; color:white;background:lightskyblue; text-align:center">
	<ul id="topHeader">
	<li id="weclcomeLi">Welcome User!</li>
	
	<li id="logoutLink">Logout</li>
	</ul>
</div>

<div class="menuBar">
	<a class="active" href="#home" onclick="focusHome()">Home</a>
	<a href="#customers" onclick="populateCustomers()">Customers</a>
	<a href="#workouts">Workouts</a>
	<a href="#exercises">Exercises</a>
</div>

<div id="home">
	<table>
		<tr>
			<td>Personal Information</td>
		</tr>
		<tr>
			<td>First Name:</td>
			<td>John</td>
		</tr>
		<tr>
			<td>Last Name:</td>
			<td>Doe</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td>johndoe@gmail.com</td>
		</tr>
		<tr>
			<td>Phone Number:</td>
			<td>555-555-5555</td>
		</tr>
		<tr>
			<td>Health Insurance:</td>
			<td>Cigna</td>
		</tr>
	</table>
</div>

<div id="customers">
	<div id="dynamicCustomersTable">
	</div>
	<input type="button" value="New Customer" onclick="displayNewCustomerForm()" />
	
	<!-- Popup Customer Form -->
	<div id="customerFormBackground">
		<div id="customerForm">
			<p>Hello new customer</p>
			<input type="button" value="Submit" />
			<input type="button" value="Close" onclick="closeNewCustomerForm()" />
		</div>
	</div>
</div>

<script>
function focusHome() {
	document.getElementById("home").style.display = "block";
	document.getElementById("customers").style.display = "none";
}

function focusCustomers() {
	document.getElementById("home").style.display = "none";
	document.getElementById("customers").style.display = "block";
}

function populateCustomers() {
	var currentUrl = window.location.pathname
	$.ajax({
		url: "/gym-system/trainer/ui",
		method: "POST",
		data: {
			type: "getCustomers"
		},
		success: function(data, textStatus, jqXHR) {
			if(data.rc == 0) {
				generateCustomersDisplay(data.customers);
			}
			else {
				alert(data.msg);
			}
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
}

function generateCustomersDisplay(customers) {
	if(document.getElementById("customersTable")) {
		document.getElementById("customersTable").remove();
	}
	
	var customerTable = document.createElement("table");
	customerTable.setAttribute("id", "customersTable");
	var tr = document.createElement("tr")
	tr.appendChild(document.createElement("td").appendChild(document.createTextNode("Customer ID")));
	tr.appendChild(document.createElement("td").appendChild(document.createTextNode("First Name")));
	tr.appendChild(document.createElement("td").appendChild(document.createTextNode("Last Name")));
	
	customerTable.appendChild(tr);
	document.getElementById("dynamicCustomersTable").appendChild(customerTable);
	
	focusCustomers();
}

function displayNewCustomerForm() {
	document.getElementById("customerFormBackground").style.display = "block";
}

function closeNewCustomerForm() {
	document.getElementById("customerFormBackground").style.display = "none";
}


</script>

</body>
</html>