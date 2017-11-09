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
	$("#createWorkoutButton").hide();
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
	$("#createWorkoutButton").hide();
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
	$("#createWorkoutButton").hide();
	
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
	$("#createWorkoutButton").show();
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
	})
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
	
	/*var params = {};
	params.type = "createWorkout";
	params.name = "testWorkout7";
	params.exerciseIds = [76,74,75];
	$.ajax({
		url: "/trainer/ui",
		method: "POST",
		data: params,
		
		success: function(data) {
			console.log(data);
			

		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});*/
	
	
});

$("#submitWorkout").on("click", function(){
	$("#createWorkoutModal").modal();
	
	var params = {};
	params.type = "createWorkout";
	params.name = $("#workoutName").val();
	params.exerciseList = SELECTED_EXERCISES_LIST;
	$.ajax({
		url: "/trainer/ui",
		method: "POST",
		data: params,
		
		success: function(data) {
			console.log(data);
			SELECTED_EXERCISES_LIST = [];
			$.modal.close();

		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});
});
	
	
	
	