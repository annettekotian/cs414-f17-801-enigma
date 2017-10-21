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
			<form method="post" action="Login">
				<table>
					<tr><td><b>Customer Form</b></td></tr>
					<tr>
						<td>First Name</td>
						<td><input id="firstname" name="firstname" type="text"/></td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td><input id="lastname" name="lastname" type="text"/></td>
					</tr>
					<tr>
						<td>Phone Number</td>
						<td><input id="phone" name="phone" type="text"/></td>
					</tr>
					<tr>
						<td>Email Address</td>
						<td><input id="email" name="email" type="text"/></td>
					</tr>
					<tr>
						<td>Health Insurance</td>
						<td>
							<select id="healthInsurances" size="5" onchange="inputNewHealthInsurance()"></select>
							<input id="newHealthInsurances" name="newHealthInsurances" type="text" disabled/></td>
						</td>
					</tr>
					<tr>
						<td>Membership Status</td>
						<td>
							<select id="membershipStatus" size="2"></select>
						</td>
					</tr>
					<tr>
						<td><input type="button" value="Submit" onclick="submitNewCustomerForm()" /></td>
						<td><input type="button" value="Close" onclick="closeNewCustomerForm()" /></td>
					</tr>
				</table>
			</form>
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
				generateCustomersDisplay(JSON.parse(data.customers));
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
	
	for(var i=0; i<customers.length; i++) {
		tr = document.createElement("tr")
		tr.appendChild(document.createElement("td").appendChild(document.createTextNode(customers[i].id)));
		tr.appendChild(document.createElement("td").appendChild(document.createTextNode(customers[i].personalInformation.firstName)));
		tr.appendChild(document.createElement("td").appendChild(document.createTextNode(customers[i].personalInformation.lastName)));
		customerTable.appendChild(tr);
	}
	
	customerTable.appendChild(tr);
	document.getElementById("dynamicCustomersTable").appendChild(customerTable);
	
	focusCustomers();
}


function inputNewHealthInsurance() {
	var option = document.getElementById("healthInsurances");
	if(option.selectedIndex == 0) {
		document.getElementById("newHealthInsurances").disabled = false;
	}
	else {
		document.getElementById("newHealthInsurances").disabled = true;
		document.getElementById("newHealthInsurances").value = "";
	}
}



function submitNewCustomerForm() {
	var firstName = document.getElementById("firstname").value;
	var lastName = document.getElementById("lastname").value;
	var phoneNumber = document.getElementById("phone").value;
	var email = document.getElementById("email").value;
	
	var healthInsurance = null;
	var healthInsuranceList = document.getElementById("healthInsurances");
	if(healthInsuranceList.selectedIndex == 0) {
		healthInsurance = document.getElementById("newHealthInsurances").value;
	}
	else {
		healthInsurance = healthInsuranceList.options[healthInsuranceList.selectedIndex].value;
	}
	
	var membershipStatusList = document.getElementById("membershipStatus");
	var membershipStatus = membershipStatusList.options[membershipStatusList.selectedIndex].value;
	
	var healthInsurances = null;
	$.ajax({
		url: "/gym-system/trainer/ui",
		method: "POST",
		data: {
			type: "createNewCustomer",
			firstName: firstName,
			lastName: lastName,
			phoneNumber: phoneNumber,
			email: email,
			healthInsurance: healthInsurance,
			membershipStatus: membershipStatus
		},
		success: function(data, textStatus, jqXHR) {
		},
		error: function(exception) {
			alert("Exception" + exception);
		}
	});
	
	closeNewCustomerForm();
}


</script>

</body>
</html>