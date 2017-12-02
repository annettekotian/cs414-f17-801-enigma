<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enigma</title>

<link rel="stylesheet" type="text/css" href="/css/gymSystem.css">
<link rel="stylesheet" type="text/css" href="/css/customer.css">
<link rel="stylesheet" type="text/css" href="/css/jquery.modal.min.css">
<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/jquery.modal.min.js"></script>
<script src="/js/globalVariables.js"></script>

</head>
<body>
	<div
		style="width: 100%; height: 40px; color: white; background: lightskyblue; text-align: center">
		<ul id="topHeader">
			<li id="weclcomeLi">Welcome User!</li>

			<li id="logoutLink"><a href="/logout">Logout</a></li>
		</ul>
	</div>
	<div id="customerMenuBarDiv">
		<ul id="customerMenuBar">
			<li id="customerHomeLi" class="menuLi">Home</li>
			<li id="customerWorkoutLi">Workouts</li>
		</ul>
	</div>
	<button id="addFeedback" class="workoutButtons">Add Feedback</button>
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
			<tr>
				<td>Membership status:</td>
				<td id="membershipStatus"></td>
			</tr>
		</table>
	</div>
	
	<div id = "workoutResults">
		<table>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Exercises</th>
				<th>Feedback</th>
			</tr>
			
			
		</table>
	</div>
	
	<div id="workoutExercisesModal" class="modal">
		<h3>Workout Exercises</h3>
		<div id="workoutExercises">
			<table>
				<th>Sequence</th>
				<th>id</th>
				<th>Name</th>
			</table>
		</div>

	</div>
</body>
<script src="/js/customer.js"></script>
<script type="text/javascript">
	var customerData =
<%=request.getAttribute("customerData")%>
	focusHome();
</script>
</html>