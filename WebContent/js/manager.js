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

function showTrainerElements() {
	$("#addTrainer").show();
	$("#modifyTrainer").show();
	$("#trainerResults").show();
	$("#deleteTrainer").show();
	$("#addQualification").show();
	$("#addWorkHours").show();
	$("#removeQualification").show();
	$(".searchTrainer").show();
	$("#deleteWorkHours").show();
}

function hideTrainerElements() {
	$("#addTrainer").hide();
	$("#modifyTrainer").hide();
	$("#trainerResults").hide();
	$("#deleteTrainer").hide();
	$("#addQualification").hide();
	$("#addWorkHours").hide();
	$("#removeQualification").hide();
	$(".searchTrainer").hide();
	$("#deleteWorkHours").hide();
}

function showTrainerData() {
	showTrainerElements();
	
	$("#addManager").hide();
	$(".searchManager").hide();
	$("#addCustomer").hide()
	$(".searchCustomer").hide();
	$("#addMachine").hide();
	$(".searchMachine").hide();
	$("#managerResults").hide();
	$("#customerResults").hide();
	$("#inventoryResults").hide();
	
	populateAllTrainers();
}

function showManagerData() {
	hideTrainerElements();
	
	$("#addCustomer").hide();
	$("#addMachine").hide();
	$(".searchCustomer").hide();
	$(".searchManager").show();
	$(".searchTrainer").hide();
	$(".searchMachine").hide();
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
	hideTrainerElements();
	getAllCustomers();
	$("#addManager").hide();
	$(".searchManager").hide();
	$("#addMachine").hide();
	$(".searchMachine").hide();
	$("#addCustomer").show();
	$("#managerResults").hide();
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
		$("#customerResults table").append("<tr data-id='" + customer.id + "' class='tableData'> <td><a class='editCustomer' href='#'>Edit</a>" +
				"<span>&nbsp;</span><a class='deleteCustomer' href='#'>Delete</a></td>" +
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
	hideTrainerElements();
	$("#addManager").hide();
	$(".searchManager").hide();
	$(".searchCustomer").hide();
	$("#addQualification").hide();
	$("#addWorkHours").hide();
	$("#addTrainer").hide();
	$("#deleteTrainer").hide();
	$("#modifyTrainer").hide();
	$("#addCustomer").hide();
	$("#addMachine").show();
	$(".searchMachine").show();
	$("#managerResults").hide();
	$("#customerResults").hide();
	// send ajax call to get inventory data
	
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: {
			type: "getInventory"
		},
		success: function(data) {
			data = JSON.parse(data);
			var machineList = data.machines; 
			populateInventoryTable(machineList);
					
		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	
	});
	
	$("#inventoryResults").show();
}


function populateInventoryTable(machineList){
	$("#inventoryResults .tableData").remove();
	for (var i = 0 ; i<machineList.length; i++) {
		var machine = machineList[i];
		$("#inventoryResults table").append("<tr class='tableData' data-id='"+ machine.id + "'>" 
				+"<td><a class='editMachine' href='#'>Edit</a><span>&nbsp;</span><a class='deleteMachine' href='#'>Delete</a></td>"
				
				 +"<td>" + machine.id + "</td>"
				+ "<td>" + machine.name + "</td>" 
				+ "<td><img src='/machineImages/"+ machine.pictureLocation + "?v="+ new Date().getTime()+"'></img></td>"
				+ "<td>"+ machine.quantity + "</td></tr>");
	}
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
	hiSelect.append("<option>--Other--</option>")
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


$("#managerHIList").on("change", function() {
	if($(this).find(":selected").index()>0 ) {
		$("#managerOtherHI").attr("disabled", true);
	} else if($(this).find(":selected").index() == 0) {
		$("#managerOtherHI").attr("disabled", false);
	}
}), 
/** add manager when create button is clicked**/

$("#createManagerButton").on("click", function (){
	var postParams = {};
	postParams.fName = $("#managerFName").val();
	postParams.lName = $("#managerLName").val();
	postParams.uName = $("#managerUName").val();
	postParams.password = $("#managerPassword").val();
	postParams.confirmPassword = $("#confirmManagerPassword").val();
	postParams.email = $("#managerEmail").val();
	postParams.phone = $("#managerPhone").val();
	postParams.street = $("#managerStreet").val();
	postParams.city = $("#managerCity").val();
	postParams.state = $("#managerState").val();
	postParams.zip = $("#managerZip").val();
	postParams.type = "createManager";
	
	var hiIndex = $("#managerHIList").find(":selected").index();
	if(hiIndex == 0) {
		postParams.insurance = $("#managerOtherHI").val();
	} else {
		postParams.insurance = $("#managerHIList").val();
	}
	
	if(postParams.password != postParams.confirmPassword) {
		alert("Passwords entered are not the same!");
		return;
	}
	
	if(postParams.password.length < 8) {
		alert("Password must be atleast 8 characters in length");
		return;
	}
	
	
	if(!postParams.fName || !postParams.lName || !postParams.uName || !postParams.password || !postParams.email || !postParams.phone 
			|| !postParams.street || !postParams.city || !postParams.state || !postParams.zip || !postParams.insurance) {
		alert("Could not create manager! Some input fields were missing");
		return;
	}
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			$.modal.close();
			var data = JSON.parse(data);
			var manager = data.manager;
			var status = data.status;
			if(status != "success") {
				alert("Could not create manager! Some error occured");
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
			if(exception.responseText.indexOf("Missing Input") >=0) {
				alert("Could not create manager! Some input fields were missing");
				return;
			}
			
			if(exception.responseText.indexOf("Password error") >=0) {
				alert("Passwords entered are not the same!");
				return;
			}
			
			if(exception.responseText.indexOf("Password short")>=0) {
				alert("Password must be atleast 8 characters in length");
				return;
			}
			
			if(exception.responseText.indexOf(" javax.mail.internet.AddressException") >=0 ) {
				alert("Invalid email address");
				return;
			}
			if(exception.responseText.indexOf("org.hibernate.exception.ConstraintViolationException") >= 0) {
				alert("Username already exists");
				return;
			}
			if(exception.responseText.indexOf("Zipcode") >= 0) {
				alert("Zipcode must be 5 digits");
				return;
			}
			if(exception.responseText.indexOf("Phone") >= 0) {
				alert("Phone number must be 10 digits in format ###-###-####");
				return;
			}
			alert("Error: " + exception);
		}
	});
		
});

