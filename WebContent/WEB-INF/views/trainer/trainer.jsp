<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/css/gymSystem.css">
<link rel="stylesheet" type="text/css" href="/css/trainer.css">
<script src="/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<title>Trainer UI</title>
</head>
<body>
<div style="width:100%; height:40px; color:white;background:lightskyblue; text-align:center">
	<ul id="topHeader">
	<li id="weclcomeLi">Welcome User!</li>
	
	<li id="logoutLink"><a href="/logout">Logout</a></li>
	</ul>
</div>

<!-- <div class="menuBar">
	<a class="active" href="#home" onclick="focusHome()">Home</a>
	<a href="#customerTable" onclick="populateCustomers()">Customers</a>
	<a href="#workouts">Workouts</a>
	<a href="#exercises">Exercises</a>
</div> -->

<div id="menuBarDiv">
	<ul id="menuBar">
		<li id="homeLi" class="menuLi">Home</li>
		<li id="customerLi" class="menuLi">Customers</li>
		<li id="workoutsLi">Workouts</li>
		<li id="exerciseLi">Exercises</li>
	</ul>
	</div>

<button id="trainerSearchCustomerButton" class="trainerSearchCustomers">Search</button>
<input id = "trainerSearchCustomerInput" class="trainerSearchCustomers" type="text" placeholder = "enter name, address etc."/>
<div id="home">
	<table>
		<tr>
			<th>Personal Information</th>
		</tr>
		<tr>
			<td>First Name:</td>
			<td id="homeFName"></td>
		</tr>
		<tr>
			<td>Last Name:</td>
			<td id="homeLName"></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td id="homeEmail"></td>
		</tr>
		<tr>
			<td>Phone Number:</td>
			<td id="homePhone"></td>
		</tr>
		<tr>
			<td>Health Insurance:</td>
			<td id="homeInsurance"></td>
		</tr>
		<tr>
			<td>Address:</td>
			<td id="homeAddress"></td>
		</tr>
	</table>
</div>

	<div id="customerTable">
		<table>
			<tr class="tableHeader">
				<th>Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Address</th>
				<th>Email</th>
				<th>Phone No</th>
				<th>Health Insurance</th>
				<th>Membership</th>

			</tr>

		</table>
	</div>
	
	
	<script src="../js/trainer.js">
</script>
<script>
var trainerData = <%=request.getAttribute("trainer")%>
focusHome(trainerData);
</script>
</body>
</html>