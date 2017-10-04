<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <title>kinoview - Login</title>
</head>
<body>
<p>
    Hello, ${requestScope.user}
</p>
<!--todo: action-->
<form action="http://localhost:8080/users/login" method="POST">
    <div class="tableConeiner">
        <div class="tableRow">
            <label for="login">
                Login or email:
            </label>
            <input type="text" id="login" name="emailOrLogin" value="" placeholder="sexy test" required>
            <br>
            <label for="password">
                Password:
            </label>
            <input type="password" id="password" name="password" value="" placeholder="your password" required>
        </div>
        <div class="tableRow">
            <input type="submit" value="Login">
            <a href="restorePassword.html">Forgot password?</a>
        </div>
    </div>
</form>
<form action="signUp.html" method="get">
    <div class="tableConteiner">
        <div class="tableRow">
            <button type="submit">Sign up</button>
        </div>
    </div>

</form>
</body>
</html>