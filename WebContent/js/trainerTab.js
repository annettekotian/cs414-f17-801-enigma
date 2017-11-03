// Globals
var TRAINER_FORM_TYPE = null;
var TRAINER_ID = null;
var SELECTED_TRAINER = null;


/****************************************************************/
/******* Functions user to add/modify/delete trainer info *******/
/****************************************************************/

function modifyTrainerForm() {
	populateTrainerSelectList();
	modifyTrainerInformation();
}

function checkNewHealthInsurance() {
	if(document.getElementById("modifyHealthInsurance").selectedIndex == 0) {
		document.getElementById("modifyOtherHealthInsurance").value = "";
		document.getElementById("modifyOtherHealthInsurance").disabled = false;
	}
	else {
		document.getElementById("modifyOtherHealthInsurance").disabled = true;
	}
}

function createTrainerForm() {
	
	// Set the form type
	TRAINER_FORM_TYPE = "create";
	
	// Set the trainer ID to zero since there is not ID yet
	TRAINER_ID = 0;
	
	// Clear the form
	document.getElementById("trainerForm").reset();
	
	// Populate the state select list
	var states = getStates();
	var stateList = $("#modifyState");
	stateList.empty();
	for (var i = 0; i< states.length; i++) {
		stateList.append("<option>" + states[i].state + "</option>");
	}
	
	// Populate the health insurance list
	var healthInsurances = getHealthInsurances();
	var healthInsuranceList = $("#modifyHealthInsurance");
	healthInsuranceList.empty();
	healthInsuranceList.append("<option>--Other--</option>")
	for (var i = 0; i< healthInsurances.length; i++) {
		healthInsuranceList.append("<option>" + healthInsurances[i].name + "</option>");
	}
	document.getElementById("modifyOtherHealthInsurance").disabled = false;
	
	document.getElementById("modifyTrainerHeader").style.display = "none";
	document.getElementById("newTrainerHeader").style.display = "block";
	
	$("#modifyTrainerForm").modal();
}

function modifyTrainerInformation() {
	
	// Set the form type
	TRAINER_FORM_TYPE = "update";
	
	// Clear the form
	document.getElementById("trainerForm").reset();
	
	// Get the specific trainer
	var trainer = JSON.parse(SELECTED_TRAINER.dataset.trainer);
	TRAINER_ID = trainer.id;
	
	// Populate the state select list
	var states = getStates();
	var stateList = $("#modifyState");
	stateList.empty();
	var selectIndex = 0;
	for (var i = 0; i< states.length; i++) {
		stateList.append("<option>" + states[i].state + "</option>");
		if(states[i].state == trainer.personalInformation.address.state.state) {
			selectIndex = i;
		}
	}
	document.getElementById("modifyState").options[selectIndex].selected = true;
	
	// Populate the health insurance list
	var healthInsurances = getHealthInsurances();
	var healthInsuranceList = $("#modifyHealthInsurance");
	healthInsuranceList.empty();
	selectIndex = 0;
	healthInsuranceList.append("<option>--Other--</option>")
	for (var i = 0; i< healthInsurances.length; i++) {
		healthInsuranceList.append("<option>" + healthInsurances[i].name + "</option>");
		if(healthInsurances[i].name == trainer.personalInformation.healthInsurance.name) {
			selectIndex = (i+1);
		}
	}
	document.getElementById("modifyHealthInsurance").options[selectIndex].selected = true;
	
	document.getElementById("modifyOtherHealthInsurance").disabled = true;
	
	document.getElementById("modifyFirstName").value = trainer.personalInformation.firstName;
	document.getElementById("modifyLastName").value = trainer.personalInformation.lastName;
	document.getElementById("modifyPhoneNumber").value = trainer.personalInformation.phoneNumber;
	document.getElementById("modifyEmail").value = trainer.personalInformation.email;
	document.getElementById("modifyStreet").value = trainer.personalInformation.address.street;
	document.getElementById("modifyCity").value = trainer.personalInformation.address.city;
	document.getElementById("modifyZipcode").value = trainer.personalInformation.address.zipcode;
	document.getElementById("modifyUserName").value = trainer.user.username;
	document.getElementById("modifyPassword").value = trainer.user.password;
	document.getElementById("modifyConfirmPassword").value = trainer.user.password;
	
	document.getElementById("modifyTrainerHeader").style.display = "block";
	document.getElementById("newTrainerHeader").style.display = "none";
	
	$("#modifyTrainerForm").modal();
}

