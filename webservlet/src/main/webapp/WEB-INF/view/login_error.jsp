<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/css/kinoviewstyles.css"/>
    <title>kinoview - Login</title>
</head>
<body>
<div class="container">
    <a href="http://localhost:8080/main"> <img class="logo" src="/resources/thumbnails/logo.png"></a>
    <div class="form">
        <%--<h1>in login.jsp</h1>--%>
        <%--<h1>by cookie ${cookie.get("userfName").value}</h1>--%>
        <%--<h1>by requestScope ${requestScope.userfName}</h1>--%>
        <h4>Неверное имя пользователя или пароль...</h4>
        <h5>Login into your account:</h5>
        <form action="http://localhost:8080/users/login" method="POST">
            <div class="tableConeiner">
                <div class="tableRow">
                    <label for="login">
                        Login or email:
                    </label>
                    <input type="text" id="login" name="emailOrLogin" value="" placeholder="alex@gmail.com" required>
                    <br>
                    <label for="password">
                        Password:
                    </label>
                    <input type="password" id="password" name="password" value="" placeholder="your password" required>
                </div>
                <div class="tableRow">
                    <button class="btn btn-secondary">Login</button>
                    <a href="restorePassword.html">Forgot password?</a>
                </div>
            </div>
        </form>
        <form action="signUp.html" method="GET">
            <div class="tableConteiner">
                <div class="tableRow">
                    <button class="btn btn-secondary">Sign up</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>