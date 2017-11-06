function populateAllExercises() {
	var exercises = getAllExercises();
	populateExercises(exercises);
}

function populateExercises(exercises) {
	$("#exerciseTable tr").slice(1).remove();
	for(var i=0; i<exercises.length; i++) {
		var name = exercises[i].name;
		var duration = "00:00:00";
		var sets = "";
		var machine = "";
		
		var exerciseRow = "<tr data-exercise='" + JSON.stringify(exercises[i]) + "'>"
		+ "<td>" + name + "</td>"
		+ "<td>" + duration + "</td>"
		+ "<td>" + sets + "</td>"
		+ "<td>" + machine + "</td>"
		+ "</tr>";
				  
		$("#exerciseTable").append(trainerRow);
	}
}

function getAllExercises() {
	var exercises = null;
	$.ajax({
		url: "/trainer/ui",
		data: {
			type: "getAllExercises"
		},
		method: "GET",
		success: function(data, textStatus, jqXHR) {
			exercises = data;
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
	return exercises;
}

function addExerciseModal() {
	$("#exerciseForm")[0].reset();
	$("#addExerciseHeader").show();
	$("#modifyExerciseHeader").hide();
	$("#exerciseModal").modal();
}

function addExercise() {
	var params = {};
	params.type = "createExercise";
	params.name = $("#exerciseName").val();
	params.machineId = $("#exerciseMachine").val();
	params.hours = $("#exerciseDurationHours").val();
	params.minutes = $("#exerciseDurationMinutes").val();
	params.seconds = $("#exerciseDurationSeconds").val();
	
	data = postTrainerUi(params);
	if(data.rc == 0) {
		populateAllExercises();
		$.modal.close();
	}
	else {
		alert("Error: " + data.msg);
	}
}

function postTrainerUi(params) {
	$.ajax({
		url: "/trainer/ui",
		method: "POST",
		data: params,
		
		success: function(data) {
			return data;
		},
		error: function(jqXHR, textStatus, errorThrown){
			$(document.body).html(jqXHR.responseText);
		},
		async: false
	});
}