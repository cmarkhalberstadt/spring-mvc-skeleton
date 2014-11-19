<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style>
table.userDataTable {
border: 5px;
}

.errorMessage {
display:none;
}

div.passwordChangeDiv {
position:relative;
display:none;
left: 50px;
}

div.singleRow {
positiong:relative;
}

.errorMessage {
color:red;
}

</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	var displayChangePasswordErrorMessage = ${displayChangePasswordErrorMessage};

	if (displayChangePasswordErrorMessage){
		var jQueryDivIDString = "#passwordChangeDiv_" + "${userNameOfLastEditedUserPassword}";
		//alert(jQueryDivIDString);
		$(jQueryDivIDString).show();
		$(".errorMessage").fadeIn(1000);
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
	        location.assign("/users");
	    }
	});
}

function changePasswordClick(userName){
	var jQueryDivIDString = "#passwordChangeDiv_" + userName;
	//alert(jQueryDivIDString);
	$(jQueryDivIDString).fadeIn(1000);
}

</script>

<title>Users</title>
</head>

<body>
<h1>Users Page</h1>


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

<table>
<tr><td>Old Password:</td><td><input type="password" name="oldPassword_${bean.userName}" id="oldPassword_${bean.userName}" /></td><td class="errorMessage">${oldPasswordIncorrectErrorMessage}</td></tr>
<tr><td>New Password:</td><td><input type="password" name="newPassword_${bean.userName}" id="newPassword_${bean.userName}" /></td><td class="errorMessage">${newPasswordConfirmErrorMessage}</td></tr>
<tr><td>Confirm New Password:</td><td><input type="password" name="confirmNewPassword_${bean.userName}" id="confirmNewPassword_${bean.userName}" /></td></tr>
<tr><td><input type="button" value="Submit" name="changePasswordSubmit_${bean.userName}" id="changePasswordSubmit_${bean.userName}" onclick="changePassword('${bean.userName}')"/></td></tr>
</table>

</div> <!-- End of div wrapper for new password fields -->
</td>




</tr>
</div> <!-- End of div wrapper for single table row -->
</c:forEach>
</table>

<a href="addNewUserPage">Add New User</a>


</body>

</html>