<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enigma</title>
<link rel="stylesheet" type="text/css" href="/css/gymSystem.css">
<link rel="stylesheet" type="text/css" href="/css/jquery.modal.min.css">
<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/js/jquery.modal.min.js"></script>
<script src="/js/globalVariables.js"></script>
<script src="/js/trainerTab.js"></script>
<script src="/js/systemFunctions.js"></script>

</head>
<body>
<div style="width:100%; height:40px; color:white;background:lightskyblue; text-align:center">
	<ul id="topHeader">
	<li id="weclcomeLi">Welcome User!</li>
	
	<li id="logoutLink"><a href="/logout">Logout</a></li>
	</ul>
</div>
	<div id="menuBarDiv">
	<ul id="menuBar">
		<li id="managerLi" class="menuLi">Managers</li>
		<li id="trainerLi" class="menuLi">Trainers</li>
		<li id="customersLi">Customers</li>
		<li id="inventoryLi">Inventory</li>
	</ul>
	</div>
	
	<button id="addManager" class="addButtons">Add Manager</button>
	<input id = "searchManagerInput" class="searchManager" type="text" placeholder="enter name, address etc" /> 
	<button id="searchManagerButton" class="searchManager">Search</button>
	<button id = "resetManagerSearch" class="searchManager">Reset</button>
	
	<button id="addTrainer" class="addButtons" onclick="createTrainerForm()">Add Trainer</button>
	<button id="modifyTrainer" class="addButtons" onclick="modifyTrainerInformation()" disabled>Modify Trainer</button>
	<button id="deleteTrainer" class="addButtons" onclick="deleteTrainer()" disabled>Delete Trainer</button>
	<button id="addQualification" class="addButtons" onclick="addTrainerQualification()" disabled>Add Qualification</button>
	<button id="removeQualification" class="addButtons" onclick="removeTrainerQualificationForm()" disabled>Remove Qualification</button>
	<button id="addWorkHours" class="addButtons" onclick="addTrainerWorkHours()" disabled>Add Work Hours</button>
	<button id="deleteWorkHours" class="addButtons" onclick="deleteTrainerWorkHours()" disabled>Delete Work Hours</button>
	<input id ="searchTrainerInput" class="searchTrainer" type="text" placeholder="Search"/> 
	<button id="searchTrainerButton" class="searchTrainer" onclick="populateSearchTrainers()">Search</button>
	<button id="searchTrainerButton" class="searchTrainer" onclick="resetSearchTrainers()">Reset</button>
	<button id="addCustomer" class="addButtons">Add Customer</button>
	<input id = "searchCustomerInput" class="searchCustomer" type="text" placeholder="enter name, address etc" /> 
	<button id="searchCustomerButton" class="searchCustomer">Search</button>
	<button id = "resetCustomerSearch" class="searchCustomer">Reset</button>
	<button id="addMachine" class="addButtons">Add Equipment</button>
	<input id = "searchMachineInput" class="searchMachine" type="text" placeholder="enter name, quantity etc" /> 
	<button id="searchMachineButton" class="searchMachine">Search</button>
	<button id = "resetMachineSearch" class="searchMachine">Reset</button>
	<div id="managerResults">
		<table>
			<tr class = "tableHeader">
				
				<th>
					Id
				</th>
				<th>
					First Name
				</th>
				<th>
					Last Name
				</th>
				<th>
					Address
				</th>
				<th>
					Email
				</th>
				<th>
					Phone No
				</th>
				<th>
					Health Insurance
				</th>
				
			</tr>
			
		</table>
	</div>
	
	<div id="trainerResults">
		<table id="trainerTable">
			<tr>
				<th>Trainer Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Address</th>
				<th>Email</th>
				<th>Phone Number</th>
				<th>Health Insurance</th>
				<th>Qualifications</th>
				<th>Work Schedule</th>
			</tr>
		</table>
	</div>

	<div id="customerResults">
		<table>
			<tr class = "tableHeader">
				<th ></th>
				<th>
					Id
				</th>
				<th>
					First Name
				</th>
				<th>
					Last Name
				</th>
				<th>
					Address
				</th>
				<th>
					Email
				</th>
				<th>
					Phone No
				</th>
				<th>
					Health Insurance
				</th>
				<th>
					Membership
				</th>
				
			</tr>			
			
		</table>
	</div>

	<div id="inventoryResults">
		<table>
			<tr>
				<th>
					
				</th>
				<th>
					Id
				</th>
				<th>
					Name
				</th>
				<th>
					Picture
				</th>
				<th>
					Quantity
				</th>
			
				
			</tr>
			
		</table>
	</div>

