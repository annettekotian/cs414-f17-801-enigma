function showAdminUI(managerData) {
	$("#managerLi").css("background", "darkgrey");
	$("#managerResults").find(".tableData").remove();
	populateManagerTable(managerData);
	
	showManagerData();
}

function showManagerUI() {
	$("#trainerLi").css("background", "darkgrey");
	$("#managerLi").hide();
	showTrainerData();
}

$("#trainerLi, #managerLi, #customersLi, #inventoryLi ").on("mouseenter", function(){
	$("#trainerLi, #managerLi, #customersLi, #inventoryLi").css("color", "black")
	$(this).css("color", "steelblue");
});

$("#trainerLi, #managerLi, #customersLi, #inventoryLi ").on("mouseleave", function(){
	$("#trainerLi, #managerLi, #customersLi, #inventoryLi").css("color", "black")
	
});
	
$("#trainerLi, #managerLi, #customersLi, #inventoryLi ").on("click", function(){
	$("#trainerLi, #managerLi, #customersLi, #inventoryLi").css("background", "none");
	$(this).css("background", "darkgrey");
})

$("#trainerLi").on("click", function(){
	showTrainerData();
});

$("#managerLi").on("click", function(){
	getAllManagers();
	showManagerData();
})

$("#customersLi").on("click", function(){
	showCustomerData();
})

$("#inventoryLi").on("click", function(){
	showInventoryData();
})

function showTrainerData() {
	populateTrainerTable();
	
	$("#addManager").hide();
	$(".searchManager").hide();
	$("#addCustomer").hide()
	$(".searchCustomer").hide();
	$("#addMachine").hide();
	$("#managerResults").hide();
	$("#customerResults").hide();
	$("#inventoryResults").hide();
	$("#addTrainer").show();
	$("#modifyTrainer").show();
	$("#trainerResults").show();
	$("#deleteTrainer").show();
}

function getAllTrainers() {
	var trainers = null;
	$.ajax({
		url: "/manager/ui",
		data: {
			type: "getAllTrainers"
		},
		method: "GET",
		success: function(data, textStatus, jqXHR) {
			trainers = data
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
	return trainers;
}

function populateTrainerTable() {
	var trainers = getAllTrainers();	

	var trainerTable = document.getElementById("trainerTable");
	while(trainerTable.rows.length > 0) {
		trainerTable.deleteRow(0);
	}
	
	var trainerTableRow = document.createElement("tr")
	
	var trainerTableColumn = document.createElement("th");
	trainerTableColumn.appendChild(document.createTextNode("Trainer ID"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("th");
	trainerTableColumn.appendChild(document.createTextNode("First Name"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("th");
	trainerTableColumn.appendChild(document.createTextNode("Last Name"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("th");
	trainerTableColumn.appendChild(document.createTextNode("Address"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("th");
	trainerTableColumn.appendChild(document.createTextNode("Email"));
	trainerTableRow.appendChild(trainerTableColumn);
	
	trainerTableColumn = document.createElement("th");
	trainerTableColumn.appendChild(document.createTextNode("Phone"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("th");
	trainerTableColumn.appendChild(document.createTextNode("Health Insurance"));
	trainerTableRow.appendChild(trainerTableColumn)

	trainerTable.appendChild(trainerTableRow);
	
	for(var i=0; i<trainers.length; i++) {
		trainerTableRow = document.createElement("tr")
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].id));
		trainerTableRow.appendChild(trainerTableColumn)
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.firstName));
		trainerTableRow.appendChild(trainerTableColumn)
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.lastName));
		trainerTableRow.appendChild(trainerTableColumn)
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.address.street + " " + trainers[i].personalInformation.address.city + ", " 
				+ trainers[i].personalInformation.address.state.stateAbbrev + " " + trainers[i].personalInformation.address.zipcode));
		trainerTableRow.appendChild(trainerTableColumn)
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.email));
		trainerTableRow.appendChild(trainerTableColumn);
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.phoneNumber));
		trainerTableRow.appendChild(trainerTableColumn)
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.healthInsurance.name));
		trainerTableRow.appendChild(trainerTableColumn)

		trainerTable.appendChild(trainerTableRow);
	}
}

