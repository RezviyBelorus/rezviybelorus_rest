<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<%--
  Created by IntelliJ IDEA.
  User: alexfomin
  Date: 25.08.17
  Time: 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>kinoview - Main</title>
</head>
<body>
<h1>
    ${requestScope.hello}
</h1>

<c:forEach var = "i" begin = "1" end = "5">
Item <c:out value = "${requestScope.films.get(i).getName()}"/><p>
    </c:forEach>
</body>
</html>
