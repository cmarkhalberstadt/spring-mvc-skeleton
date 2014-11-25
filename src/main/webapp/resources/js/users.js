
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