function submitTrainerForm() {
	
	var postParams = {};
	postParams.id = TRAINER_ID;
	postParams.firstName = $("#modifyFirstName").val();
	postParams.lastName = $("#modifyLastName").val();
	postParams.userName = $("#modifyUserName").val();
	postParams.password = $("#modifyPassword").val();
	postParams.confirmPassword = $("#modifyConfirmPassword").val();
	postParams.email = $("#modifyEmail").val();
	postParams.phone = $("#modifyPhoneNumber").val();
	postParams.street = $("#modifyStreet").val();
	postParams.city = $("#modifyCity").val();
	postParams.state = $("#modifyState").val();
	postParams.zip = $("#modifyZipcode").val();
	
	if(document.getElementById("modifyHealthInsurance").selectedIndex == 0) {
		postParams.healthInsurance = $("#modifyOtherHealthInsurance").val();
	}
	else {
		postParams.healthInsurance = $("#modifyHealthInsurance").val();
	}
	
	if(TRAINER_FORM_TYPE == "create") {
		postParams.type = "createTrainer";
	}
	else {
		postParams.type = "updateTrainer";
	}
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			if(data.rc == 0) {
				populateAllTrainers();
				$.modal.close();
			}
			else {
				alert("Error: " + data.msg);
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			$(document.body).html(jqXHR.responseText);
		}
	});
}

function deleteTrainer() {

	var trainer = JSON.parse(SELECTED_TRAINER.dataset.trainer);
	if(!confirm("Are you sure you want to delete " + trainer.personalInformation.firstName + " " + trainer.personalInformation.lastName + "?")) {
		return;
	}
	
	var postParams = {};
	postParams.id = trainer.id;
	postParams.type = "deleteTrainer";
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			if(data.rc == 0) {
				populateAllTrainers();
				$.modal.close();
			}
			else {
				alert("Error: " + data.msg);
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			$(document.body).html(jqXHR.responseText);
		}
	});
}



/****************************************************************/
/****** Functions user to add/modify/delete qualifications ******/
/****************************************************************/

function addTrainerQualification() {
	populationQualifications();
	document.getElementById("qualificationForm").reset();
	$("#addQualificationForm").modal();
}

function populationQualifications() {
	var qualifications = getAllQualifications();
	var qualificationsList = $("#qualifications");
	qualificationsList.empty();
	qualificationsList.append("<option>--Other--</option>");
	for (var i = 0; i< qualifications.length; i++) {
		qualificationsList.append("<option>" + qualifications[i].name + "</option>");
	}
	checkNewQualification();
}

function getAllQualifications() {
	var qualifications = null;
	$.ajax({
		url: "/manager/ui",
		data: {
			type: "getAllQualifications"
		},
		method: "GET",
		success: function(data, textStatus, jqXHR) {
			qualifications = data;
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
	return qualifications;
}

function checkNewQualification() {
	if(document.getElementById("qualifications").selectedIndex == 0) {
		document.getElementById("newQualification").value = "";
		document.getElementById("newQualification").disabled = false;
	}
	else {
		document.getElementById("newQualification").disabled = true;
	}
}

function submitTrainerQualification() {
	var trainer = JSON.parse(SELECTED_TRAINER.dataset.trainer);
	var qualification = "";
	if(document.getElementById("qualifications").selectedIndex == 0) {
		qualification = $("#newQualification").val();
	}
	else {
		qualification = $("#qualifications").val();
	}
	var postParams = {};
	postParams.id = trainer.id;
	postParams.qualification = qualification;
	postParams.type = "addQualification";
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			if(data.rc == 0) {
				populateAllTrainers();
				$.modal.close();
			}
			else {
				alert("Error: " + data.msg);
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			$(document.body).html(jqXHR.responseText);
		}
	});
}

function removeTrainerQualificationForm() {
	var trainer = JSON.parse(SELECTED_TRAINER.dataset.trainer);
	var qualificationsList = $("#removeQualificationsList");
	qualificationsList.empty();
	if(trainer.qualifications.length == 0){
		populateAllTrainers();
		return;
	}
	for (var i = 0; i< trainer.qualifications.length; i++) {
		qualificationsList.append("<option>" + trainer.qualifications[i].name + "</option>");
	}
	$("#removeQualificationModal").modal();
}


function removeTrainerQualification() {
	var trainer = JSON.parse(SELECTED_TRAINER.dataset.trainer);
	var qualification = $("#removeQualificationsList").val();
	var postParams = {};
	postParams.id = trainer.id;
	postParams.qualification = qualification;
	postParams.type = "deleteQualification";
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			if(data.rc == 0) {
				populateAllTrainers();
				$.modal.close();
			}
			else {
				alert("Error: " + data.msg);
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			$(document.body).html(jqXHR.responseText);
		}
	});
}


