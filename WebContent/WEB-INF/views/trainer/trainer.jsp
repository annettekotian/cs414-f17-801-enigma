<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/gymSystem.css">
<link rel="stylesheet" type="text/css" href="../css/trainer.css">
<script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
<title>Insert title here</title>
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
	<a href="#customers" onclick="focusCustomers()">Customers</a>
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
	<table>
		<tr>
			<td>
				
			</td>
			<td>
				<table>
					<tr><td>Customers</td></tr>
					<tr>
						<td>
							<select id="customerList" size=10>
								<option>Jane</option>
								<option>John</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><input type="button" value="New Customer" onclick="displayForm()"/></td>
						<td><input type="button" value="Modify Customer"/></td>
						<td><input type="button" value="Delete Customer"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
	<!-- Popup new customer form -->
	<div id="newCustomerFormBackground">
		<div id="newCustomerForm">
			<form method="post">
				<input id="username" name="username" type="text" />
				<input id="firstName" name="firstName" type="text" />
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

</script>

</body>
</html>