function showManagerData() {
	$("#addTrainer").hide();
	$("#modifyTrainer").hide();
	$("#deleteTrainer").hide();
	$("#addCustomer").hide();
	$("#addMachine").hide();
	$(".searchCustomer").hide();
	$(".searchManager").show();
	$("#trainerResults").hide();
	$("#customerResults").hide();
	$("#inventoryResults").hide();
	$("#addManager").show();
	$("#managerResults").show();
	
}

function getAllManagers(){
	$("#managerResults").find(".tableData").remove();
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getAllManagers"
		},
		success: function(managerData) {
			managerData = JSON.parse(managerData)
			populateManagerTable(managerData);
			
		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	});
	
}

function populateManagerTable(managerData) {
	for (var i = 0; i < managerData.length; i++) {
		var manager = managerData[i];
		$("#managerResults table").append("<tr data-id='"+ manager.id + "' class='tableData'> " +
				"<td>" +  manager.id+"</td> " + 
				"<td> " + manager.personalInformation.firstName+ "</td> " + 
				" <td> " + manager.personalInformation.lastName +"</td> " +
				" <td> " + manager.personalInformation.address.street + " "+ manager.personalInformation.address.city+ " " 
				+ manager.personalInformation.address.state.state + " " + manager.personalInformation.address.zipcode +" </td> " +
						"<td> "+ manager.personalInformation.email+ "</td> " + "" +
								"<td>" + manager.personalInformation.phoneNumber + "</td> " +
								" <td>" + manager.personalInformation.healthInsurance.name + "</td></tr>");
	}
}

function showCustomerData() {
	getAllCustomers();
	$("#addManager").hide();
	$(".searchManager").hide();
	$("#addTrainer").hide();
	$("#modifyTrainer").hide();
	$("#deleteTrainer").hide();
	$("#addMachine").hide();
	$("#addCustomer").show();
	$("#managerResults").hide();
	$("#trainerResults").hide();
	$("#inventoryResults").hide();
	$("#customerResults").show();
	$(".searchCustomer").show();
}

function getAllCustomers() {
	
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getAllCustomers"
		},
		success: function(customerData) {
			customerData = JSON.parse(customerData);
			populateCustomerTable(customerData);
			
		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	
	});
}

/**
 * this methods loads the customer table data
 * @param customerData: JSON array of customer data
 * @returns
 */
function populateCustomerTable(customerData){
	$("#customerResults").find(".tableData").remove();
	for (var i = 0; i < customerData.length; i++) {
		var customer = customerData[i];
		$("#customerResults table").append("<tr data-id='" + customer.id + "' class='tableData'> <td><a class='editCustomer' href='#'>Edit</a></td>" +
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
	

function showInventoryData() {
	$("#addManager").hide();
	$(".searchManager").hide();
	$(".searchCustomer").hide();
	$("#addTrainer").hide();
	$("#modifyTrainer").hide();
	$("#addCustomer").hide();
	$("#addMachine").show();
	$("#managerResults").hide();
	$("#trainerResults").hide();
	$("#customerResults").hide();
	$("#inventoryResults").show();
}


/**
 * send ajax call to get Health insurance details and state list before opening modal

 */
$("#addManager").on("click", function(){
	$("#addManagerModal").modal();
});

$("#addManagerModal").on($.modal.BEFORE_OPEN, function () {
	var hiData = getHealthInsurances();
	var states = getStates();
	var hiSelect = $("#managerHIList");
	hiSelect.empty();
	for (var i = 0; i< hiData.length; i++) {
		hiSelect.append("<option data-id='" + hiData[i].id + "'>" + hiData[i].name +  "</option>")
	}
	
	var statesSelect = $("#managerState");
	statesSelect.empty();
	for (var i = 0; i< states.length; i++) {
		statesSelect.append("<option data-id='" + states[i].id + "'>" + states[i].state +  "</option>");
	}
})

$("#addManagerModal").on($.modal.AFTER_CLOSE, function() {
	$("#addManagerModal input").val("");
	$("#addManagerModal select").val($("#addManagerModal select option:first").val())
});

/** add manager when create button is clicked**/

$("#createManagerButton").on("click", function (){
	var postParams = {};
	postParams.fName = $("#managerFName").val();
	postParams.lName = $("#managerLName").val();
	postParams.uName = $("#managerUName").val();
	postParams.password = $("#managerPassword").val();
	postParams.email = $("#managerEmail").val();
	postParams.phone = $("#managerPhone").val();
	postParams.street = $("#managerStreet").val();
	postParams.city = $("#managerCity").val();
	postParams.state = $("#managerState").val();
	postParams.zip = $("#managerZip").val();
	postParams.hiId = $("#managerHIList").find(":selected").data("id");
	postParams.type = "createManager";
	
	
	if(!postParams.fName || !postParams.lName || !postParams.uName || !postParams.password || !postParams.email || !postParams.phone 
			|| !postParams.street || !postParams.city || !postParams.state || !postParams.zip) {
		alert("Could not create manager! Some input fields were missing");
		return;
	}
	
	$.modal.close();
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			var data = JSON.parse(data);
			var manager = data.manager;
			var status = data.status;
			if(status == "failure") {
				alert("Could not create manager! Some input fields were missing");
				return;
			}
			$("#managerResults table").append("<tr data-id='"+ manager.id + "' class='tableData'>"  +
					"<td>" +  manager.id+"</td> " + 
					"<td> " + manager.personalInformation.firstName+ "</td> " + 
					" <td> " + manager.personalInformation.lastName +"</td> " +
					" <td> " + manager.personalInformation.address.street + " "+ manager.personalInformation.address.city+ " " 
					+ manager.personalInformation.address.state.state + " " + manager.personalInformation.address.zipcode +" </td> " +
							"<td> "+ manager.personalInformation.email+ "</td> " + "" +
									"<td>" + manager.personalInformation.phoneNumber + "</td> " +
									" <td>" + manager.personalInformation.healthInsurance.name + "</td></tr>");

		},
		error: function(exception) {
			if(exception.responseText.indexOf("org.hibernate.exception.ConstraintViolationException") >= 0) {
				alert("Username already exists");
				return;
			}
			alert("Error: " + exception);
		}
	});
		
});

