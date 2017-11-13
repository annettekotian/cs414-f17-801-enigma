function focusHome() {
	$("#homeLi").css("background", "darkgrey");
	$("#home").show();
	populateHomePage()
	$("#customerTable").hide();
	$(".trainerSearchCustomers").hide();
	$("#exerciseResults").hide();
	$("#addExercise").hide();
	$("#modifyExercise").hide();
	$("#deleteExercise").hide();
	$("#searchExerciseBox").hide();
	$("#searchExercise").hide();
	$("#resetExercise").hide();
	$("#workoutResults").hide();
	$(".workoutButtons").hide();
}

function populateHomePage() {
	$("#homeFName").text(trainerData.personalInformation.firstName);
	$("#homeLName").text(trainerData.personalInformation.lastName);
	$("#homeEmail").text(trainerData.personalInformation.email);
	$("#homePhone").text(trainerData.personalInformation.phoneNumber);
	$("#homeInsurance").text(trainerData.personalInformation.healthInsurance.name);
	$("#homeAddress").text(trainerData.personalInformation.address.street + ", " + trainerData.personalInformation.address.city + 
			", " + trainerData.personalInformation.address.state.stateAbbrev + " - " + trainerData.personalInformation.address.zipcode);
}

function focusCustomers() {
	populateCustomers();
	$("#home").hide();
	$("#customerTable").show();
	$(".trainerSearchCustomers").show();
	
	$("#exerciseResults").hide();
	$("#addExercise").hide();
	$("#modifyExercise").hide();
	$("#deleteExercise").hide();
	$("#searchExerciseBox").hide();
	$("#searchExercise").hide();
	$("#resetExercise").hide();
	$("#workoutResults").hide();
	$(".workoutButtons").hide();
}

function focusExercises() {
	$("#exerciseResults").show();
	$("#addExercise").show();
	$("#modifyExercise").show();
	$("#deleteExercise").show();
	$("#searchExerciseBox").show();
	$("#searchExercise").show();
	$("#resetExercise").show();
	
	$("#customerTable").hide();
	$("#home").hide();
	$(".trainerSearchCustomers").hide();
	$("#workoutResults").hide();
	$(".workoutButtons").hide();
	
	populateAllExercises();
}

function focusWorkouts() {
	$("#exerciseResults").hide();
	$("#addExercise").hide();
	$("#modifyExercise").hide();
	$("#deleteExercise").hide();
	$("#searchExerciseBox").hide();
	$("#searchExercise").hide();
	$("#resetExercise").hide();
	
	$("#customerTable").hide();
	$("#home").hide();
	$(".trainerSearchCustomers").hide();
	$("#workoutResults").show();
	
	$(".workoutButtons").show();
	showWorkoutData();
}

$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").on("click", function(){
	$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").css("background", "none");
	$(this).css("background", "darkgrey");
	
});

$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").on("mouseenter", function(){
	$("#trainerLi, #managerLi, #customersLi, #inventoryLi").css("color", "black")
	$(this).css("color", "steelblue");
});

$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").on("mouseleave", function(){
	$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").css("color", "black")
	
});

$("#homeLi").on("click", focusHome);

$("#customerLi").on("click", focusCustomers);

$("#exerciseLi").on("click", focusExercises);

$("#workoutsLi").on("click", focusWorkouts);


