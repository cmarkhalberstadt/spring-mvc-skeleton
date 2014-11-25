<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style>
table.userDataTable {
border: 5px;
transition: max-height 0.75s ease-in;
}

.errorMessage_changePassword {
display:none;
}

.errorMessage_addUser {
display:none;
}

div.passwordChangeDiv {
position:absolute;
display:none;
width:550px;
left: 430px;
}

div.singleRow {
positiong:relative;
}

.errorMessage_changePassword {
color:red;
}

.errorMessage_addUser {
color:red;
}

div.addNewUserFieldsDiv {
display:none;
width:550px;
}

</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="/resources/js/users.js" type="text/javascript"></script>
<!--  
<script type="text/javascript">

var currentlyVisibleUserNameForPasswordChange = "";

$(document).ready(function(){
	// this is only ever a boolean value
	var displayChangePasswordErrorMessage_changePassword = ${displayChangePasswordErrorMessage_changePassword};

	if (displayChangePasswordErrorMessage_changePassword){
		var jQueryDivIDString = "#passwordChangeDiv_" + "${userNameOfLastEditedUserPassword_changePassword}";
		//alert(jQueryDivIDString);
		$(jQueryDivIDString).show();
		$(".errorMessage_changePassword").fadeIn(1000);
	}
	
	// this is only ever a boolean value
	var displayAddNewUserErrorMessage_addNewUser = ${displayAddNewUserErrorMessage_addNewUser};
	
	if (displayAddNewUserErrorMessage_addNewUser){
		var jQueryString = "#" + "addNewUserFieldsDiv";
		$(jQueryString).show();
		$(".errorMessage_addUser").fadeIn(1000);
	}
});



function deleteEntry(user){
	var URL = "/user/" + user;
	//alert(URL);
	
	$.ajax({
	    url: URL,
	    type: 'DELETE',
	    success: function(result) {
	        // Do something with the result
	        //alert(result);
	        location.assign("/users");
	    }
	});
	
}

function changePassword(user){
	
	
	var URL = "/user/" + user;
	
	var oldPasswordID = "oldPassword_" + user;
	var newPasswordID = "newPassword_" + user;
	var confirmNewPasswordID = "confirmNewPassword_" + user;
	
	URL += "?";
	URL += "oldPassword=";
	
	var oldPassword = document.getElementById(oldPasswordID).value;
	URL += oldPassword;
	
	URL += "&newPassword=";
	
	var newPassword = document.getElementById(newPasswordID).value;
	
	URL += newPassword;
	
	URL += "&confirmNewPassword=";
	
	var confirmNewPassword = document.getElementById(confirmNewPasswordID).value;
	
	URL += confirmNewPassword;
	
	
	
	$.ajax({
	    url: URL,
	    type: 'PUT',
	    data: {
	    	"oldPassword" : oldPassword,
	    	"newPassword" : newPassword,
	    	"confirmNewPassword" : confirmNewPassword
	    },
	    success: function(result) {
	        // display fields on window to change password.
	    	//location.assign("/users");
	        var resultObject = jQuery.parseJSON(result);
	        //alert(resultObject.name);
	        //alert(result);
	        location.assign("/users");
	    }
	});
}

function changePasswordClick(userName){
	var jQueryDivIDString = "#passwordChangeDiv_" + userName;
	if (currentlyVisibleUserNameForPasswordChange == userName){
		$('.passwordChangeDiv').fadeOut(300);
		currentlyVisibleUserNameForPasswordChange = "";
	} else {
		$('.passwordChangeDiv').fadeOut(300);
		$(jQueryDivIDString).fadeIn(1000);
		currentlyVisibleUserNameForPasswordChange = userName;
	}
	
	//alert(jQueryDivIDString);
	
}

function addNewUserButtonClick(){
	var jQueryDivIDString = "#addNewUserFieldsDiv";
	//alert(jQueryDivIDString);
	$(jQueryDivIDString).fadeToggle(1000);
}

</script> -->

<title>Users</title>
</head>

<body>
<h1>Users Page</h1>
<hr />

<table class="userDataTable">
<tr class="headerRow"><td>ID</td><td>User Name</td><td>Password</td></tr>
<c:forEach var="bean" items="${users}">
<div class="singleRow">
<tr><td>${bean.id}</td><td>${bean.userName }</td><td>${bean.password }</td>

<!-- <form action="<c:url value="/user/${bean.userName}"/>"> </form> -->


<td><input type="button" value="DELETE" id="DELETE" name="DELETE" onclick="deleteEntry('${bean.userName}')"></td>
<td><input type="button" value="CHANGE PASSWORD" id="CHANGE_PASSWORD_${bean.userName}" 
name="CHANGE_PASSWORD" onclick="changePasswordClick('${bean.userName}')"></td>
<td>
<div id="passwordChangeDiv_${bean.userName}" class="passwordChangeDiv">
<hr />
<h3>Username: ${bean.userName}</h3>
<table>
<tr><td>Old Password:</td><td><input type="password" name="oldPassword_${bean.userName}" id="oldPassword_${bean.userName}" /></td><td class="errorMessage_changePassword">${oldPasswordIncorrectErrorMessage_changePassword}</td></tr>
<tr><td>New Password:</td><td><input type="password" name="newPassword_${bean.userName}" id="newPassword_${bean.userName}" /></td><td class="errorMessage_changePassword">${newPasswordConfirmErrorMessage_changePassword}</td></tr>
<tr><td>Confirm New Password:</td><td><input type="password" name="confirmNewPassword_${bean.userName}" id="confirmNewPassword_${bean.userName}" /></td></tr>
<tr><td><input type="button" value="Submit" name="changePasswordSubmit_${bean.userName}" id="changePasswordSubmit_${bean.userName}" onclick="changePassword('${bean.userName}')"/></td></tr>
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
<form action="<c:url value="/user"/>" method="POST">
<table>
<tr><td>Username:</td><td><input type="text" name="UsernameToAdd" value="" id="UsernameToAdd"/></td><td class="errorMessage_addUser">${usernameErrorMessage_addUser}</td></tr>
<tr><td>Password:</td><td><input type="password" name="passwordToAdd" id="passwordToAdd" /></td><td class="errorMessage_addUser">${passwordErrorMessage_addUser}</td ></tr>
<tr><td>Confirm Password:</td><td><input type="password" name="confirmPasswordToAdd" id="confirmPasswordToAdd" /></td></tr>
</table>
<input type="submit" value="Submit" id="Submit" name="Submit"/>
</form>
<hr />
</div>

</body>

</html>