$("#searchManagerButton").on("click", function(){
	getSearchManagerResults($("#searchManagerInput").val());
});

$("#resetManagerSearch").on("click", function(){
	$("#searchManagerInput").val("");
	getSearchManagerResults("");
});

function getSearchManagerResults(keywords) {
	var params = {};
	params.type = "getSearchManagerResults";
	params.searchText = keywords;
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
}


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
			type: "getCustomerModalData"
		},
		async: false,
		success: function(data) {
			var hiData = JSON.parse(data.healthInsurances);
			var states = JSON.parse(data.states);
			var membership = JSON.parse (data.membershipType);
			var hiSelect = $("#customerHIList");
			hiSelect.empty();
			hiSelect.append("<option> --Other--</option>");
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

$("#customerHIList").on("change", function() {
	if($(this).find(":selected").index()>0 ) {
		$("#customerOtherHI").attr("disabled", true);
	} else if($(this).find(":selected").index() == 0) {
		$("#customerOtherHI").attr("disabled", false);
	}
}), 


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
	postParams.membershipStatus = $("#customerMembership").val();
	var hiIndex = $("#customerHIList").find(":selected").index();
	if(hiIndex == 0) {
		postParams.healthInsurance = $("#customerOtherHI").val();
	} else {
		postParams.healthInsurance = $("#customerHIList").val();
	}
	
	
	postParams.type = "createCustomer";
	
	
	if(!postParams.fName || !postParams.lName || !postParams.email || !postParams.phone || !postParams.healthInsurance || !postParams.membershipStatus
			|| !postParams.street || !postParams.city || !postParams.state || !postParams.zip) {
		alert("Could not create customer! Some input fields were missing");
		return;
	}
	
	
	
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: postParams,
		
		success: function(data) {
			$.modal.close();
			var data = JSON.parse(data);
			var customer = data.customer;
			var status = data.status;
			if(status == "failure") {
				alert("Could not create customer! Some input fields were missing");
				return;
			}
			
			$("#customerResults table").append("<tr data-id='" + customer.id + "' class='tableData'> <td><a class='editCustomer' href='#'>Edit</a>" +
					"<span>&nbsp;</span><a class='deleteCustomer' href='#'>Delete</a></td>" +
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
			if(exception.responseText.indexOf("Missing input") >= 0) {
				alert("Could not create customer! Some input fields were missing");
				return;
			}
			if (exception.responseText.indexOf(" javax.mail.internet.AddressException") >=0 ) {
				alert("Invalid email address");
				return;
			}
			if(exception.responseText.indexOf("Zipcode") >= 0) {
				alert("Zipcode must be 5 digits");
				return;
			}
			if(exception.responseText.indexOf("Phone") >= 0) {
				alert("Phone number must be 10 digits in format ###-###-####");
				return;
			}
			alert("Error" + exception);
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
				$("#customerOtherHI").attr("disabled", true);
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
		var hiIndex = $("#customerHIList").find(":selected").index();
		if(hiIndex == 0) {
			postParams.healthInsurance = $("#customerOtherHI").val();
		} else {
			postParams.healthInsurance = $("#customerHIList").val();
		}
		postParams.membershipStatus = $("#customerMembership").val();
		postParams.type = "updateCustomer";
		
		
		if(!postParams.fName || !postParams.lName || !postParams.email || !postParams.phone || !postParams.healthInsurance || !postParams.membershipStatus
				|| !postParams.street || !postParams.city || !postParams.state || !postParams.zip) {
			alert("Could not edit customer! Some input fields were missing");
			return;
		}
		
		
		$.ajax({
			url: "/manager/ui",
			method: "POST",
			data: postParams,
			
			success: function(data) {
				$.modal.close();
				var data = JSON.parse(data);
				var customer = data.customer;
				var status = data.status;
				if(status == "failure") {
					alert("Could not create customer! Some input fields were missing");
					return;
				}
				
				var tr = $("#customerResults table").find("tr[data-id='" + customer.id+"']");
				tr.empty();
				tr.append("<td><a class='editCustomer' href='#'>Edit</a><span>&nbsp;</span><a class='deleteCustomer' href='#'>Delete</a></td>" +
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
				if(exception.responseText.indexOf("Missing input") >= 0) {
					alert("Could not create customer! Some input fields were missing");
					return;
				}else if (exception.responseText.indexOf(" javax.mail.internet.AddressException") >=0 ) {
					alert("Invalid email address");
					return;
				}else if(exception.responseText.indexOf("Zipcode") >= 0) {
					alert("Zipcode must be 5 digits");
					return;
				}
				else if(exception.responseText.indexOf("Phone") >= 0) {
					alert("Phone number must be 10 digits in format ###-###-####");
					return;
				}
				alert("Error" + exception);
			}
		
		});

	});
});

