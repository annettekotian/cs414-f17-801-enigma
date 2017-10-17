function showAdminUI() {
	$("#managerLi").css("background", "darkgrey");
	$("#addTrainer").hide();
	$("#trainerResults").hide();
	$("#managerResults").show();
}

function showManagerUI() {
	$("#trainerLi").css("background", "darkgrey");
	$("#managerLi").hide();
	$("#addManager").hide();
	$("#trainerResults").show();
	$("#managerResults").hide();
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

function showTrainerData() {
	$("#addManager").hide();
	$("#managerResults").hide();
	$("#addTrainer").show();
	$("#trainerResults").show();
}

function showManagerData() {
	$("#addTrainer").hide();
	$("#trainerResults").hide();
	$("#addManager").show();
	$("#managerResults").show();
	
}