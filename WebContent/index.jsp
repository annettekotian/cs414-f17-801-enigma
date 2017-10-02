<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="js/jquery-3.2.1.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/gymSystem.css">
<title>Login</title>
</head>
<body>

<div id="topHeaderDiv" ><span>Welcome to Gym Managment System</span> </div>
<div id="formDiv">

	<h2>Gym System Login</h2>
	<form method="post" action="Login">
		<table>
			<tr>
				<td><label>User name: </label></td>
				<td><input id="username" name="userName" type="text"/></td>
			</tr>
			<tr>
				<td><label>Password: </label></td>
				<td><input id="password" name="password" type="password"/></td>
			</tr>
			<tr>
				<td><input type="submit" /></td>
			</tr>
		</table>
	</form>
</div>

<script>
	$("#submit").on('click', function(){
		$.ajax({
			url: "Login",
			method: "POST",
			data: {
				type: "loginUser",
				userName: $("#username").val(),
				password: $("#password").val()
				
			},
			success: function(result){
				alert("Logged in")
			}
		})
	})
</script>

</body>
</html>