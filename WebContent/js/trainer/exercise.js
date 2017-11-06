function populateAllExercises() {
	var exercises = getAllExercises();
	populateExercises(exercises);
}

function populateExercises(exercises) {
	$("#exerciseTable tr").slice(1).remove();
	for(var i=0; i<exercises.length; i++) {
		var name = exercises[i].name;
		var duration = "";
		var sets = "";
		var machine = "";

		
		if(exercises[i].duration) {
			duration = exercises[i].duration.hours + "H " + exercises[i].duration.minutes + "M " + exercises[i].duration.seconds + "S";
		}
		if(exercises[i].sets) {
			for(var j=0; j<exercises[i].sets.length; j++) {
				if(j == 0) {
					sets = sets + exercises[i].sets[i].repetitions + " repetition(s)";
				}
				else {
					sets = sets + ", " + exercises[i].sets[i].repetitions + " repetition(s)";
				}
			}
		}
		
		var exerciseRow = "<tr data-exercise='" + JSON.stringify(exercises[i]) + "'>"
			+ "<td>" + name + "</td>"
			+ "<td>" + duration + "</td>"
			+ "<td>" + sets + "</td>"
			+ "<td>" + machine + "</td>"
			+ "</tr>";
				  
		$("#exerciseTable").append(exerciseRow);
	}
}

function getAllExercises() {
	var params = {};
	params.type = "getAllExercises";
	return getTrainerUi(params);
}

function getAllMachines() {
	var params = {};
	params.type = "getAllMachines";
	return getTrainerUi(params);
}

function addExerciseModal() {
	$("#exerciseName").val("");
	$("#exerciseDurationHours").val("");
	$("#exerciseDurationMinutes").val("");
	$("#exerciseDurationSeconds").val("");
	$("#exerciseSetRepetition").val("");
	
	$("#exerciseMachine").empty();
	$("#exerciseMachine").append("<option data-id='0'>--None--</option>");
	var machines = getAllMachines();
	for(var i=0; i<machines.lenght; i++) {
		$("#exerciseMachine").append("<option data-id='" + machines[i].id + "'>" + machines[i].name + "</option>");
	}
	
	
	$("#addExerciseHeader").show();
	$("#modifyExerciseHeader").hide();
	$("#exerciseSetList").empty();
	$("#exerciseModal").modal();
}

function addExerciseSet() {
	var repetitions = $("#exerciseSetRepetition").val();
	$("#exerciseSetList").append("<li data-reps='" + repetitions + "'>" + repetitions + " repetition(s)</li>");
}

function addExercise() {
	var params = {};
	params.type = "createExercise";
	params.name = $("#exerciseName").val();
	params.machineId = $("#exerciseMachine").find(":selected").data("id");
	params.hours = $("#exerciseDurationHours").val() || 0;
	params.minutes = $("#exerciseDurationMinutes").val() || 0;
	params.seconds = $("#exerciseDurationSeconds").val() || 0;
	params.sets = [];
	$("#exerciseSetList li").each(function () {
		params.sets.push($(this).data("reps"));
	});
	
	var data = postTrainerUi(params);
	if(data.rc == 0) {
		populateAllExercises();
		$.modal.close();
	}
	else {
		alert("Error: " + data.msg);
	}
}

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

function getTrainerUi(params) {
	var results = null;
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
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