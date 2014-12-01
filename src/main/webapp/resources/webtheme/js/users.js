


$(document).ready(function(){
	// this is only ever a boolean value
	

	if (displayChangePasswordErrorMessage_changePassword){
		
		//alert(jQueryDivIDString);
		$(jQueryDivIDString).show();
		$(".errorMessage_changePassword").fadeIn(1000);
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

function addUserSubmitButtonClick(){
	alert("POST Called");
	document.getElementById("errorMessage_addUser_Username").value = "";
	document.getElementById("errorMessage_addUser_Password").value = "";
	document.getElementById("errorMessage_addUser_ConfirmPassword").value = "";
	$("#errorMessage_addUser_Username").hide();
	$("#errorMessage_addUser_Password").hide();
	$("#errorMessage_addUser_ConfirmPassword").hide();
	
	var usernameToAdd        = document.getElementById("UsernameToAdd").value;
	var passwordToAdd        = document.getElementById("passwordToAdd").value;
	var confirmPasswordToAdd = document.getElementById("confirmPasswordToAdd").value;
	
	if (passwordToAdd == ""){
		document.getElementById("errorMessage_addUser_Username").innerHTML = "";
		document.getElementById("errorMessage_addUser_Password").innerHTML = "Please enter a valid password.";
		document.getElementById("errorMessage_addUser_ConfirmPassword").innerHTML = "";
		$("#errorMessage_addUser_Username").hide();
		$("#errorMessage_addUser_Password").fadeIn(1000);
		$("#errorMessage_addUser_ConfirmPassword").hide();
	} else {
		if (passwordToAdd != confirmPasswordToAdd){
			document.getElementById("errorMessage_addUser_Username").innerHTML = "";
			document.getElementById("errorMessage_addUser_Password").innerHTML = "";
			document.getElementById("errorMessage_addUser_ConfirmPassword").innerHTML = "Confirmation password does not match";
			$("#errorMessage_addUser_Username").hide();
			$("#errorMessage_addUser_Password").hide();
			$("#errorMessage_addUser_ConfirmPassword").fadeIn(1000);
		} else {
			// try to add into database
			var URL = "/user"
			
			
			
			URL += "?";
			URL += "UsernameToAdd=";
			
			URL += usernameToAdd;
			
			URL += "&passwordToAdd=";
			
			
			URL += passwordToAdd;
			
			URL += "&confirmPasswordToAdd=";
			
			
			URL += confirmPasswordToAdd;
			
			alert(URL);
			
			$.ajax({
			    url: URL,
			    type: 'POST',
			    success: function(result) {
			    	alert(result);
			        if (result == "true"){
			        	location.assign("/users");
			        } else {
			        	document.getElementById("errorMessage_addUser_Username").innerHTML = "Username is not available.";
			        	document.getElementById("errorMessage_addUser_Password").innerHTML = "";
			        	document.getElementById("errorMessage_addUser_ConfirmPassword").innerHTML = "";
			        	$("#errorMessage_addUser_Username").fadeIn(1000);
			        	$("#errorMessage_addUser_Password").hide();
			        	$("#errorMessage_addUser_ConfirmPassword").hide();
			        }
			    }
			});
		}
	}
	
}