function populateCustomers() {
	var currentUrl = window.location.pathname
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
		data: {
			type: "getCustomers"
		},
		success: function(data, textStatus, jqXHR) {
			data = JSON.parse(data);
			if(data.status == "success") {
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


/**
 * this methods loads the customer table data
 * @param customerData: JSON array of customer data
 * @returns
 */
function generateCustomersDisplay(customerData){
	$("#customerTable").find(".tableData").remove();
	for (var i = 0; i < customerData.length; i++) {
		var customer = customerData[i];
		$("#customerTable table").append("<tr class='tableData'> " +
				"<td>" +  customer.id+"</td> " + 
				"<td> " + customer.personalInformation.firstName+ "</td> " + 
				" <td> " + customer.personalInformation.lastName +"</td> " +
				" <td> " + customer.personalInformation.address.street + " "+ customer.personalInformation.address.city+ " " 
				+ customer.personalInformation.address.state.state + " " + customer.personalInformation.address.zipcode +" </td> " +
						"<td> "+ customer.personalInformation.email+ "</td> " + "" +
						"<td>" + customer.personalInformation.phoneNumber + "</td> " +
						" <td>" + customer.personalInformation.healthInsurance.name + "</td>"+
						" <td>" + customer.membership.type+ "</td></tr>");
	}
	
}

$("#trainerSearchCustomerButton").on("click", function(){
	var params = {};
	params.type = "getSearchCustomerResults";
	params.searchText = $("#trainerSearchCustomerInput").val();
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
		data: params,
		
		success: function(data) {
			var data = JSON.parse(data);
			var customerData = data.results;
			
			generateCustomersDisplay(customerData);
			

		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});
});

$("#createWorkoutModal").on($.modal.BEFORE_OPEN, function() {
	
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
		data: {
			type: "getAllExercises"
		},
		
		success: function(data) {
			//debugger;
			$("#workoutExerciseSelect").children().remove();
			$("#workoutExerciseSelect").append("<option>--Select--</option>")
			for(var i = 0; i< data.length; i++) {
				$("#workoutExerciseSelect").append("<option>"+ data[i].name + "</option>");
			}
			
			

		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});
});


$("#addExerciseToWorkout").on("click", function(){
	if($("#workoutExerciseSelect").find(":selected").index() == 0) {
		alert("Please select an exercise to add to the workout.");
	} else {
		var exerciseName = $("#workoutExerciseSelect").val();
		if(SELECTED_EXERCISES_LIST.indexOf(exerciseName)>=0) {
			alert("Exercise has already been added!");
			return;
		}
		
		$("#workoutExerciseList").append("<li>"+ $("#workoutExerciseSelect").val() +"</li>");
		SELECTED_EXERCISES_LIST.push(exerciseName);
	}
})


$("#createWorkoutButton").on("click", function(){
	$("#createWorkoutModal").modal();
	
});

$("#createWorkoutModal").on($.modal.AFTER_CLOSE, function(){
	$("#workoutName").val("");
	$("#workoutExerciseSelect").children().remove();
	$("#workoutExerciseList").children().remove();
	SELECTED_EXERCISES_LIST = [];
})

$("#submitWorkout").on("click", function(){
	//$("#createWorkoutModal").modal();
	
	var params = {};
	params.type = "createWorkout";
	params.name = $("#workoutName").val();
	params.exerciseList = SELECTED_EXERCISES_LIST;
	$.ajax({
		url: "/trainer/ui",
		method: "POST",
		data: params,
		
		success: function(workout) {
			
			//console.log(data);
			SELECTED_EXERCISES_LIST = [];
			WORKOUT_LIST.push(workout);
			$.modal.close();
			$("#workoutResults table").append("<tr data-id='"+ workout.id + "' class='tableData'> " +
					"<td>" +  workout.id+"</td> " + 
					"<td> " + workout.name+ "</td> " + 
					"<td> <a class='viewWorkoutExercises' href='#'>View Exercises</a></td></tr>");

		},
		error: function(exception) {
			if(exception.responseText.indexOf("missing input") >=0) {
				alert("Could not create workout! Some input fields were missing");
			} else {
				alert("Error: " + exception);
			}
			
		}
	});
});
	
	
	
function showWorkoutData () {
	var params = {};
	params.type = "getAllWorkouts";
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
		data: params,
		
		success: function(data) {
			//console.log(data);
			WORKOUT_LIST = data;
			populateWorkoutTable(data);

		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});
}

function populateWorkoutTable(workoutList) {
	$("#editWorkoutButton").attr("disabled", true);
	CURRENTLY_EDITED_WORKOUT = 0;
	$("#workoutResults .tableData").remove();
	for(var i = 0; i < workoutList.length; i++) {
		workout = workoutList[i];
		$("#workoutResults table").append("<tr data-id='"+ workout.id + "' class='tableData'> " +
				"<td>" +  workout.id+"</td> " + 
				"<td> " + workout.name+ "</td> " + 
				"<td> <a class='viewWorkoutExercises' href='#'>View Exercises</a></td></tr>");
	}
}

