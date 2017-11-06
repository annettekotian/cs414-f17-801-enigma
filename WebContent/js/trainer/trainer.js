function focusHome() {
	$("#homeLi").css("background", "darkgrey");
	$("#home").show();
	populateHomePage()
	$("#customerTable").hide();
	$(".trainerSearchCustomers").hide();
	$("#exerciseResults").hide();
	$("#addExercise").hide();
}

function populateHomePage() {
	$("#homeFName").text(trainerData.personalInformation.firstName);
	$("#homeLName").text(trainerData.personalInformation.lastName);
	$("#homeEmail").text(trainerData.personalInformation.email);
	$("#homePhone").text(trainerData.personalInformation.phoneNumber);
	$("#homeInsurance").text(trainerData.personalInformation.healthInsurance.name);
	$("#homeAddress").text(trainerData.personalInformation.address.street + ", " + trainerData.personalInformation.address.city + 
			", " + trainerData.personalInformation.address.state.stateAbbrev + " - " + trainerData.personalInformation.address.zipcode);
}

function focusCustomers() {
	populateCustomers();
	$("#home").hide();
	$("#customerTable").show();
	$(".trainerSearchCustomers").show();
	
	$("#exerciseResults").hide();
	$("#addExercise").hide();
}

function focusExercises() {
	$("#exerciseResults").show();
	$("#addExercise").show();
	
	$("#customerTable").hide();
	$("#home").hide();
	$(".trainerSearchCustomers").hide();
	
	populateAllExercises();
}

function focusWorkours() {
	$("#exerciseResults").hide();
	$("#addExercise").hide();
	
	$("#customerTable").hide();
	$("#home").hide();
	$(".trainerSearchCustomers").hide();
}

$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").on("click", function(){
	$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").css("background", "none");
	$(this).css("background", "darkgrey");
	
});

$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").on("mouseenter", function(){
	$("#trainerLi, #managerLi, #customersLi, #inventoryLi").css("color", "black")
	$(this).css("color", "steelblue");
});

$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").on("mouseleave", function(){
	$("#homeLi, #customerLi, #workoutsLi, #exerciseLi").css("color", "black")
	
});

$("#homeLi").on("click", focusHome);

$("#customerLi").on("click", focusCustomers);

$("#exerciseLi").on("click", focusExercises);

$("#workoutsLi").on("click", focusWorkours);


function populateCustomers() {
	var currentUrl = window.location.pathname
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
		data: {
			type: "getCustomers"
		},
		success: function(data, textStatus, jqXHR) {
			data = JSON.parse(data);
			if(data.status == "success") {
				generateCustomersDisplay(data.customers);
			}
			else {
				alert(data.msg);
			}
		},
		error: function(exception) {
			alert("Exception" + exception);
		},
		async: false
	});
}


/**
 * this methods loads the customer table data
 * @param customerData: JSON array of customer data
 * @returns
 */
function generateCustomersDisplay(customerData){
	$("#customerTable").find(".tableData").remove();
	for (var i = 0; i < customerData.length; i++) {
		var customer = customerData[i];
		$("#customerTable table").append("<tr class='tableData'> " +
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

$("#trainerSearchCustomerButton").on("click", function(){
	var params = {};
	params.type = "getSearchCustomerResults";
	params.searchText = $("#trainerSearchCustomerInput").val();
	$.ajax({
		url: "/trainer/ui",
		method: "GET",
		data: params,
		
		success: function(data) {
			var data = JSON.parse(data);
			var customerData = data.results;
			
			generateCustomersDisplay(customerData);
			

		},
		error: function(exception) {
			
			alert("Error: " + exception);
		}
	});
});