$("#searchManagerButton").on("click", function(){
	var params = {};
	params.type = "getSearchManagerResults";
	params.searchText = $("#searchManagerInput").val();
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: params,
		
		success: function(data) {
			var data = JSON.parse(data);
			var managerData = data.results;
			$("#managerResults").find(".tableData").remove();
			populateManagerTable(managerData);
			

		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});
});





$("#addCustomer").on("click", function() {
	$("#createCustomerButton").show();
	$("#createCustomerHeader").show();
	$("#editCustomerButton").hide();
	$("#editCustomerHeader").hide();
	getCustomerModalData();
	$("#customerModal").modal();
})

/**
 * send ajax call to get Health insurance list, state list and membeship list, before opening modal

 */

function getCustomerModalData() {
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getAddCustomerData"
		},
		async: false,
		success: function(data) {
			var hiData = JSON.parse(data.healthInsurances);
			var states = JSON.parse(data.states);
			var membership = JSON.parse (data.membershipType);
			var hiSelect = $("#customerHIList");
			hiSelect.empty();
			for (var i = 0; i< hiData.length; i++) {
				hiSelect.append("<option data-id='" + hiData[i].id + "'>" + hiData[i].name +  "</option>")
			}
			
			var statesSelect = $("#customerState");
			statesSelect.empty();
			for (var i = 0; i< states.length; i++) {
				statesSelect.append("<option data-id='" + states[i].id + "'>" + states[i].state +  "</option>");
			}
			
			var membershipSelect = $("#customerMembership");
			membershipSelect.empty();
			for (var i = 0; i< membership.length; i++) {
				membershipSelect.append("<option data-id='" + membership[i].id + "'>" + membership[i].type +  "</option>");
			}
		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	});
}


/**
 * creates a new customer
 * @returns
 */
