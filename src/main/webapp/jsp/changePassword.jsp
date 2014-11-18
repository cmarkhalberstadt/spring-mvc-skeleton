<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style>
p.errorString {
color:red;
}
</style>
<script type="text/javascript">
function doUpdate(user){
	var URL = "/user/" + user;
	
}
</script>
<title>Change Password Page</title>
</head>

<body>
<h1>Change Password Page</h1>
<br />
<p>Please enter your old password followed by your new password twice</p>

<p class="errorString">${errorString}</p>


<table>
<tr><td>Old Password:</td><td><input type="password" name="OldPassword" value="" id="OldPassword"/></td></tr>
<br />
<tr><td>New Password:</td><td><input type="password" name="NewPassword" id="NewPassword" /></td></tr>
<br />
<tr><td>Confirm New Password:</td><td><input type="password" name="ConfirmNewPassword" id="ConfirmNewPassword" /></td></tr>
</table>
<input type="button" value="Submit" id="Submit" name="Submit" onclick="doUpdate('${userName}')"/>

</body>

</html>