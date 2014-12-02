<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/css/users.css"/>



<title>Users</title>
</head>

<body>
<h1>Users Page</h1>
<hr />

<table class="userDataTable">
<tr class="headerRow"><td>ID</td><td>User Name</td><td>Password</td></tr>
<c:forEach var="bean" items="${users}">
<div class="singleRow">
<tr><td>${bean.id}</td><td>${bean.username }</td><td>${bean.password }</td>

<!-- <form action="<c:url value="/user/${bean.username}"/>"> </form> -->


<td><input type="button" value="DELETE" id="DELETE" name="DELETE" onclick="deleteEntry('${bean.username}')"></td>
<td><input type="button" value="CHANGE PASSWORD" id="CHANGE_PASSWORD_${bean.username}" 
name="CHANGE_PASSWORD" onclick="changePasswordClick('${bean.username}')"></td>
<td>
<div id="passwordChangeDiv_${bean.username}" class="passwordChangeDiv">
<hr />
<h3>Username: ${bean.username}</h3>
<table>
<tr><td>Old Password:</td><td><input type="password" name="oldPassword_${bean.username}" id="oldPassword_${bean.username}" /></td><td class="errorMessage_changePassword" id="errorMessage_changePassword_oldPassword_${bean.username}"></td></tr>
<tr><td>New Password:</td><td><input type="password" name="newPassword_${bean.username}" id="newPassword_${bean.username}" /></td><td class="errorMessage_changePassword" id="errorMessage_changePassword_newPassword_${bean.username}"></td></tr>
<tr><td>Confirm New Password:</td><td><input type="password" name="confirmNewPassword_${bean.username}" id="confirmNewPassword_${bean.username}" /></td><td class="errorMessage_changePassword" id="errorMessage_changePassword_confirmNewPassword_${bean.username}"></td></tr>
<tr><td><input type="button" value="Submit" name="changePasswordSubmit_${bean.username}" id="changePasswordSubmit_${bean.username}" onclick="changePassword('${bean.username}')"/></td></tr>
</table>
<hr />
</div> <!-- End of div wrapper for new password fields -->
</td>




</tr>
</div> <!-- End of div wrapper for single table row -->
</c:forEach>
</table>

<input type="button" value="ADD NEW USER" name="addNewUserButton" id="addNewUserButton" onclick="addNewUserButtonClick()">
<div name="addNewUserFieldsDiv" id="addNewUserFieldsDiv" class="addNewUserFieldsDiv">
<hr />
<h3>Enter New User Name</h3>
<!-- 
<form action="<c:url value="/user"/>"> method="POST" -->
<form>
<table>
<tr><td>Username:</td><td><input type="text" name="UsernameToAdd" value="" id="UsernameToAdd"/></td><td class="errorMessage_addUser" id="errorMessage_addUser_Username"></td></tr>
<tr><td>Password:</td><td><input type="password" name="passwordToAdd" id="passwordToAdd" /></td><td class="errorMessage_addUser" id="errorMessage_addUser_Password"></td ></tr>
<tr><td>Confirm Password:</td><td><input type="password" name="confirmPasswordToAdd" id="confirmPasswordToAdd" /></td><td class="errorMessage_addUser" id="errorMessage_addUser_ConfirmPassword"></td></tr>
</table>
<input type="button" value="Submit" id="Submit" name="Submit" onclick="addUserSubmitButtonClick()"/>
</form>
<hr />
</div>

<!-- Load all javascript components -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="/resources/js/users.js" type="text/javascript"></script>
</body>



</html>