/****************************************************************/
/******** Functions user to add/modify/delete work hours ********/
/****************************************************************/

function populateStartDay() {
	var year = parseInt($("#startYear").find(":selected").text());
	var month = $("#startMonth")[0].selectedIndex + 1;
	var numberOfDays = new Date(year, month, 0).getDate();
	
	var startDayList = $("#startDay");
	startDayList.empty();
	for(var i=1; i<=numberOfDays; i++) {
		startDayList.append("<option>" + i + "</option>");
	}
}

function populateEndDay() {
	var year = parseInt($("#endYear").find(":selected").text());
	var month = $("#endMonth")[0].selectedIndex + 1;
	var numberOfDays = new Date(year, month, 0).getDate();
	
	var endDayList = $("#endDay");
	endDayList.empty();
	for(var i=1; i<=numberOfDays; i++) {
		endDayList.append("<option>" + i + "</option>");
	}
}

function addTrainerWorkHours() {
	var date = new Date();
	
	var startYearList = $("#startYear");
	startYearList.empty();
	for (var i = 0; i< 5; i++) {
		startYearList.append("<option>" + (date.getFullYear() + i) + "</option>");
	}
	
	var endYearList = $("#endYear");
	endYearList.empty();
	for (var i = 0; i< 5; i++) {
		endYearList.append("<option>" + (date.getFullYear() + i) + "</option>");
	}
	
	populateStartDay();
	populateEndDay();
	
	$("#addWorkHoursForm").modal();
}

function submitWorkHours() {
	var trainer = JSON.parse(SELECTED_TRAINER.dataset.trainer);
	var startYear = parseInt($("#startYear").find(":selected").text());
	var startMonth = parseInt($("#startMonth")[0].selectedIndex);
	var startDay = parseInt($("#startDay")[0].selectedIndex);
	var startHour = parseInt($("#startHour").val());
	var startMinute = parseInt($("#startMinute").val());
	var startPeriod = parseInt($("#startPeriod")[0].selectedIndex);
	var endYear = parseInt($("#endYear").find(":selected").text());
	var endMonth = parseInt($("#endMonth")[0].selectedIndex);
	var endDay = parseInt($("#endDay")[0].selectedIndex);
	var endHour = parseInt($("#endHour").val());
	var endMinute = parseInt($("#endMinute").val());
	var endPeriod = parseInt($("#endPeriod")[0].selectedIndex);
	
	var postParams = {};
	postParams.id = trainer.id;
	postParams.startYear = startYear;
	postParams.startMonth = startMonth;
	postParams.startDay = startDay + 1;
	postParams.startHour = startHour + startPeriod * 12;
	postParams.startMinute = startMinute;
	postParams.endYear = endYear;
	postParams.endMonth = endMonth;
	postParams.endDay = endDay + 1;
	postParams.endHour = endHour + startPeriod * 12;
	postParams.endMinute = endMinute;
	postParams.type = "addWorkHours";
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			if(data.rc == 0) {
				populateAllTrainers();
				$.modal.close();
			}
			else {
				alert("Error: " + data.msg);
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			$(document.body).html(jqXHR.responseText);
		}
	});
}



/****************************************************************/
/************* Functions to populate trainers table *************/
/****************************************************************/

