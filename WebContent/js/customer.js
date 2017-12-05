var workoutId = null;

$("#customerHomeLi, #customerWorkoutLi, #feedbackLi").on("click", function(){
	$("#customerHomeLi, #customerWorkoutLi, #feedbackLi").css("background", "none");
	$(this).css("background", "darkgrey");
})

$("#customerHomeLi").on("click", focusHome);
$("#customerWorkoutLi").on("click", focusWorkouts);

function focusHome() {
	$("#workoutResults").hide();
	$(".workoutButtons").hide();
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
	$(".workoutButtons").show();
	$("#home").hide();
	$("#workoutResults").show();
	populateWorkoutTable();
}

function populateWorkoutTable() {
	$("#workoutResults .tableData").remove();
	
	for(var i=0; i< customerData.workouts.length; i++) {
		var workout = customerData.workouts[i];
		$("#workoutResults table").append("<tr data-id='" + workout.id + "' class='tableData'>" +
				"<td> "+ workout.id + "</td>" +
				"<td>" + workout.name + "</td>" +
				"<td> <a href='#' class='viewExercise'>View Exercises</a></td>" +
				"<td>"  + "</td>" + 
				"</tr>")
	}
}

$(document).on("click", "#workoutResults .tableData", function(){
	console.log("here");
	$("#workoutResults .tableData").css("background-color", "");
	$(this).css("background-color", "gainsboro");
	$("#addFeedback").attr("disabled", false);
	workoutId = $(this).data("id");
});

$("#addFeedback").on("click", function(){
	$("#feedbackText").val("");
	$("#feedbackModal").modal();
	
});

$("#submitFeedback").on("click", function(){
	var feedback = $("#feedbackText").val();
	if(feedback == "") {
		return;
	}
	
	var params = {};
	params.type = "addFeedback";
	params.customerId = customerData.id;
	params.workoutId = workoutId;
	params.feedback = feedback;
	
	$.ajax({
		url: "/CustomerServlet/ui",
		method: "POST",
		data: params,
		
		success: function(data) {
			if(data.rc == 0) {
				$.modal.close();
				populateWorkoutTable();
			} else {
				alert("Error: " + data.msg);
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			$(document.body).html(jqXHR.responseText);
		},
	});
});

$(document).on("click", ".viewExercise", function() {
	debugger;
	var workoutId = $(this).parents("tr").data("id");
	var workout = customerData.workouts.find(element => element.id == workoutId);
	$("#workoutExercises").find(".tableData").remove();
	for(var i = 0; i< workout.exercises.length; i++) {
		var exercise = workout.exercises[i];
		$("#workoutExercises table").append("<tr class='tableData'>" +
				"<td>" + (i+1) + "</td>" +
				"<td>" + exercise.id + "</td>" +
				"<td>" + exercise.name + "</td>" +
				"</tr>")
	}
	$("#workoutExercisesModal").modal();
})




