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
<script src="/js/globalVariables.js"></script>
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
<button id="createWorkoutButton" class="workoutButtons">Create Workout</button>
<button id="editWorkoutButton" class="workoutButtons" disabled>Edit Workout</button>

<div id="workoutResults">
	<table >
		<tr class="tableHeader">
				<th>Id</th>
				<th>Workout Name</th>
				<th>Exercises</th>
		</tr>
	</table>
</div>

<div id="createWorkoutModal" class="modal">
	<h3>Create Workout</h3>
	<label>Workout Name</label><br/>
	<input id="workoutName" type="text" placeholder="Enter workout name"/> <br>
	<label>Select Exercise</label><br/>
	<select id="workoutExerciseSelect">
	</select> <br/>
	<button id="addExerciseToWorkout">Add exercise</button><br/>
	<label>List of exercises added</label><br/>
	<ol id="workoutExerciseList">
	</ol>
	<button id="submitWorkout">Create</button>
</div>


<div id="editWorkoutModal" class="modal">
	<h3>Edit Workout</h3>
	<label>Workout Name</label><br/>
	<input id="editWorkoutName" type="text" placeholder="Enter workout name"/> <br>
	<label>Sequence of exercises</label><br/>
	<table id="editWorkoutExercises">
		<th></th>
		<th>Sequence</th>
		<th>id</th>
		<th>Name</th>
	</table>
	<button id="submitWorkoutChanges">Submit Changes</button>
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

<button id="addExercise" class="addButtons" onclick="addExerciseModal()">Add Exercise</button>
<button id="modifyExercise" class="addButtons" onclick="modifyExerciseModal()" disabled>Modify Exercise</button>
<button id="deleteExercise" class="addButtons" onclick="deleteExercise()" disabled>Delete Exercise</button>
<input id="searchExerciseBox" type="text" placeholder="Search Exercises">
<button id="searchExercise" class="addButtons" onclick="searchExercise()">Search</button>
<button id="resetExercise" class="addButtons" onclick="populateAllExercises()">Reset</button>
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
	<label>Name</label><br>
	<input id="exerciseName" type="text" placeholder="Name"><br>
	<label>Duration Hours</label><br>
	<input id="exerciseDurationHours" type="number" placeholder="Hours"><br>
	<label>Duration Minutes</label><br>
	<input id="exerciseDurationMinutes" type="number" placeholder="Minutes"><br>
	<label>Duration Seconds</label><br>
	<input id="exerciseDurationSeconds" type="number" placeholder="Seconds"><br>
	<label>Sets</label><br>
	<ul id="exerciseSetList"></ul>
	<input id="exerciseSetRepetition" type="number" placeholder="Repetitions"><br>
	<button type="button" id="addExerciseSet" class="addButtons" onclick="addExerciseSet()" >Add Set</button>
	<button type="button" id="deleteExerciseSet" class="addButtons" onclick="deleteExerciseSet()" >Delete Set</button><br>
	<label>Machine</label><br>
	<select id="exerciseMachine"></select><br>
	<button id="submitAddExercise" class="addButtons" onclick="addExercise()">Submit</button><br>
	<button id="submitModifyExercise" class="addButtons" onclick="modifyExercise()">Submit</button><br>
</div>


<script src="/js/trainer/trainer.js" type="text/javascript"></script>
<script src="/js/trainer/exercise.js" type="text/javascript"></script>
<script>
var trainerData = <%=request.getAttribute("trainer")%>
focusHome(trainerData);
</script>
</body>
</html>