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
	<button id="addTrainer" class="addButtons" onclick="createTrainerForm()">Add Trainer</button>
	<button id="modifyTrainer" class="addButtons" onclick="modifyTrainerForm()">Modify Trainer</button>
	<button id="deleteTrainer" class="addButtons" onclick="deleteTrainerForm()">Delete Trainer</button>
	<button id="addCustomer" class="addButtons">Add Customer</button>
	<input id = "searchCustomerInput" class="searchCustomer" type="text" placeholder="enter name, address etc" /> 
	<button id="searchCustomerButton" class="searchCustomer">Search</button>
	<button id="addMachine" class="addButtons">Add Equipment</button>
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
		<table id="trainerTable"></table>
	</div>

	<div id="customerResults">
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
					Name
				</th>
				<th>
					Picture
				</th>
				<th>
					Quantity
				</th>
			
				
			</tr>
			<tr>
				<td><a href="">Edit</a></td>
				<td>Machine 1</td>
				<td><img src="../images/treadmill.jpg"></img></td>
				<td>1</td>
				
				
			</tr>
			<tr>
				<td><a href="">Edit</a></td>
				<td>Machine 2</td>
				<td><img src="../images/treadmill.jpg"></img></td>
				<td>2</</td>
				
				
			</tr>
			<tr>
				<td><a href="">Edit</a></td>
				<td>Machine 3</td>
				<td><img src="../images/treadmill.jpg"></img></td>
				<td>3</</td>
				
				
			</tr>
		</table>
	</div>

<div id="modifyTrainerSelectForm" class="modal">
	<h3>Modify Trainer</h3>
	<form>
		<label>Select Trainer</label>
		<select id="modifyTrainerList" size="1"></select>
	</form>
	<button id="modifyTrainerSelectFormButton" onclick="modifyTrainerInformation()">Next</button>
	<button id="deleteTrainerSelectFormButton" onclick="deleteTrainer()">Delete</button>
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
			<div id="managerUserNameDiv" class="managerInputDiv">
				<label>User name</label><br/>
				<input id="managerUName" class="managerInput" type="text" placeholder=" Enter user name "/> <br/>
			</div>
			<div id="managerPasswordDiv" class="managerInputDiv">
				<label>Password</label><br/>
				<input id="managerPassword" class="managerInput" type="text" placeholder=" Enter password"/>
			</div>
			<div id="managerEmailDiv" class="managerInputDiv">
				<label>Email id</label><br/>
				<input id="managerEmail" class="managerInput" type="text" placeholder=" Enter email id"/> <br/>
			</div>
			<div id="managerPhoneDiv" class="managerInputDiv">
				<label>Phone no. </label><br/>
				<input id="managerPhone" class="managerInput" type="number" placeholder=" Enter phone no"/>
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
				<input id="managerZip" class="managerInput" type="number" placeholder=" Enter zip"/>
			</div>
			<div id="managerHealthInuranceDiv" class="managerInputDiv" > 	
				<label>Health Insurance</label><br/>
				<select id="managerHIList" class="managerInput" >
				</select>
			</div>
			
			
			<button id="createManagerButton"> Create</button>
		</div>	
	</div>
	
	
	<!-- <div id="editManagerModal" class="modal"> 
		<h3>Modify a Manager</h3>
		<div >
			<div id="editManagerFirstNameDiv" class="managerInputDiv">
				<label>First Name</label> <br/>
				<input id="editManagerFName" class="managerInput" type="text"/> 
			</div>
			
			<div id="editManagerLastNameDiv" class="managerInputDiv">
				<label>Last Name</label><br/>
				<input id="editManagerLName" class="managerInput" type="text"/> <br/>
			</div>
			<div id="editManagerUserNameDiv" class="managerInputDiv">
				<label>User name</label><br/>
				<input id="editManagerUName" class="managerInput" type="text" disabled /> <br/>
			</div>
			<div id="editManagerPasswordDiv" class="managerInputDiv">
				<label>Password</label><br/>
				<input id="editManagerPassword" class="managerInput" type="password" disabled/>
			</div>
			<div id="editManagerEmailDiv" class="managerInputDiv">
				<label>Email id</label><br/>
				<input id="editManagerEmail" class="managerInput" type="text"/> <br/>
			</div>
			<div id="editManagerPhoneDiv" class="managerInputDiv">
				<label>Phone no. </label><br/>
				<input id="editManagerPhone" class="managerInput" type="number"/>
			</div>
			<div id="editManagerStreetDiv" class="managerInputDiv">
				<label>Street</label><br/>
				<input id="editManagerStreet" class="managerInput" type="text"/> <br/>
			</div>
			<div id="editManagerCityDiv" class="managerInputDiv">
				<label>City</label><br/>
				<input id="editManagerCity" class="managerInput" type="text" />
			</div>
			<div id="editManagerStateDiv" class="managerInputDiv">
				<label>State</label><br/>
				<select id="editManagerState" class="managerInput" type="text" ></select> <br/>
			</div>
			<div id="editManagerZipDiv" class="managerInputDiv">
				<label>Zip code</label><br/>
				<input id="editManagerZip" class="managerInput" type="number"/>
			</div>
			<div id="editManagerHealthInuranceDiv" class="managerInputDiv" > 	
				<label>Health Insurance</label><br/>
				<select id="editManagerHIList" class="managerInput" >
				</select>
			</div>
			
			
			<button id="editManagerButton"> Confirm</button> 
			<button id="cancelManagerChanges"> Cancel</button> 
		</div>	
	</div>
	
	
-->

<div id="addCustomerModal" class="modal">
		<h3>Add a Customer</h3>
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
				<input id="customerPhone" class="customerInput" type="number" placeholder=" Enter phone no"/>
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
				<input id="customerZip" class="customerInput" type="number" placeholder=" Enter zip"/>
			</div>
			<div id="customerHealthInuranceDiv" class="customerInputDiv" > 	
				<label>Health Insurance</label><br/>
				<select id="customerHIList"  class="customerInput">
				</select>
			</div>
			
			<div id="customerHealthInuranceDiv" class="customerInputDiv" > 	
				<label>Membership Status</label><br/>
				<select id="customerMembership"  class="customerInput">
				</select>
			</div>
			
			
			<button id="createCustomerButton">Create</button>
		</div>
	
	
	
	
	
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