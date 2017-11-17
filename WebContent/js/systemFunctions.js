function getStates() {
	var states = null;
	$.ajax({
		url: "/manager/ui",
		data: {
			type: "getStates"
		},
		method: "GET",
		success: function(data, textStatus, jqXHR) {
			states = JSON.parse(data.states);
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
	return states;
}

function getHealthInsurances() {
	var healthInsurances = null;
	$.ajax({
		url: "/manager/ui",
		data: {
			type: "getHealthInsurances"
		},
		method: "GET",
		success: function(data, textStatus, jqXHR) {
			healthInsurances = JSON.parse(data.healthInsurances);
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
	return healthInsurances;
}