$(document).on("click", ".deleteCustomer", function(){
	var id = $(this).parents("tr").data('id');
	var deleteBool = confirm("Do you want to delete the customer having id " + id + "?");
	if(deleteBool === false) {
		return;
	}
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: {
			"type": "deleteCustomer",
			"id": id
		},
		
		success: function(data) {
			data = JSON.parse(data);
			if(data.status == "success") {
				$("tr[data-id='" + id + "']").remove();
			}
			
			

		},
		error: function(exception) {
			if(exception.responseText.indexOf("Missing input") >= 0) {
				alert("Could not create customer! Some input fields were missing");
				return;
			}
			else {
				alert("Error" + exception);
			}
		}
	
	});

})


$("#customerModal").on($.modal.AFTER_CLOSE, function() {
	$("#customerModal input").val("");
	$("#customerModal select").val($("#customerModal select option:first").val())
});


$("#searchCustomerButton").on("click", function(){
	
	getSearchCustomerResults($("#searchCustomerInput").val());
});

$("#resetCustomerSearch").on("click", function(){
	$("#searchCustomerInput").val("");
	getSearchCustomerResults("");
}) 

function getSearchCustomerResults(keywords) {
	var params = {};
	params.type = "getSearchCustomerResults";
	params.searchText = keywords;
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
}


$("#addMachine").on("click", function(){
	$("#addMachineModal").modal();
});

$("#addMachinePic").on("change", function(){

	if (this.files && this.files[0]) {
	    var reader = new FileReader();

	    reader.onload = function(e) {
	      $('#addMachinePreview').attr('src', e.target.result);
	    }

	    reader.readAsDataURL(this.files[0]);
	  }
})

$("#addMachineForm").on("submit", function(e){
	e.preventDefault();
	var data = new FormData(this);
	$.ajax({
		url: "/manager/ui",
		type: "POST",
		data:  data,
		contentType: false,
		cache: false,
		processData:false,
		success: function(data){
			$.modal.close();
			data = JSON.parse(data);
			var machine = data.machine;
			$("#inventoryResults table").append("<tr class='tableData' data-id='"+ machine.id + "' class='.tableData'>" 
					+ "<td><a class='editMachine' href='#'>Edit</a><span>&nbsp;</span><a class='deleteMachine' href='#'>Delete</a></td>"
					+"<td>" + machine.id + "</td>"
					+ "<td>" + machine.name + "</td>" 
					+ "<td><img src='/machineImages/"+ machine.pictureLocation + "?v="+new Date().getTime()+"'></img></td>"
					+ "<td>"+ machine.quantity + "</td></tr>");
		},
		error: function(error){
			if(error.responseText.indexOf("missing input") >= 0) {
				alert("Could not add machine to the inventory. Some input fields were missing");
			} else if(error.responseText.indexOf("not image file") >= 0){
				alert("The file selected is not an image file");
			} else if (error.responseText.indexOf("org.hibernate.exception.ConstraintViolationException") >= 0){
				alert("Machine name already exists. Please use another name");
			} else {
				alert("Error: " + error);
			}
		} 
	});
})


