function focusHome() {
	document.getElementById("home").style.display = "block";
	document.getElementById("customerTable").style.display = "none";
	$(".trainerSearchCustomers").hide();
}

function focusCustomers() {
	document.getElementById("home").style.display = "none";
	document.getElementById("customerTable").style.display = "block";
	$(".trainerSearchCustomers").show();
}

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
	focusCustomers();
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
})