<div id="modifyTrainerForm" class="modal">
	<h3 id="modifyTrainerHeader">Modify Trainer</h3>
	<h3 id="newTrainerHeader">New Trainer</h3>
	<form id="trainerForm">
		<label>First Name</label><br>
		<input id="modifyFirstName" type="text"><br>
		<label>Last Name</label><br>
		<input id="modifyLastName" type="text"><br>
		<label>Phone Number</label><br>
		<input id="modifyPhoneNumber" type="text"><br>
		<label>Email Address</label><br>
		<input id="modifyEmail" type="text"><br>
		<label>Street</label><br>
		<input id="modifyStreet" type="text"><br>
		<label>City</label><br>
		<input id="modifyCity" type="text"><br>
		<label>State</label><br>
		<select id="modifyState" size="1"></select><br>
		<label>Zipcode</label><br>
		<input id="modifyZipcode" type="text"><br>
		<label>Health Insurance</label><br>
		<select id="modifyHealthInsurance" size="1" onchange="checkNewHealthInsurance()"></select><br>
		<label>Other Health Insurance</label><br>
		<input id="modifyOtherHealthInsurance" type="text" disabled><br>
		<label>User Name</label><br>
		<input id="modifyUserName" type="text"><br>
		<label>Password</label><br>
		<input id="modifyPassword" type="password"><br>
		<label>Confirm Password</label><br>
		<input id="modifyConfirmPassword" type="password"><br>
	</form>
	<button onclick="submitTrainerForm()">Submit</button>
</div>

<div id="addQualificationForm" class="modal">
	<h3>Add Trainer Qualification</h3>
	<form id="qualificationForm">
		<label>Qualifications</label><br>
		<select id="qualifications" onchange="checkNewQualification()"></select><br>
		<label>New Qualification</label><br>
		<input id="newQualification" type="text" disabled><br>
	</form>
	<button onclick="submitTrainerQualification()">Submit</button>
</div>

<div id="removeQualificationModal" class="modal">
	<h3>Remove Trainer Qualification</h3>
	<form id="removeQualificationForm">
		<label>Qualifications</label><br>
		<select id="removeQualificationsList"></select><br>
	</form>
	<button onclick="removeTrainerQualification()">Submit</button>
</div>

<div id="addWorkHoursForm" class="modal">
	<h3>Add Trainer Work Hours</h3>
	<form>
		<label>Start Year</label><br>
		<select id="startYear" onchange="populateStartDay()"></select><br>
		<label>Start Month</label><br>
		<select id="startMonth" onchange="populateStartDay()">
			<option>January</option>
			<option>February</option>
			<option>March</option>
			<option>April</option>
			<option>May</option>
			<option>June</option>
			<option>July</option>
			<option>August</option>
			<option>September</optione>
			<option>October</option>
			<option>November</option>
			<option>December</option>
		</select><br>
		<label>Start Day</label><br>
		<select id="startDay"></select><br>
		<label>Start Time</label><br>
		<label>Hour</label><input id="startHour" type="number" value="1" min="1" max="12"><br>
		<label>Minute</label><input id="startMinute" type="number" value="1" min="0" max="59"><br>
		<select id="startPeriod">
			<option>AM</option>
			<option>PM</option>
		</select><br>
		
		<label>End Year</label><br>
		<select id="endYear" onchange="populateEndDay()"></select><br>
		<label>End Month</label><br>
		<select id="endMonth" onchange="populateEndDay()">
			<option>January</option>
			<option>February</option>
			<option>March</option>
			<option>April</option>
			<option>May</option>
			<option>June</option>
			<option>July</option>
			<option>August</option>
			<option>September</optione>
			<option>October</option>
			<option>November</option>
			<option>December</option>
		</select><br>
		<label>End Day</label><br>
		<select id="endDay"></select><br>
		<label>End Time</label><br>
		<label>Hour</label><input id="endHour" type="number" value="1" min="1" max="12"><br>
		<label>Minute</label><input id="endMinute" type="number" value="1" min="0" max="59"><br>
		<select id="endPeriod">
			<option>AM</option>
			<option>PM</option>
		</select><br>
	</form>
	<button onclick="submitWorkHours()">Submit</button>