$("#addMachineModal").on($.modal.AFTER_CLOSE, function() {
	$("#addMachineName, #addMachineQuantity, #addMachinePic").val("");
	$("#addMachinePreview").attr("src", "#");
});

$(document).on("click", ".editMachine", function(){	
	 CURRENTLY_EDITED_MACHINE_ID = $(this).parents("tr").data("id");
	 $("#machineId").val(CURRENTLY_EDITED_MACHINE_ID);
		$.ajax({
			url: "/manager/ui",
			type: "GET",
			data:  {
				type: "getMachineById",
				id: CURRENTLY_EDITED_MACHINE_ID
			},
			
			success: function(data){
				data = JSON.parse(data);
				var machine = data.machine
				$("#editMachineName").val(machine.name);
				$("#editMachineQuantity").val(machine.quantity);
				$("#editMachinePreview").attr("src", "/machineImages/"+ machine.pictureLocation);
				$("#editMachineModal").modal();
				
			},
			error: function(error){
				alert("Error: " + error);
			} 
		})
});
$("#cancelMachineButton, #cancelEditMachine").on("click", function(e){
	e.preventDefault();
	$.modal.close();
})


$("#editMachinePic").on("change", function(){
	if (this.files && this.files[0]) {
	    var reader = new FileReader();

	    reader.onload = function(e) {
	      $('#editMachinePreview').attr('src', e.target.result);
	    }

	    reader.readAsDataURL(this.files[0]);
	  }
})

$(document).on("click", ".deleteMachine", function(){
	var id = $(this).parents("tr").data('id');
	var deleteBool = confirm("Do you want to delete the customer having id " + id + "?");
	if(deleteBool === false) {
		return;
	}
	$.ajax({
		url: "/manager/ui",
		method: "POST",
		data: {
			"type": "deleteMachine",
			"id": id
		},
		
		success: function(data) {
			data = JSON.parse(data);
			if(data.status == "success") {
				$("tr[data-id='" + id + "']").remove();
			}
			
			

		},
		error: function(exception) {
			
			alert("Error" + exception);
		}
	
	});
})


$("#editMachineForm").on("submit", function(e){

	console.log("here");
	e.preventDefault();
	var data = new FormData(this);
	$.ajax({
		url: "/manager/ui",
		type: "POST",
		data:  data,
		contentType: false,
		cache: false,
		processData:false,
		success: function(data){
			$.modal.close();
			data = JSON.parse(data);
			var machine = data.machine
			
			var tr = $("#inventoryResults table").find("tr[data-id='" + machine.id+"']");
			tr.empty();
			var machine = data.machine;
			tr.append("<td><a class='editMachine' href='#'>Edit</a><span>&nbsp;</span><a class='deleteMachine' href='#'>Delete</a></td>"
					+"<td>" + machine.id + "</td>"
					+ "<td>" + machine.name + "</td>" 
					+ "<td><img src='/machineImages/"+ machine.pictureLocation + "?v="+ new Date().getTime()+"'></img></td>"
					+ "<td>"+ machine.quantity + "</td>");
		},
		error: function(error){
			if(error.responseText.indexOf("missing input") >= 0) {
				alert("Could not add machine to the inventory. Some input fields were missing");
			} else if(error.responseText.indexOf("not image file") >= 0){
				alert("The file selected is not an image file");
			} else if (error.responseText.indexOf("org.hibernate.exception.ConstraintViolationException") >= 0){
				alert("Machine name already exists. Please use another name");
			} else {
				alert("Error: " + error);
			}
		} 
	});
})

$("#searchMachineButton").on("click", function(){
	getSearchMachineResults($("#searchMachineInput").val());
});

$("#resetMachineSearch").on("click", function(){
	$("#searchMachineInput").val("");
	getSearchMachineResults("");
});

function getSearchMachineResults(keywords) {
	var params = {};
	params.type = "getSearchMachineResults";
	params.searchText = keywords;
	$.ajax({
		url: "/manager/ui",
		method: "GET",
		data: params,
		
		success: function(data) {

			data = JSON.parse(data);
			var machineList = data.machines;
			
			populateInventoryTable(machineList);
		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});
}


