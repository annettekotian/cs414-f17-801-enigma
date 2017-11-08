var selectedExerciseRow = null;

function selectExerciseRow(event) {
	if(selectedExerciseRow != null) {
		selectedExerciseRow.id = "";
	}
	selectedExerciseRow = event.target.parentElement;
	selectedExerciseRow.id = "selectedExerciseRow";
	document.getElementById("modifyExercise").disabled = false;
	document.getElementById("deleteExercise").disabled = false;
}

function clearSelectedExerciseRow() {
	if(selectedExerciseRow != null) {
		selectedExerciseRow.id = "";
		selectedExerciseRow = null;
		document.getElementById("modifyExercise").disabled = true;
		document.getElementById("deleteExercise").disabled = true;
	}
}

function populateAllExercises() {
	$("#searchExerciseBox").val("");
	var exercises = getAllExercises();
	populateExercises(exercises);
}

function populateExercises(exercises) {
	clearSelectedExerciseRow();
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
					sets = sets + exercises[i].sets[j].repetitions + " repetition(s)";
				}
				else {
					sets = sets + ", " + exercises[i].sets[j].repetitions + " repetition(s)";
				}
			}
		}
		if(exercises[i].machine) {
			machine = exercises[i].machine.name;
		}
		
		var exerciseRow = "<tr class='exerciseRow' onclick='selectExerciseRow(event)' data-exercise='" + JSON.stringify(exercises[i]) + "'>"
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

function resetExerciseModal(){
	$("#exerciseName").val("");
	$("#exerciseDurationHours").val("");
	$("#exerciseDurationMinutes").val("");
	$("#exerciseDurationSeconds").val("");
	$("#exerciseSetRepetition").val("");
	
	$("#exerciseMachine").empty();
	$("#exerciseMachine").append("<option data-id='0'>--None--</option>");
	var machines = getAllMachines();
	for(var i=0; i<machines.length; i++) {
		$("#exerciseMachine").append("<option value='" + machines[i].name + "' data-id='" + machines[i].id + "'>" + machines[i].name + "</option>");
	}
	
	$("#exerciseSetList").empty();
}

function addExerciseModal() {
	resetExerciseModal();
	
	$("#addExerciseHeader").show();
	$("#modifyExerciseHeader").hide();
	$("#submitModifyExercise").hide();
	$("#submitAddExercise").show();
	$("#exerciseModal").modal();
}

function addExerciseSet() {
	var repetitions = $("#exerciseSetRepetition").val();
	$("#exerciseSetList").append("<li data-reps='" + repetitions + "'>" + repetitions + " repetition(s)</li>");
}

function deleteExerciseSet() {
	if($("#exerciseSetList li").length > 0) {
		$("#exerciseSetList li:last-child").remove();
	}
}

function addExercise() {
	submitExercise("createExercise", 0);
}

function submitExercise(type, id) {
	var params = {};
	params.type = type;
	params.id = id;
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

function modifyExerciseModal() {
	resetExerciseModal();
	
	var exercise = JSON.parse(selectedExerciseRow.dataset.exercise);
	
	$("#exerciseName").val(exercise.name);
	if(exercise.duration) {
		if(exercise.duration.hours) {
			$("#exerciseDurationHours").val(exercise.duration.hours);
		}
		if(exercise.duration.minutes) {
			$("#exerciseDurationMinutes").val(exercise.duration.minutes);
		}
		if(exercise.duration.seconds) {
			$("#exerciseDurationSeconds").val(exercise.duration.seconds);
		}
	}
	if(exercise.sets) {
		for(var i=0; i<exercise.sets.length; i++) {
			$("#exerciseSetList").append("<li data-reps='" + exercise.sets[i].repetitions + "'>" + exercise.sets[i].repetitions + " repetition(s)</li>");
		}
	}
	if(exercise.machine) {
		$("#exerciseMachine").val(exercise.machine.name);
	}
	
	$("#addExerciseHeader").hide();
	$("#modifyExerciseHeader").show();
	$("#submitModifyExercise").show();
	$("#submitAddExercise").hide();
	$("#exerciseModal").modal();
}

function modifyExercise() {
	submitExercise("modifyExercise", JSON.parse(selectedExerciseRow.dataset.exercise).id);
}

function deleteExercise() {
	var exercise = JSON.parse(selectedExerciseRow.dataset.exercise);
	if(!confirm("Are you sure you want to delete " + exercise.name + "?")) {
		return;
	}
	
	var params = {};
	params.type = "deleteExercise";
	params.id = exercise.id;

	var data = postTrainerUi(params);
	if(data.rc == 0) {
		populateAllExercises();
		$.modal.close();
	}
	else {
		alert("Error: " + data.msg);
	}
}

function searchExercise() {
	var value = $("#searchExerciseBox").val();
	var params = {};
	params.type = "searchExercises";
	params.value = value;
	
	var exercises = getTrainerUi(params);
	populateExercises(exercises);
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