</div>

<div id="removeWorkHoursModal" class="modal">
	<h3>Remove Trainer Work Hours</h3>
	<form id="removeWorkHoursForm">
		<label>Qualifications</label><br>
		<select id="removeWorkHoursList"></select><br>
	</form>
	<button onclick="deleteWorkHours()">Submit</button>
</div>

	<div id="addManagerModal" class="modal">
		<h3>Add a Manager</h3>
		<div id="inputFormDiv">
			<div id="managerFirstNameDiv" class="managerInputDiv">
				<label>First Name</label> <br/>
				<input id="managerFName" class="managerInput" type="text" placeholder=" Enter first name"/> 
			</div>
			
			<div id="managerLastNameDiv" class="managerInputDiv">
				<label>Last Name</label><br/>
				<input id="managerLName" class="managerInput" type="text" placeholder=" Enter last name"/> <br/>
			</div>
			<div id="managerHealthInuranceDiv" class="managerInputDiv" > 	
				<label>Health Insurance</label><br/>
				<select id="managerHIList" class="managerInput" >
					
				</select>
			</div>
			<div id="managerOtherHealthInuranceDiv" class="managerInputDiv" > 	
				<label>Other Health Insurance</label><br/>
				<input id = "managerOtherHI" class="managerInput"/>
				
			</div>
			<div id="managerEmailDiv" class="managerInputDiv">
				<label>Email id</label><br/>
				<input id="managerEmail" class="managerInput" type="email" placeholder=" Enter email id"/> <br/>
			</div>
			<div id="managerPhoneDiv" class="managerInputDiv">
				<label>Phone no. </label><br/>
				<input id="managerPhone" class="managerInput" type="text" placeholder=" Enter phone no"/>
			</div>
			<div id="managerStreetDiv" class="managerInputDiv">
				<label>Street</label><br/>
				<input id="managerStreet" class="managerInput" type="text" placeholder=" Enter street name, apt no. "/> <br/>
			</div>
			<div id="managerCityDiv" class="managerInputDiv">
				<label>City</label><br/>
				<input id="managerCity" class="managerInput" type="text" placeholder=" Enter city"/>
			</div>
			<div id="managerStateDiv" class="managerInputDiv">
				<label>State</label><br/>
				<select id="managerState" class="managerInput" type="text" placeholder=" Enter state "></select> <br/>
			</div>
			<div id="managerZipDiv" class="managerInputDiv">
				<label>Zip code</label><br/>
				<input id="managerZip" class="managerInput" type="text" placeholder=" Enter zip"/>
			</div>
			
			<div id="managerUserNameDiv" class="managerInputDiv">
				<label>User name</label><br/>
				<input id="managerUName" class="managerInput" type="text" placeholder=" Enter user name "/> <br/>
			</div>
			<div id="managerPasswordDiv" class="managerInputDiv">
				<label>Password</label><br/>
				<input id="managerPassword" class="managerInput" type="password" placeholder=" Enter password"/>
			</div>
			<div id="confirmManagerPasswordDiv" class="managerInputDiv">
				<label>Confirm Password</label><br/>
				<input id="confirmManagerPassword" class="managerInput" type="password" placeholder=" Enter password"/>
			</div>
			
			<button id="createManagerButton"> Create</button>
		</div>	
	</div>
	

