function showAdminUI(managerData) {
	$("#managerLi").css("background", "darkgrey");
	$("#managerResults").find(".tableData").remove();
	for (var i = 0; i<managerData.length; i++) {
		var manager = managerData[i];
		$("#managerResults table").append("<tr data-id='"+ manager.id + "' class='tableData'> <td><a class='editManager' href='#'>Edit</a></td>" +
				"<td>" +  manager.id+"</td> " + 
				"<td> " + manager.personalInformation.firstName+ "</td> " + 
				" <td> " + manager.personalInformation.lastName +"</td> " +
				" <td> " + manager.personalInformation.address.street + " "+ manager.personalInformation.address.city+ " " 
				+ manager.personalInformation.address.state.state + " " + manager.personalInformation.address.zipcode +" </td> " +
						"<td> "+ manager.personalInformation.email+ "</td> " + "" +
								"<td>" + manager.personalInformation.phoneNumber + "</td> " +
								" <td>" + manager.personalInformation.healthInsurance.name + "</td></tr>");
	}
	
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
	populateManagerTable();
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
	$("#addCustomer").hide();
	$("#addMachine").hide();
	$("#managerResults").hide();
	$("#customerResults").hide();
	$("#inventoryResults").hide();
	$("#addTrainer").show();
	$("#trainerResults").show();
}

function populateTrainerTable() {
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
	$("#addCustomer").hide();
	$("#addMachine").hide();
	$("#trainerResults").hide();
	$("#customerResults").hide();
	$("#inventoryResults").hide();
	$("#addManager").show();
	$("#managerResults").show();
	
}

function populateManagerTable(){
	$("#managerResults").find(".tableData").remove();
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getAllManagers"
		},
		success: function(managerData) {
			managerData = JSON.parse(managerData)
			for (var i = 0; i < managerData.length; i++) {
				var manager = managerData[i];
				$("#managerResults table").append("<tr data-id='"+ manager.id + "' class='tableData'> > <td><a class='editManager' href='#' >Edit</a></td>" +
						"<td>" +  manager.id+"</td> " + 
						"<td> " + manager.personalInformation.firstName+ "</td> " + 
						" <td> " + manager.personalInformation.lastName +"</td> " +
						" <td> " + manager.personalInformation.address.street + " "+ manager.personalInformation.address.city+ " " 
						+ manager.personalInformation.address.state.state + " " + manager.personalInformation.address.zipcode +" </td> " +
								"<td> "+ manager.personalInformation.email+ "</td> " + "" +
										"<td>" + manager.personalInformation.phoneNumber + "</td> " +
										" <td>" + manager.personalInformation.healthInsurance.name + "</td></tr>");
			}
			
		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	});
	
	
}

function showCustomerData() {
	populateCustomerTable();
	$("#addManager").hide();
	$("#addTrainer").hide();
	$("#addMachine").hide();
	$("#addCustomer").show();
	$("#managerResults").hide();
	$("#trainerResults").hide();
	$("#customerResults").show();
}

function populateCustomerTable() {
	$("#customerResults").find(".tableData").remove();
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getAllCustomers"
		},
		success: function(customerData) {
			customerData = JSON.parse(customerData)
			for (var i = 0; i < customerData.length; i++) {
				var customer = customerData[i];
				$("#customerResults table").append("<tr class='tableData'> " +
						"<td>" +  customer.id+"</td> " + 
						"<td> " + customer.personalInformation.firstName+ "</td> " + 
						" <td> " + customer.personalInformation.lastName +"</td> " +
						" <td> " + customer.personalInformation.address.street + " "+ customer.personalInformation.address.city+ " " 
						+ customer.personalInformation.address.state.state + " " + customer.personalInformation.address.zipcode +" </td> " +
								"<td> "+ customer.personalInformation.email+ "</td> " + "" +
										"<td>" + customer.personalInformation.phoneNumber + "</td> " +
										" <td>" + customer.personalInformation.healthInsurance.name + "</td></tr>");
			}
			
		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	
	});
}
	

function showInventoryData() {
	$("#addManager").hide();
	$("#addTrainer").hide();
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
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getAddManagerData"
		},
		success: function(data) {
			var hiData = JSON.parse(data.healthInsurances);
			var states = JSON.parse(data.states);
			var hiSelect = $("#managerHIList");
			hiSelect.empty();
			for (var i = 0; i< hiData.length; i++) {
				hiSelect.append("<option data-id='" + hiData[i].id + "'>" + hiData[i].name +  "</option>")
			}
			
			var statesSelect = $("#managerState");
			for (var i = 0; i< states.length; i++) {
				statesSelect.append("<option data-id='" + states[i].id + "'>" + states[i].state +  "</option>");
			}

		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	});
})


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
		alert("incomplete input!");
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
			$("#managerResults table").append("<tr data-id='"+ manager.id + "' class='tableData'> <td><a class='editManager' href='#'>Edit</a></td>"  +
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

/************ send ajax call to get the clicked manager details*************/
$(document.body).on("click", '.editManager', function () {
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getEditManagerData",
			id: $(this).parents("tr").data("id"),
		},
		success: function(data) {
			data = JSON.parse(data);
			var manager = data.manager;
			var hiData = data.healthInsurances
			var states = data.states;
			var hiSelect = $("#editManagerHIList");
			for (var i = 0; i< hiData.length; i++) {
				hiSelect.append("<option data-id='" + hiData[i].id + "'>" + hiData[i].name +  "</option>")
			}

			var statesSelect = $("#editManagerState");
			for (var i = 0; i< states.length; i++) {
				statesSelect.append("<option data-id='" + states[i].id + "'>" + states[i].state +  "</option>");
			}
			
			$("#editManagerFName").val(manager.personalInformation.firstName);
			$("#editManagerLName").val(manager.personalInformation.lastName);
			$("#editManagerUName").val(manager.user.username);
			$("#editManagerPassword").val(manager.user.password);
			$("#editManagerEmail").val(manager.personalInformation.email);
			$("#editManagerPhone").val(manager.personalInformation.phoneNumber);
			$("#editManagerStreet").val(manager.personalInformation.address.street);
			$("#editManagerCity").val(manager.personalInformation.address.city);
			$("#editManagerState").val(manager.personalInformation.address.state.state);
			$("#editManagerZip").val(manager.personalInformation.address.zipcode);
			$("#editManagerHIList").val(manager.personalInformation.healthInsurance.name);
			
			$("#editManagerModal").modal();

		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	});
})

