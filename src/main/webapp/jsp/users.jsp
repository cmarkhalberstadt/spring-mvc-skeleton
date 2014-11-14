<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style>
table.userDataTable {
border: 5px;
}
</style>
<title>Users</title>
</head>

<body>
<h1>Users Page</h1>


<table class="userDataTable">
<tr class="headerRow"><td>ID</td><td>User Name</td><td>Password</td></tr>
<c:forEach var="bean" items="${users}">
<tr><td>${bean.id}</td><td>${bean.userName }</td><td>${bean.password }</td></tr>
</c:forEach>
</table>


</body>
</html>