$("#customerHomeLi, #customerWorkoutLi, #feedbackLi").on("click", function(){
	$("#customerHomeLi, #customerWorkoutLi, #feedbackLi").css("background", "none");
	$(this).css("background", "darkgrey");
})

$("#customerHomeLi").on("click", focusHome);
$("#customerWorkoutLi").on("click", focusWorkouts);
$("#feedbackLi").on("click", focusFeedback);

function focusHome() {
	$("#workoutResults").hide();
	$("#home").show();
	$("#homeFName").text(customerData.personalInformation.firstName);
	$("#homeLName").text(customerData.personalInformation.lastName);
	$("#homeEmail").text(customerData.personalInformation.email);
	$("#homePhone").text(customerData.personalInformation.phoneNumber);
	$("#homeInsurance").text(customerData.personalInformation.healthInsurance.name);
	$("#homeAddress").text(customerData.personalInformation.address.street + ", " + customerData.personalInformation.address.city + 
			", " + customerData.personalInformation.address.state.stateAbbrev + " - " + customerData.personalInformation.address.zipcode);
	$("#membershipStatus").text(customerData.membership.type);
}


function focusWorkouts() {
	$("#home").hide();
	$("#workoutResults").show();
	populateWorkoutTable();
}

function focusFeedback() {
	$("#home").hide();
	$("#workoutResults").hide();
}

function populateWorkoutTable() {
	$("#workoutResults .tableData").remove();
	
	for(var i=0; i< customerData.workouts.length; i++) {
		var workout = customerData.workouts[i];
		$("#workoutResults table").append("<tr data-id='"+ workout.id +  "' class='tableData'>" +
				"<td> "+ workout.id + "</td>" +
				"<td>" + workout.name + "</td>" +
				"<td> <a href='#' class='viewExercise'>View Exercises</a></td></tr>")
	}
}