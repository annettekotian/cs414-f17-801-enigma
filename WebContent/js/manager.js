function showAdminUI() {
	$("#managerLi").css("background", "darkgrey");
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
	$("#addManager").hide();
	$("#addCustomer").hide();
	$("#addMachine").hide();
	$("#managerResults").hide();
	$("#customerResults").hide();
	$("#inventoryResults").hide();
	$("#addTrainer").show();
	$("#trainerResults").show();
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
		url: "/gym-system/manager/ui",
		method: "POST",
		data: {
			type: "getHealthInsurances"
		},
		success: function(data, textStatus, jqXHR) {
			hiData = JSON.parse(data.healthInsurances);
			var select = $("#addManagerHIList");
			for (var i = 0; i< hiData.length; i++) {
				select.append("<option data-id='" + hiData[i].id + "'>" + hiData[i].description +  "</option>")
			}

		},
		error: function(exception) {
			alert("Error: " + exception);
		}
	});
})



/**
 get all manager data when add manager modal is closed
 * 
 */

