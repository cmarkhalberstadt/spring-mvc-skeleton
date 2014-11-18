<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style>
table.userDataTable {
border: 10px;
}

p.errorString {
color:red;
}



</style>
<title>Add New User</title>
</head>

<body>
<h1>Add New User</h1>

<div id="topLevelContainer">

<div id="tableOfExistingUsers">

<table class="userDataTable">
<tr class="headerRow"><td>ID</td><td>User Name</td><td>Password</td></tr>
<c:forEach var="bean" items="${users}">
<tr><td>${bean.id}</td><td>${bean.userName }</td><td>${bean.password }</td></tr>
</c:forEach>
</table>

</div><!-- End of Table of Existing Users Div -->
<br />

<div id="addNewUserDivBlock" class="addNEwUserDivBlock"></div>
<h3>Enter New User Name</h3>
<p class="errorString">${errorString}</p>
<form action="<c:url value="/addNewUser"/>" method="POST">
<table>
<tr><td>Username:</td><td><input type="text" name="Username" value="" id="Username"/></td></tr>
<br />
<tr><td>Password:</td><td><input type="password" name="Password_1" id="Password_1" /></td></tr>
<tr><td>Confirm Password:</td><td><input type="password" name="Password_2" id="Password_2" /></td></tr>
</table>
<input type="submit" value="Submit" id="Submit" name="Submit" onclick="updateUserNameVar()"/>
</form>
</div>


</body>
<script type="text/javascript">
var userNameVar = "initialValue";
var Password = "";

function updateUserNameVar(){
	userNameVar = document.getElementById("Username").value;
	Password    = document.getElementById("Password").value;
	var URLString = "user/" + userNameVar;
	window.location.href = URLString;
}
</script>
</html>