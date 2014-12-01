
var currentlyVisibleUserNameForPasswordChange = "";
var addNewUserCurrentlyVisible = false;

$(document).ready(function(){
	
});



function deleteEntry(user){
	var URL = "/user/" + user;
	
	
	$.ajax({
	    url: URL,
	    type: 'DELETE',
	    success: function(result) {
	        if (result=="true"){
	        	location.assign("/users");
	        } else {
	        	alert("ERROR: username not in database");
	        }
	    }
	});
	
}

function changePassword(user){
	
	$("#errorMessage_changePassword_oldPassword_" + user).fadeOut(400);
	$("#errorMessage_changePassword_newPassword_" + user).fadeOut(400);
	$("#errorMessage_changePassword_confirmNewPassword_" + user).fadeOut(400);
	
	
	
	var URL = "/user/" + user;
	
	// variables for storing the IDs
	var oldPasswordID = "oldPassword_" + user;
	var newPasswordID = "newPassword_" + user;
	var confirmNewPasswordID = "confirmNewPassword_" + user;
	
	// variables for storing the data in the text fields
	var oldPassword = document.getElementById(oldPasswordID).value;
	var newPassword = document.getElementById(newPasswordID).value;
	var confirmNewPassword = document.getElementById(confirmNewPasswordID).value;
	
    if (newPassword == ""){
    	// new password field is empty
    	document.getElementById("errorMessage_changePassword_oldPassword_" + user).innerHTML = "";
    	document.getElementById("errorMessage_changePassword_newPassword_" + user).innerHTML = "Please enter a valid password.";
    	document.getElementById("errorMessage_changePassword_confirmNewPassword_" + user).innerHTML = "";
    	$("#errorMessage_changePassword_oldPassword_" + user).fadeOut(300);
    	$("#errorMessage_changePassword_newPassword_" + user).fadeIn(1000);
    	$("#errorMessage_changePassword_confirmNewPassword_" + user).fadeOut(300);
	} else {
		if (newPassword == confirmNewPassword){
			// we should do the put call
			// build the URL with the request parameters
			URL += "?";
			URL += "oldPassword=";
			URL += oldPassword;
			URL += "&newPassword=";
			URL += newPassword;
			
			
			// ajax call
			$.ajax({
			    url: URL,
			    type: 'PUT',
			    data: {
			    	"oldPassword" : oldPassword,
			    	"newPassword" : newPassword
			    },
			    success: function(result) {
			        if (result == "true"){
			        	document.getElementById("errorMessage_changePassword_oldPassword_" + user).innerHTML = "";
			        	document.getElementById("errorMessage_changePassword_newPassword_" + user).innerHTML = "";
			        	document.getElementById("errorMessage_changePassword_confirmNewPassword_" + user).innerHTML = "";
			        	location.assign("/users")
			        } else {
			        	document.getElementById("errorMessage_changePassword_oldPassword_" + user).innerHTML = "Old password does not match.";
			        	document.getElementById("errorMessage_changePassword_newPassword_" + user).innerHTML = "";
			        	document.getElementById("errorMessage_changePassword_confirmNewPassword_" + user).innerHTML = "";
			        	$("#errorMessage_changePassword_oldPassword_" + user).fadeIn(1000);
			        	$("#errorMessage_changePassword_newPassword_" + user).fadeOut(300);
			        	$("#errorMessage_changePassword_confirmNewPassword_" + user).fadeOut(300);
			        }
			        //location.assign("/users");
			    }
			});
		} else {
			document.getElementById("errorMessage_changePassword_oldPassword_" + user).innerHTML = "";
			document.getElementById("errorMessage_changePassword_newPassword_" + user).innerHTML = "";
			document.getElementById("errorMessage_changePassword_confirmNewPassword_" + user).innerHTML = "Password does not match.";
			$("#errorMessage_changePassword_oldPassword_" + user).fadeOut(300);
			$("#errorMessage_changePassword_newPassword_" + user).fadeOut(300);
			$("#errorMessage_changePassword_confirmNewPassword_" + user).fadeIn(1000);
		}
	}
	
    
}

function changePasswordClick(userName){
	var jQueryDivIDString = "#passwordChangeDiv_" + userName;
	if (currentlyVisibleUserNameForPasswordChange == userName){
		$('.passwordChangeDiv').fadeOut(300);
		currentlyVisibleUserNameForPasswordChange = "";
		fadeOutChangePasswordErrorMessages(userName);
	} else {
		$('.passwordChangeDiv').fadeOut(300);
		clearChangePasswordErrorMessages(userName);
		clearChangePasswordTextFields(userName);
		$(jQueryDivIDString).fadeIn(1000);
		fadeOutChangePasswordErrorMessages(currentlyVisibleUserNameForPasswordChange);
		currentlyVisibleUserNameForPasswordChange = userName;
	}
	
}

