<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: alexfomin
  Date: 26.07.17
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<p>
    Hello, ${requestScope.user.fName}
</p>

<p>
    Nice, ${sessionScope.userfName}
</p>

<p>by requestScope</p>

<h1>${cookie.get("userfName").value}</h1>

</body>
</html>
