<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style>
p.errorString {
color:red;
}
</style>
<title>Users Authentication Page</title>
</head>

<body>
<h1>User Authentication Page</h1>
<br />
<p>Please Enter your User Name and Password</p>

<p class="errorString">${errorString}</p>
<form action="<c:url value="/usersAuthentication" />" method="POST">
<table>
<tr><td>Username:</td><td><input type="text" name="Username" value="" id="Username"/></td></tr>
<br />
<tr><td>Password:</td><td><input type="password" name="Password" id="Password"/></td></tr>
</table>
<input type="submit" value="Submit" id="Submit" name="Submit"/>
</form>
</body>
</html>