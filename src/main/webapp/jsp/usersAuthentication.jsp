<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style>
p.errorString {
color:red;
}
</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">

 /*
function submitButtonClick(){
	var userName = document.getElementById("Username").value;
	var password = document.getElementById("Password").value;
	
	var URL = "/api/user" + "?" + "Username=" + userName;
	alert(URL);
	
	$.ajax({
	    url: URL,
	    type: 'GET',
	    success: function(result) {
	        // Do something with the result
	        alert(result);
	        //location.assign("/users");
	    }
	});
}
*/


</script>
<title>Users Authentication Page</title>
</head>

<body>
<h1>User Authentication Page</h1>
<br />
<p>Please Enter your User Name and Password</p>

<p class="errorString">${errorString}</p>
<!-- Old Form tag <form action="<c:url value="/user/{}" />" method="POST"> 

<form action="<c:url value="/user/${userNameVar}"/>" method="GET"> -->
<form action="<c:url value="/user"/>" method="GET"> 
<table>
<tr><td>Username:</td><td><input type="text" name="Username" value="" id="Username"/></td></tr>
<br />
<tr><td>Password:</td><td><input type="password" name="Password" id="Password" /></td></tr>
</table>
<input type="submit" value="Submit" id="Submit" name="Submit"   /><!--  onclick="submitButtonClick()"-->
</form>
</body>
<script type="text/javascript">

</script>
</html>