$("#createCustomerButton").on("click", function (){
	var postParams = {};
	postParams.fName = $("#customerFName").val();
	postParams.lName = $("#customerLName").val();
	postParams.email = $("#customerEmail").val();
	postParams.phone = $("#customerPhone").val();
	postParams.street = $("#customerStreet").val();
	postParams.city = $("#customerCity").val();
	postParams.state = $("#customerState").val();
	postParams.zip = $("#customerZip").val();
	postParams.healthInsurance = $("#customerHIList").val();
	postParams.membershipStatus = $("#customerMembership").val();
	postParams.type = "createCustomer";
	
	
	if(!postParams.fName || !postParams.lName || !postParams.email || !postParams.phone || !postParams.healthInsurance || !postParams.membershipStatus
			|| !postParams.street || !postParams.city || !postParams.state || !postParams.zip) {
		alert("Could not create customer! Some input fields were missing");
		return;
	}
	
	$.modal.close();
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			var data = JSON.parse(data);
			var customer = data.customer;
			var status = data.status;
			if(status == "failure") {
				alert("Could not create customer! Some input fields were missing");
				return;
			}
			
			$("#customerResults table").append("<tr data-id='" + customer.id + "' class='tableData'> <td><a class='editCustomer' href='#'>Edit</a></td>" +
					"<td>" +  customer.id+"</td> " + 
					"<td> " + customer.personalInformation.firstName+ "</td> " + 
					" <td> " + customer.personalInformation.lastName +"</td> " +
					" <td> " + customer.personalInformation.address.street + " "+ customer.personalInformation.address.city+ " " 
					+ customer.personalInformation.address.state.state + " " + customer.personalInformation.address.zipcode +" </td> " +
							"<td> "+ customer.personalInformation.email+ "</td> " + "" +
									"<td>" + customer.personalInformation.phoneNumber + "</td> " +
									" <td>" + customer.personalInformation.healthInsurance.name + "</td>"+
									" <td>" + customer.membership.type + "</td></tr>");

		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	
	});

});

$("#cancelCustomerButton").on('click', function() {
	$.modal.close();
})

$(document).on('click', '.editCustomer', function() {
	CURRENTLY_EDITED_CUSTOMER_ID = $(this).parents('tr').data('id');
	$("#createCustomerButton").hide();
	$("#createCustomerHeader").hide();
	$("#editCustomerButton").show();
	$("#editCustomerHeader").show();
	getCustomerModalData();
	
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getCustomerById",
			id: CURRENTLY_EDITED_CUSTOMER_ID
				
		},
		success: function(data) {
			data = JSON.parse(data);
			if(data.status == "success") {
				customer = data.customer;
				$("#customerFName").val(customer.personalInformation.firstName);
				$("#customerLName").val(customer.personalInformation.lastName);
				
				$("#customerEmail").val(customer.personalInformation.email);
				$("#customerPhone").val(customer.personalInformation.phoneNumber);
				$("#customerStreet").val(customer.personalInformation.address.street);
				$("#customerCity").val(customer.personalInformation.address.city);
				$("#customerState").val(customer.personalInformation.address.state.state);
				$("#customerZip").val(customer.personalInformation.address.zipcode);
				$("#customerHIList").val(customer.personalInformation.healthInsurance.name);
				$("#customerMembership").val(customer.membership.type);
				$("#customerModal").modal();
				
			} else {
				alert("An error has occured. Could not load customer details.");
			}
		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	});
	
	/**
	 * edits a customer
	 * @returns
	 */
	$("#editCustomerButton").unbind('click').on("click", function (){
		//console.log("editCustomerButton called");
		var postParams = {};
		postParams.id = CURRENTLY_EDITED_CUSTOMER_ID;
		postParams.fName = $("#customerFName").val();
		postParams.lName = $("#customerLName").val();
		postParams.email = $("#customerEmail").val();
		postParams.phone = $("#customerPhone").val();
		postParams.street = $("#customerStreet").val();
		postParams.city = $("#customerCity").val();
		postParams.state = $("#customerState").val();
		postParams.zip = $("#customerZip").val();
		postParams.healthInsurance = $("#customerHIList").val();
		postParams.membershipStatus = $("#customerMembership").val();
		postParams.type = "updateCustomer";
		
		
		if(!postParams.fName || !postParams.lName || !postParams.email || !postParams.phone || !postParams.healthInsurance || !postParams.membershipStatus
				|| !postParams.street || !postParams.city || !postParams.state || !postParams.zip) {
			alert("Could not edit customer! Some input fields were missing");
			return;
		}
		
		$.modal.close();
		
		$.ajax({
			url: "/manager/ui",
			method: "POST",
			data: postParams,
			
			success: function(data) {
				var data = JSON.parse(data);
				var customer = data.customer;
				var status = data.status;
				if(status == "failure") {
					alert("Could not create customer! Some input fields were missing");
					return;
				}
				
				var tr = $("#customerResults table").find("tr[data-id='" + customer.id+"']");
				tr.empty();
				tr.append("<td><a class='editCustomer' href='#'>Edit</a></td>" +
						"<td>" +  customer.id+"</td> " + 
						"<td> " + customer.personalInformation.firstName+ "</td> " + 
						" <td> " + customer.personalInformation.lastName +"</td> " +
						" <td> " + customer.personalInformation.address.street + " "+ customer.personalInformation.address.city+ " " 
						+ customer.personalInformation.address.state.state + " " + customer.personalInformation.address.zipcode +" </td> " +
								"<td> "+ customer.personalInformation.email+ "</td> " + "" +
										"<td>" + customer.personalInformation.phoneNumber + "</td> " +
										" <td>" + customer.personalInformation.healthInsurance.name + "</td>"+
										" <td>" + customer.membership.type + "</td>");
				
				

			},
			error: function(exception) {
				alert("Error: " + exception);
			}
		
		});

	});
});