$(document).on("click", ".viewWorkoutExercises", function(){
	var workoutId = $(this).parents('tr').data('id');
	var workout = WORKOUT_LIST.find(item => item.id == workoutId);
	var exercises = workout.exercises;
	$("#workoutExercises .tableData").remove();
	for(var i = 0; i< exercises.length; i++) {
		var exercise = exercises[i];
		$("#workoutExercises table").append("<tr data-id='"+ exercise.id + "' class='tableData'>" 
				+ "<td>" + (i+1) + "</td>" 
				+ "<td>" + exercise.id + "</td>" 
				+ "<td>" + exercise.name + "</td>"  
				+ "</tr>")
	}
	$("#workoutExercisesModal").modal();
})


$(document).on("click", "#workoutResults .tableData", function(){
	console.log("here");
	$("#workoutResults .tableData").css("background-color", "");
	$(this).css("background-color", "gainsboro");
	$("#editWorkoutButton").attr("disabled", false);
	$("#assignWorkoutButton").attr("disabled", false);
	$("#unassignWorkoutButton").attr("disabled", false);
	CURRENTLY_EDITED_WORKOUT = $(this).data("id");
});

$("#editWorkoutButton").on("click", function(){
	$("#editWorkoutModal").modal();
	
});

$("#editWorkoutModal").on($.modal.BEFORE_OPEN, function(){

	var workout = WORKOUT_LIST.find(item => item.id == CURRENTLY_EDITED_WORKOUT);
	$("#editWorkoutName").val(workout.name);
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
		data: {
			type: "getAllExercises"
		},
		async:false,
		success: function(data) {
			//debugger;
			$("#editWorkoutExerciseSelect").children().remove();
			$("#editWorkoutExerciseSelect").append("<option>--Select--</option>")
			for(var i = 0; i< data.length; i++) {
				$("#editWorkoutExerciseSelect").append("<option>"+ data[i].name + "</option>");
			}
			
			

		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});
	CURRENTLY_EDITED_WORKOUT_EXERCISES = $.extend(true, [], workout.exercises);
	populateEditExerciseInEditWorkoutModal(CURRENTLY_EDITED_WORKOUT_EXERCISES);	
})

function populateEditExerciseInEditWorkoutModal(exerciseList){
	$("#editWorkoutExercises").find(".tableData").remove();
	for(var i = 0; i< exerciseList.length; i++){
		var exercise = exerciseList[i];
		$("#editWorkoutExercises").append("<tr class='tableData' data-name='"+ exercise.name + "' data-id='" + exercise.id + "'>"
				+ "<td><a class='removeExercise' href='#'>Remove</a></td>"
				+ "<td>"+ (i+1) + "</td>"
				+ "<td>"+ exercise.name + "</td>"
				+ "</tr");
	}
}

$(document).on("click", ".removeExercise", function(){
	var exerciseName = $(this).parents("tr").data("name");
	CURRENTLY_EDITED_WORKOUT_EXERCISES = CURRENTLY_EDITED_WORKOUT_EXERCISES.filter(function(obj){
		return obj.name != exerciseName;
	})
	
	populateEditExerciseInEditWorkoutModal (CURRENTLY_EDITED_WORKOUT_EXERCISES);
});


$("#addExerciseToEditedWorkout").on("click", function(){
	if($("#editWorkoutExerciseSelect").find(":selected").index() == 0) {
		alert("Please select an exercise to add to the workout.");
		return;
	}
	var exerciseName = $("#editWorkoutExerciseSelect").val();
	var exerciseExists = false;
	for(var i = 0; i< CURRENTLY_EDITED_WORKOUT_EXERCISES.length; i++) {
		if(CURRENTLY_EDITED_WORKOUT_EXERCISES[i].name == exerciseName) {
			exerciseExists = true;
			break;
		} 
	}
	
	if(exerciseExists == true){
		alert("Exercise has already been added!");
		return;
	}
	CURRENTLY_EDITED_WORKOUT_EXERCISES.push({name:exerciseName});
	populateEditExerciseInEditWorkoutModal (CURRENTLY_EDITED_WORKOUT_EXERCISES);
	
})

