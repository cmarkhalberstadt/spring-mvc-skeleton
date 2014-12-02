


function submitButtonClick(){
	$("#errorMessage_username").fadeOut(200);
	$("#errorMessage_password").fadeOut(200);
	var userName = document.getElementById("Username").value;
	var password = document.getElementById("Password").value;
	
	var URL = "/user" + "?" + "Username=" + userName;
	URL += "&Password=" + password;
	
	$.ajax({
	    url: URL,
	    type: 'GET',
	    success: function(result) {
	        if (result == "falseUsername"){
	        	document.getElementById("errorMessage_username").innerHTML = "Username not in database.";
	        	document.getElementById("errorMessage_password").innerHTML = "";
	        	$("#errorMessage_username").fadeIn(1000);
	        } else if (result == "falsePassword"){
	        	document.getElementById("errorMessage_username").innerHTML = "";
	        	document.getElementById("errorMessage_password").innerHTML = "Incorrect password.";
	        	$("#errorMessage_password").fadeIn(1000);
	        } else {
	        	location.assign("/" + result);
	        }
	    }
	});
}