function clearChangePasswordTextFields(user){
	// variables for storing the IDs
	var oldPasswordID = "oldPassword_" + user;
	var newPasswordID = "newPassword_" + user;
	var confirmNewPasswordID = "confirmNewPassword_" + user;
	
	document.getElementById(oldPasswordID).value = "";
	document.getElementById(newPasswordID).value = "";
	document.getElementById(confirmNewPasswordID).value = "";
}

function clearChangePasswordErrorMessages(user){
	document.getElementById("errorMessage_changePassword_oldPassword_" + user).innerHTML = "";
	document.getElementById("errorMessage_changePassword_newPassword_" + user).innerHTML = "";
	document.getElementById("errorMessage_changePassword_confirmNewPassword_" + user).innerHTML = "";
}

function fadeOutChangePasswordErrorMessages(user){
	if (user != ""){
		$("#errorMessage_changePassword_oldPassword_" + user).fadeOut(400);
		$("#errorMessage_changePassword_newPassword_" + user).fadeOut(400);
		$("#errorMessage_changePassword_confirmNewPassword_" + user).fadeOut(400);
	}
}

function addNewUserButtonClick(){
	var jQueryDivIDString = "#addNewUserFieldsDiv";
	if (addNewUserCurrentlyVisible){
		$(jQueryDivIDString).fadeOut(1000);
		addNewUserCurrentlyVisible = false;
	} else {
		// clear error messages
		document.getElementById("errorMessage_addUser_Username").innerHTML = "";
		document.getElementById("errorMessage_addUser_Password").innerHTML = "";
		document.getElementById("errorMessage_addUser_ConfirmPassword").innerHTML = "";
		
		// clear text fields
		document.getElementById("UsernameToAdd").value = "";
		document.getElementById("passwordToAdd").value = "";
		document.getElementById("confirmPasswordToAdd").value = "";
		
		$(jQueryDivIDString).fadeIn(1000);
		addNewUserCurrentlyVisible = true;
	}
}

function addUserSubmitButtonClick(){
	$("#errorMessage_addUser_Username").fadeOut(300);
	$("#errorMessage_addUser_Password").fadeOut(300);
	$("#errorMessage_addUser_ConfirmPassword").fadeOut(300);
	document.getElementById("errorMessage_addUser_Username").innerHTML = "";
	document.getElementById("errorMessage_addUser_Password").innerHTML = "";
	document.getElementById("errorMessage_addUser_ConfirmPassword").innerHTML = "";
	
	
	var usernameToAdd        = document.getElementById("UsernameToAdd").value;
	var passwordToAdd        = document.getElementById("passwordToAdd").value;
	var confirmPasswordToAdd = document.getElementById("confirmPasswordToAdd").value;
	
	if (passwordToAdd == ""){
		document.getElementById("errorMessage_addUser_Username").innerHTML = "";
		document.getElementById("errorMessage_addUser_Password").innerHTML = "Please enter a valid password.";
		document.getElementById("errorMessage_addUser_ConfirmPassword").innerHTML = "";
		$("#errorMessage_addUser_Username").fadeOut(300);
		$("#errorMessage_addUser_Password").fadeIn(1000);
		$("#errorMessage_addUser_ConfirmPassword").fadeOut(300);
	} else {
		if (passwordToAdd != confirmPasswordToAdd){
			document.getElementById("errorMessage_addUser_Username").innerHTML = "";
			document.getElementById("errorMessage_addUser_Password").innerHTML = "";
			document.getElementById("errorMessage_addUser_ConfirmPassword").innerHTML = "Confirmation password does not match";
			$("#errorMessage_addUser_Username").fadeOut(300);
			$("#errorMessage_addUser_Password").fadeOut(300);
			$("#errorMessage_addUser_ConfirmPassword").fadeIn(1000);
		} else {
			// try to add into database
			var URL = "/user"
				
			URL += "?";
			URL += "UsernameToAdd=";
			URL += usernameToAdd;
			
			URL += "&passwordToAdd=";
			URL += passwordToAdd;
			
			
			
			$.ajax({
			    url: URL,
			    type: 'POST',
			    success: function(result) {
			        if (result == "true"){
			        	location.assign("/users");
			        } else {
			        	document.getElementById("errorMessage_addUser_Username").innerHTML = "Username is not available.";
			        	document.getElementById("errorMessage_addUser_Password").innerHTML = "";
			        	document.getElementById("errorMessage_addUser_ConfirmPassword").innerHTML = "";
			        	$("#errorMessage_addUser_Username").fadeIn(1000);
			        	$("#errorMessage_addUser_Password").fadeOut(300);
			        	$("#errorMessage_addUser_ConfirmPassword").fadeOut(300);
			        }
			    }
			});
		}
	}
	
}