$("#editWorkoutModal").on($.modal.AFTER_CLOSE, function(){
	$("#editWorkoutName").val("");
	$("#editWorkoutExercises").find(".tableData").remove()
	$("#editWorkoutExerciseSelect").children().remove();
	CURRENTLY_EDITED_WORKOUT_EXERCISES = [];
	
});


$("#submitWorkoutChanges").on("click", function(){
	var params = {};
	params.id = CURRENTLY_EDITED_WORKOUT;
	params.type = "updateWorkout";
	params.name = $("#editWorkoutName").val();
	params.exerciseList = [];
	for(var i=0; i< CURRENTLY_EDITED_WORKOUT_EXERCISES.length;i++) {
		params.exerciseList.push(CURRENTLY_EDITED_WORKOUT_EXERCISES[i].name);
	}
	
	$.ajax({
		url: "/trainer/ui",
		method: "POST",
		data: params,
		
		success: function(workout) {
			$.modal.close();
			for(var i = 0; i< WORKOUT_LIST.length; i ++) {
			// did not use array.find because I wanted to referecne the object
			if(WORKOUT_LIST[i].id == workout.id) {
				WORKOUT_LIST[i] = workout;
			}
			
			var tr =  $("#workoutResults").find("tr[data-id='" + workout.id + "']");
			tr.children().remove();
			tr.append("<td>" +  workout.id+"</td> " + 
			"<td> " + workout.name+ "</td> " + 
			"<td> <a class='viewWorkoutExercises' href='#'>View Exercises</a></td>");
			
		}
		},
		error: function(exception) {
			if(exception.responseText.indexOf("missing input") >=0) {
				alert("Could not create workout! Some input fields were missing");
			} else {
				alert("Error: " + exception);
			}
			
		}
	});
	
});

function getAllCustomers() {
	var customers = null;
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
		data: {
			type: "getCustomers"
		},
		success: function(data, textStatus, jqXHR) {
			data = JSON.parse(data);
			customers = data.customers;
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
	return customers;
}

$("#assignWorkoutButton").on("click", function(){
	var customers = getAllCustomers();
	
	$("#assignWorkoutCustomerList").empty();
	$("#assignWorkoutCustomerList").append("<option data-id='0'>--None--</option>");
	if(customers) {
		for(var i=0; i<customers.length; i++) {
			$("#assignWorkoutCustomerList").append("<option data-id='" + customers[i].id + "'>" + customers[i].personalInformation.firstName + " " + customers[i].personalInformation.lastName + "</option>");
		}
	}
	
	$("#assignWorkoutModal").modal();
});

function postTrainerUi(params) {
	var results = null;
	$.ajax({
		url: "/trainer/ui",
		method: "POST",
		data: params,
		
		success: function(data) {
			results = data;
		},
		error: function(jqXHR, textStatus, errorThrown){
			$(document.body).html(jqXHR.responseText);
		},
		async: false
	});
	return results;
}

$("#assignWorkout").on("click", function(){
	var params = {}
	params.type = "assignWorkout";
	params.workoutId = CURRENTLY_EDITED_WORKOUT;
	params.customerId = $("#assignWorkoutCustomerList").find(":selected").data("id");
	if(params.customerId == 0) {
		return;
	}
	
	var data = postTrainerUi(params);
	if(data.rc == 0) {
		$.modal.close();
	}
	else {
		alert("Error: " + data.msg);
	}
});

$("#searchWorkoutButton").on("click", function(){
	getSearchWorkoutResults($("#searchWorkoutInput").val());
	
});

$("#resetSearchWorkout").on("click", function(){
	$("#searchWorkoutInput").val("");
	getSearchWorkoutResults("");
});

function getSearchWorkoutResults(searchText) {
	var params = {};
	params.type = "getSearchWorkoutResults";
	params.searchText = searchText;
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
		data: params,
		
		success: function(data) {
			//debugger;
			populateWorkoutTable(data);
		},
		error: function(error){
			alert("error " + error)
		},
		async: false
	});
}
