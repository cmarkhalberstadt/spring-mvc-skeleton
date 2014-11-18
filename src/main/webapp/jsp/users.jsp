<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style>
table.userDataTable {
border: 5px;
}
</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">

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
	alert("put: " + URL);
	$.ajax({
	    url: URL,
	    type: 'PUT',
	    success: function(result) {
	        // Do something with the result
	        //alert(result);
	        location.assign("/changePassword/" + user);
	    }
	});
}

</script>

<title>Users</title>
</head>

<body>
<h1>Users Page</h1>


<table class="userDataTable">
<tr class="headerRow"><td>ID</td><td>User Name</td><td>Password</td></tr>
<c:forEach var="bean" items="${users}">
<tr><td>${bean.id}</td><td>${bean.userName }</td><td>${bean.password }</td>

<!-- <form action="<c:url value="/user/${bean.userName}"/>"> </form> -->


<td><input type="button" value="DELETE" id="DELETE" name="DELETE" onclick="deleteEntry('${bean.userName}')"></td>
<td><input type="button" value="CHANGE PASSWORD" id="CHANGE_PASSWORD" 
name="CHANGE_PASSWORD" onclick="changePassword('${bean.userName}')"></td>



</tr>
</c:forEach>
</table>

<a href="addNewUserPage">Add New User</a>


</body>

</html>