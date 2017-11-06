<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/css/gymSystem.css">
<link rel="stylesheet" type="text/css" href="/css/trainer.css">
<link rel="stylesheet" type="text/css" href="/css/jquery.modal.min.css">
<script src="/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="/js/jquery.modal.min.js"></script>
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


<button id="addExercise" class="addButtons" onclick="addExerciseModal()">Add Exercise</button>
<div id="exerciseResults">
	<table id="exerciseTable">
		<tr class="tableHeader">
			<th>Exercise Name</th>
			<th>Duration</th>
			<th>Sets</th>
			<th>Machine</th>
		</tr>
	</table>
</div>

<div id="exerciseModal" class="modal">
	<h3 id="addExerciseHeader">Add Exercise</h3>
	<h3 id="modifyExerciseHeader">Modify Exercise</h3>
	<form id="exerciseForm">
		<label>Name</label><br>
		<input id="exerciseName" type="text"><br>
		<label>Duration Hours</label><br>
		<input id="exerciseDurationHours" type="number"><br>
		<label>Duration Minutes</label><br>
		<input id="exerciseDurationMinutes" type="number"><br>
		<label>Duration Seconds</label><br>
		<input id="exerciseDurationSeconds" type="number"><br>
		<label>Sets</label><br>
		<p id="exerciseSetsDisplay">Current Sets: None<p>
		<input id="exercisesetRepetition" type="number"><br>
		<button id="addExerciseSet" class="addButtons" onclick="addExerciseSet()">Add Set</button><br>
		<label>Machine</label><br>
		<input id="exerciseMachine" type="number"><br>
	</form>
	<button id="addExercise" class="addButtons" onclick="addExercise()">Submit</button><br>
</div>


<script src="/js/trainer/trainer.js" type="text/javascript"></script>
<script src="/js/trainer/exercise.js" type="text/javascript"></script>
<script>
var trainerData = <%=request.getAttribute("trainer")%>
focusHome(trainerData);
</script>
</body>
</html>