<div id="customerModal" class="modal">
		<h3 id="createCustomerHeader">Add a Customer</h3>
		<h3 id="editCustomerHeader">Modify a Customer</h3>
		<div id="inputFormDiv">
			<div id="customerFirstNameDiv" class="customerInputDiv">
				<label>First Name</label> <br/>
				<input id="customerFName" class="customerInput" type="text" placeholder=" Enter first name"/> 
			</div>
			
			<div id="customerLastNameDiv" class="customerInputDiv">
				<label>Last Name</label><br/>
				<input id="customerLName" class="customerInput" type="text" placeholder=" Enter last name"/> <br/>
			</div>
			<div id="customerEmailDiv" class="customerInputDiv">
				<label>Email id</label><br/>
				<input id="customerEmail" class="customerInput" type="text" placeholder=" Enter email id"/> <br/>
			</div>
			<div id="customerPhoneDiv" class="customerInputDiv">
				<label>Phone no. </label><br/>
				<input id="customerPhone" class="customerInput" type="text" placeholder=" Enter phone no"/>
			</div>
			<div id="customerStreetDiv" class="customerInputDiv">
				<label>Street</label><br/>
				<input id="customerStreet" class="customerInput" type="text" placeholder=" Enter street name, apt no. "/> <br/>
			</div>
			<div id="customerCityDiv" class="customerInputDiv">
				<label>City</label><br/>
				<input id="customerCity" class="customerInput" type="text" placeholder=" Enter city"/>
			</div>
			<div id="customerStateDiv" class="customerInputDiv">
				<label>State</label><br/>
				<select id="customerState" class="customerInput" type="text" placeholder=" Enter state "></select> <br/>
			</div>
			<div id="customerZipDiv" class="customerInputDiv">
				<label>Zip code</label><br/>
				<input id="customerZip" class="customerInput" type="text" placeholder=" Enter zip"/>
			</div>
			<div id="customerHealthInuranceDiv" class="customerInputDiv" > 	
				<label>Health Insurance</label><br/>
				<select id="customerHIList"  class="customerInput">
				</select>
			</div>
			
			<div id="customerOtherHIDiv" class="customerInputDiv" > 	
				<label>Other Health Insurance</label><br/>
				<input id="customerOtherHI" class="customerInput" type="text"/>
				
			</div>
			
			<div id="customerMembershipDiv" class="customerInputDiv" > 	
				<label>Membership Status</label><br/>
				<select id="customerMembership"  class="customerInput">
				</select>
			</div>
		
			<button id="createCustomerButton">Create</button>
			<button id="editCustomerButton">Submit changes</button>
			<button id="cancelCustomerButton">Cancel</button>
		</div>
	</div>
	
	<div id="addMachineModal" class="modal">
		<h3>Add a machine</h3>
		<form id="addMachineForm" action="/manager/ui" method="post" enctype="multipart/form-data">
		<input style="display:none" name="type" type="text" name="type" value="addMachine"/>
		<div class=machineInputDiv>
			<label>Machine name</label><br/>
			<input id="addMachineName" name="machineName" type="text" placeholder="Enter machine name"/>
			
		</div>
		<div class=machineInputDiv>
			<label>Machine quantity</label><br/>
			<input id="addMachineQuantity" name="machineQuantity" type="number" placeholder="Enter quantity"/>
		</div>
		<div class=machineInputDiv>
			
			<label>Choose picture</label><br/>
			<input id="addMachinePic" type="file" name="machinePic" accept=".jpg,.png,.bmp,.tiff"/><br/>
			<img id="addMachinePreview" src="#"></img>
		</div>
		<input id="addMachineButton" type="submit" value="Add machine"/>
		<button id= "cancelMachineButton" >Cancel</button>
		</form>
	</div>
	
	<div id="editMachineModal" class="modal">
		<h3>Add a machine</h3>
		<form id="editMachineForm" action="/manager/ui" method="post" enctype="multipart/form-data">
		<input style="display:none" name="type" type="text" name="type" value="updateMachine"/>
		<input style="display:none" id = "machineId" name="machineId" type="text"  value=""/>
		<div class=machineInputDiv>
			<label>Machine name</label><br/>
			<input id="editMachineName" name="machineName" type="text" placeholder="Enter machine name"/>
			
		</div>
		<div class=machineInputDiv>
			<label>Machine quantity</label><br/>
			<input id="editMachineQuantity" name="machineQuantity" type="number" placeholder="Enter quantity"/>
		</div>
		<div class=machineInputDiv>
			
			<label>Choose picture</label><br/>
			<input id="editMachinePic" type="file" name="machinePic" accept=".jpg,.png,.bmp,.tiff"/><br/>
			<img id="editMachinePreview" src="#"></img>
		</div>
		<input id="editMachineButton" type="submit" value="Submit Changes"/>
		<button id= "cancelEditMachine" >Cancel</button>
		</form>
	</div>
</body>

<script src="/js/manager.js"></script>
<script type="text/javascript">
var level = "<%=request.getAttribute("level")%>"
var managerData = <%=request.getAttribute("managerData")%>
/* var level = "MANAGER"; */	

	switch(level) {
	case "ADMIN": 
		showAdminUI(managerData);
		break;
	case "MANAGER":
			
		showManagerUI();
	}
	

</script>
</html>