// Get all trainers from the server
function getAllTrainers() {
	var trainers = null;
	$.ajax({
		url: "/manager/ui",
		data: {
			type: "getAllTrainers"
		},
		method: "GET",
		success: function(data, textStatus, jqXHR) {
			trainers = data;
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
	return trainers;
}

// Send a search value to the server expecting a list of trainers to be returned
function searchTrainers(searchValue) {
	var trainers = null;
	$.ajax({
		url: "/manager/ui",
		data: {
			type: "searchTrainers",
			value: searchValue
		},
		method: "GET",
		success: function(data, textStatus, jqXHR) {
			trainers = data;
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
	return trainers;
}

// Reset the search trainer bar
function resetSearchTrainers() {
	$("#searchTrainerInput").val("");
	populateAllTrainers();
}

// Populate the trainers table with information matching the search info
function populateSearchTrainers() {
	var searchValue = $("#searchTrainerInput").val();
	var trainers = searchTrainers(searchValue);
	populateTrainerTable(trainers);
}

// Populate the trainers table with all trainer information
function populateAllTrainers() {
	var trainers = getAllTrainers();
	populateTrainerTable(trainers);
}

// Onclick function for when a trainer table row is selected
function selectTrainerRow(event) {
	if(SELECTED_TRAINER != null) {
		SELECTED_TRAINER.id = "";
	}
	SELECTED_TRAINER = event.target.parentElement;
	SELECTED_TRAINER.id = "trainerTableRowSelected";
	document.getElementById("modifyTrainer").disabled = false;
	document.getElementById("deleteTrainer").disabled = false;
	document.getElementById("addQualification").disabled = false;
	document.getElementById("removeQualification").disabled = false;
	document.getElementById("addWorkHours").disabled = false;
}

// Function to populate the trainers table with specific trainer information
function populateTrainerTable(trainers) {	

	SELECTED_TRAINER = null;
	document.getElementById("modifyTrainer").disabled = true;
	document.getElementById("deleteTrainer").disabled = true;
	document.getElementById("addQualification").disabled = true;
	document.getElementById("removeQualification").disabled = true;
	document.getElementById("addWorkHours").disabled = true;
	
	// Remove all rows from the table except the first
	$("#trainerTable tr").slice(1).remove();
	
	for(var i=0; i<trainers.length; i++) {
		
		var trainerId = trainers[i].id;
		var firstName = trainers[i].personalInformation.firstName;
		var lastName = trainers[i].personalInformation.lastName;
		var address = trainers[i].personalInformation.address.street + " "
			+ trainers[i].personalInformation.address.city + ", "
			+ trainers[i].personalInformation.address.state.stateAbbrev + " "
			+ trainers[i].personalInformation.address.zipcode;
		var email = trainers[i].personalInformation.email;
		var phoneNumber = trainers[i].personalInformation.phoneNumber;
		var healthInsurance = trainers[i].personalInformation.healthInsurance.name;	
		
		var qualifications = "";
		for(var j=0; j<trainers[i].qualifications.length; j++) {
			qualifications += trainers[i].qualifications[j].name + "<br>";
		}
		
		var workSchedule = "";
		for(var j=0; j<trainers[i].workHours.length; j++) {
			var startDateTime = trainers[i].workHours[j].startDateTime;
			var endDateTime = trainers[i].workHours[j].endDateTime;
			workSchedule += startDateTime + " - " + endDateTime + "<br>";
		}		
		
		var trainerRow = "<tr class=trainerTableRow onclick='selectTrainerRow(event)' data-trainer='" + JSON.stringify(trainers[i]) + "'>"
			+ "<td>" + trainerId + "</td>"
			+ "<td>" + firstName + "</td>"
			+ "<td>" + lastName + "</td>"
			+ "<td>" + address + "</td>"
			+ "<td>" + email + "</td>"
			+ "<td>" + phoneNumber + "</td>"
			+ "<td>" + healthInsurance + "</td>"
			+ "<td>" + qualifications + "</td>"
			+ "<td>" + workSchedule + "</td>"
			+ "</tr>";
					  
		$("#trainerTable").append(trainerRow);
	}
}