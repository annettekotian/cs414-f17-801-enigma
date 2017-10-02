<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enigma</title>
<link rel="stylesheet" type="text/css" href="css/gymSystem.css">
</head>
<body>
<div style="width:100%; height:40px; color:white;background:lightskyblue; text-align:center">
	<ul id="topHeader">
	<li id="weclcomeLi">Welcome User!</li>
	
	<li id="logoutLink">Logout</li>
	</ul>
</div>
	<div id="menuBarDiv">
	<ul id="menuBar">
		<li id="trainerLi" class="menuLi">Trainers</li>
		<li class="menuLi">Customers</li>
		<li class="">Inventory</li>
	</ul>
	</div>
	
	<button id="addTrainer">Add Trainer</button>
	<div id="trainerResults">
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
					Qualifications
				</th>
				<th>
					Health Insurance Provider
				</th>
				
			</tr>
			<tr>
				<td><a href="">Edit</a></td>
				<td>Trainer 1</td>
				<td>Street 1, Fort Collins, CO-80521</td>
				<td>Qualification 1</td>
				<td>Insurance 1</td>
				
			</tr>
			<tr>
				<td><a href="">Edit</a></td>
				<td>Trainer 2</td>
				<td>Street 1, Fort Collins, CO-80521</td>
				<td>Qualification 1</td>
				<td>Insurance 2</td>
				
			</tr>
			<tr>
				<td><a href="">Edit</a></td>
				<td>Trainer 3</td>
				<td>Street 1, Fort Collins, CO-80521</td>
				<td>Qualification 1</td>
				<td>Insurance 3</td>
				
			</tr>
		</table>
	</div>

<script type="text/javascript">
$(".customerDiv").on("click", function(){
	
})
</script>
</body>
</html>