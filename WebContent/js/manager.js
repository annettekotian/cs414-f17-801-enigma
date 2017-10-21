function showAdminUI(managerData) {
	$("#managerLi").css("background", "darkgrey");
	$("#managerResults").find(".tableData").remove();
	for (var i = 0; i<managerData.length; i++) {
		var manager = managerData[i];
		$("#managerResults table").append("<tr> " +
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
	
	var trainerTableColumn = document.createElement("td");
	trainerTableColumn.appendChild(document.createTextNode("Trainer ID"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("td");
	trainerTableColumn.appendChild(document.createTextNode("First Name"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("td");
	trainerTableColumn.appendChild(document.createTextNode("Last Name"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("td");
	trainerTableColumn.appendChild(document.createTextNode("Address"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("td");
	trainerTableColumn.appendChild(document.createTextNode("Email"));
	trainerTableRow.appendChild(trainerTableColumn);
	
	trainerTableColumn = document.createElement("td");
	trainerTableColumn.appendChild(document.createTextNode("Phone"));
	trainerTableRow.appendChild(trainerTableColumn)
	
	trainerTableColumn = document.createElement("td");
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
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.address));
		trainerTableRow.appendChild(trainerTableColumn)
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.email));
		trainerTableRow.appendChild(trainerTableColumn);
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.phoneNumber));
		trainerTableRow.appendChild(trainerTableColumn)
		
		trainerTableColumn = document.createElement("td");
		trainerTableColumn.appendChild(document.createTextNode(trainers[i].personalInformation.healthInsurance.description));
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

function showCustomerData() {
	$("#addManager").hide();
	$("#addTrainer").hide();
	$("#addMachine").hide();
	$("#addCustomer").show();
	$("#managerResults").hide();
	$("#trainerResults").hide();
	$("#customerResults").show();
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
 * send ajax call to get Health insurance detaiils before opening modal

 */
$("#addManagerModal").on($.modal.BEFORE_OPEN, function beforeOpeningAddManagerModal () {
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
			//alert("success")

		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	});
	
	
	
});


/**
 get all manager data when add manager modal is closed
 * 
 */