$("#editManagerButton, #cancelManagerChanges").on("click", function() {
	$.modal.close()
})

$("#addCustomer").on("click", function() {
	$("#addCustomerModal").modal();
})

/**
 * send ajax call to get Health insurance list, state list and membeship list, before opening modal

 */

$("#addCustomerModal").on($.modal.BEFORE_OPEN, function () {
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getAddCustomerData"
		},
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
			for (var i = 0; i< states.length; i++) {
				statesSelect.append("<option data-id='" + states[i].id + "'>" + states[i].state +  "</option>");
			}
			
			var membershipSelect = $("#customerMembership");
			for (var i = 0; i< membership.length; i++) {
				membershipSelect.append("<option data-id='" + membership[i].id + "'>" + membership[i].type +  "</option>");
			}
		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	});
});


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
		alert("incomplete input!");
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
			
			$("#customerResults table").append("<tr class='tableData'> " +
					"<td>" +  customer.id+"</td> " + 
					"<td> " + customer.personalInformation.firstName+ "</td> " + 
					" <td> " + customer.personalInformation.lastName +"</td> " +
					" <td> " + customer.personalInformation.address.street + " "+ customer.personalInformation.address.city+ " " 
					+ customer.personalInformation.address.state.state + " " + customer.personalInformation.address.zipcode +" </td> " +
							"<td> "+ customer.personalInformation.email+ "</td> " + "" +
									"<td>" + customer.personalInformation.phoneNumber + "</td> " +
									" <td>" + customer.personalInformation.healthInsurance.name + "</td></tr>");

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

var formType = "";

function displayNewTrainerForm() {
	formType = "Trainer";
	displayEmployeeForm();
}

function clearForms() {
	// Reset all the input fields
	document.getElementById("contactInformationForm").reset();
	document.getElementById("addressInformationForm").reset();
	document.getElementById("healthInsuranceForm").reset();
	document.getElementById("userCredentialsForm").reset();
}

function displayEmployeeForm() {
	clearForms();
	
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getAddManagerData"
		},
		success: function(data) {
			var hiData = JSON.parse(data.healthInsurances);
			var states = JSON.parse(data.states);
			var hiSelect = $("#healthInsurance");
			hiSelect.empty();
			hiSelect.append("<option data-id='0'>--Other--</option>");
			for (var i = 0; i< hiData.length; i++) {
				hiSelect.append("<option data-id='" + hiData[i].id + "'>" + hiData[i].name +  "</option>")
			}
			
			var statesSelect = $("#state");
			statesSelect.empty();
			for (var i = 0; i< states.length; i++) {
				statesSelect.append("<option data-id='" + states[i].id + "'>" + states[i].state +  "</option>");
			}

		},
		error: function(exception) {
			alert("Error: " + exception);
		},
		async: false
	});
	
	displayContactInformation();
}

function displayContactInformation() {
	document.getElementById("contactInformationHeader").innerText = formType + " Contact Information";
	$("#addContactInformation").modal();
}

function displayAddressForm() {	
	document.getElementById("addressInformationHeader").innerText = formType + " Address Information";
	$("#addAddressInformation").modal();
}

function displayHealthInsuranceForm() {
	document.getElementById("healthInsuranceHeader").innerText = formType + " Health Insurance Information";
	$("#addHealthInsurance").modal();
}

function checkNewHealthInsurance() {
	if(document.getElementById("healthInsurance").selectedIndex == 0) {
		document.getElementById("otherHealthInsurance").value = "";
		document.getElementById("otherHealthInsurance").disabled = false;
	}
	else {
		document.getElementById("otherHealthInsurance").disabled = true;
	}
}

function disaplyNextForm() {
	if(formType != "customer") {
		displayUserCredentialForm();
	}
}

function displayUserCredentialForm() {
	document.getElementById("userCredentials").innerText = formType + " User Credentials";
	document.getElementById("password").value = "";
	$("#addUserCredentials").modal();
}

function submitEmployeeForm() {
	var postParams = {};
	postParams.firstName = $("#firstName").val();
	postParams.lastName = $("#lastName").val();
	postParams.userName = $("#userName").val();
	postParams.password = $("#password").val();
	postParams.email = $("#email").val();
	postParams.phone = $("#phoneNumber").val();
	postParams.street = $("#street").val();
	postParams.city = $("#city").val();
	postParams.state = $("#state").val();
	postParams.zip = $("#zipcode").val();
	
	if(document.getElementById("healthInsurance").selectedIndex == 0) {
		postParams.healthInsurance = $("#otherHealthInsurance").val();
	}
	else {
		postParams.healthInsurance = $("#healthInsurance").val();
	}
	
	if(formType == "Trainer") {
		postParams.type = "createTrainer";
	}
	
	
	$.modal.close();
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			if(data.rc == 0) {
				if(formType == "Trainer") {
					populateTrainerTable();
				}
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