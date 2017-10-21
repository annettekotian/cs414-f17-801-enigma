<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enigma</title>
<link rel="stylesheet" type="text/css" href="../css/gymSystem.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.modal.min.css">
<script src="../js/jquery-3.2.1.min.js"></script>
<script src="../js/jquery.modal.min.js"></script>


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
	
	<button id="addManager" class="addButtons"><a href="#addManagerModal" rel="modal:open">Add Manager</a></button>
	<button id="addTrainer" class="addButtons">Add Trainer</button>
	<button id="addCustomer" class="addButtons"><a href="#addCustomerModal" rel="modal:open">Add Customer</a></button>
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
			<tr>
				<th>
					
				</th>
				<th>
					Name
				</th>
				<th>
					Address
				</th>
				<th>
					Email
				</th>
				<th>
					Health Insurance Provider
				</th>
				
			</tr>
			<tr>
				<td><a href="">Edit</a></td>
				<td>Customer 1</td>
				<td>Street 1, Fort Collins, CO-80521</td>
				<td>customer1@email.com</td>
				<td>Insurance 1</td>
				
			</tr>
			<tr>
				<td><a href="">Edit</a></td>
				<td>Customer 2</td>
				<td>Street 1, Fort Collins, CO-80521</td>
				<td>customer1@email.com</</td>
				<td>Insurance 2</td>
				
			</tr>
			<tr>
				<td><a href="">Edit</a></td>
				<td>Customer 3</td>
				<td>Street 1, Fort Collins, CO-80521</td>
				<td>customer1@email.com</</td>
				<td>Insurance 3</td>
				
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
			
			
			<button id="createManagerButton"><a href="#"> Create</a></button>
		</div>	
	</div>


<div id="addCustomerModal" class="modal">
		<h3>Add a Manager</h3>
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
			
			
			<button id="createCustomerButton"><a href="#"> Create</a></button>
		</div>
	
	
	
	
	
	</div>
</body>
<script src="../js/manager.js"></script>
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