$("#customerModal").on($.modal.AFTER_CLOSE, function() {
	$("#customerModal input").val("");
	$("#customerModal select").val($("#customerModal select option:first").val())
});


$("#searchCustomerButton").on("click", function(){
	var params = {};
	params.type = "getSearchCustomerResults";
	params.searchText = $("#searchCustomerInput").val();
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: params,
		
		success: function(data) {
			var data = JSON.parse(data);
			var customerData = data.results;
			
			populateCustomerTable(customerData);
			

		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});
})


/* Functions used to create/modify trainers */

var trainers = null;
var trainerId = null;
var trainerFormType = null;

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

function populateTrainerSelectList() {
	trainers = getAllTrainers();
	
	var trainerList = $("#modifyTrainerList");
	trainerList.empty();
	trainerList.append("<option disabled selected value>--Select Trainer--</option>")
	for (var i = 0; i< trainers.length; i++) {
		trainerList.append("<option data-id='" + trainers[i].id + "'>" + trainers[i].personalInformation.firstName 
				+ " " + trainers[i].personalInformation.lastName + "</option>");
	}
}

function modifyTrainerForm() {
	populateTrainerSelectList();
	$("#deleteTrainerSelectFormButton").hide();
	$("#modifyTrainerSelectFormButton").show();
	$("#modifyTrainerSelectForm").modal();
}

function deleteTrainerForm() {
	populateTrainerSelectList();
	$("#deleteTrainerSelectFormButton").show();
	$("#modifyTrainerSelectFormButton").hide();
	$("#modifyTrainerSelectForm").modal();
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
	trainerFormType = "create";
	
	// Set the trainer ID to zero since there is not ID yet
	trainerId = 0;
	
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
	trainerFormType = "update";
	
	// Clear the form
	document.getElementById("trainerForm").reset();
	
	// Get the specific trainer
	var trainerIndex = document.getElementById("modifyTrainerList").selectedIndex -1;
	if(trainerIndex < 0) {
		return;
	}
	var trainer = trainers[trainerIndex];
	trainerId = trainer.id;
	
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

function verifyTrainerForm() {
	if(document.getElementById("modifyPassword").value != document.getElementById("modifyConfirmPassword").value) {
		alert("Passwords are not the same");
		return false;
	}
	if(document.getElementById("modifyPassword").value.length < 8) {
		alert("Passwords must be at least 8 characters");
		return false;
	}
		
	return true;
}

function submitTrainerForm() {
	if(!verifyTrainerForm()) {
		return;
	}
	
	var postParams = {};
	postParams.id = trainerId;
	postParams.firstName = $("#modifyFirstName").val();
	postParams.lastName = $("#modifyLastName").val();
	postParams.userName = $("#modifyUserName").val();
	postParams.password = $("#modifyPassword").val();
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
	
	if(trainerFormType == "create") {
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
				populateTrainerTable();
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
	
	// Get the specific trainer
	var trainerIndex = document.getElementById("modifyTrainerList").selectedIndex -1;
	if(trainerIndex < 0) {
		return;
	}
	var trainer = trainers[trainerIndex];
	
	var postParams = {};
	postParams.id = trainer.id;
	postParams.type = "deleteTrainer";
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			if(data.rc == 0) {
				populateTrainerTable();
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