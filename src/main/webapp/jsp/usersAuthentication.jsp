<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/css/usersAuthentication.css"/>

<title>Users Authentication Page</title>
</head>

<body>
<h1>User Authentication Page</h1>
<br />
<p>Please Enter your User Name and Password</p>
<!-- Old Form tag <form action="<c:url value="/user/{}" />" method="POST"> 

<form action="<c:url value="/user/${userNameVar}"/>" method="GET"> 
<form action="<c:url value="/user"/>" method="GET"> -->

<table>
<tr><td>Username:</td><td><input type="text" name="Username" value="" id="Username"/></td><td class="errorMessage_username" id="errorMessage_username"></td></tr>
<br />
<tr><td>Password:</td><td><input type="password" name="Password" id="Password" /></td><td class="errorMessage_password" id="errorMessage_password"></td></tr>
</table>
<input type="button" value="Submit" id="Submit" name="Submit" onclick="submitButtonClick()"  /><!--  -->

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="/resources/js/usersAuthentication.js" type="text/javascript"></script>
</body>

</html>