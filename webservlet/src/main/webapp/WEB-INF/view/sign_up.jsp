<%--
  Created by IntelliJ IDEA.
  User: alexfomin
  Date: 18.09.17
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/css/kinoviewstyles.css"/>
    <title>Kinoview - Register</title>
</head>

<body>
<div class="container">
    <a href="http://localhost:8080/main"> <img class="logo" src="/resources/thumbnails/logo.png"></a>
    <div class="form">
        <form action="http://localhost:8080/users/signUp" method="POST">
            <h5>Kinoview - Register:</h5>
            <div class="tableContainer">
                <div class="tableRow">
                    <label for="fName">First name</label>
                    <input type="text" id="fName" name="fName" value="" placeholder="you first name" required>
                </div>
                <div class="tableRow">
                    <label for="lname">Last name:</label>
                    <input type="text" id="lname" name="lName" value="" placeholder="your last name" required>
                </div>
                <div class="tableRow">
                    <label for="login">Login:</label>
                    <input type="text" id="login" name="login" value="" placeholder="type login" required>
                </div>
                <div class="tableRow">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" value="" oninput="check(this)"
                           placeholder="*******" required>
                </div>
                <div class="tableRow">
                    <label for="passwordConfirm">Repeat password:</label>
                    <input type="password" id="passwordConfirm" value="" oninput="check(this)" placeholder="*******"
                           required>
                </div>
                <div class="tableRow">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="" placeholder="123@gmail.com" required>
                </div>

                <div class="tableRow">
                    <button class="btn btn-secondary" onclick="history.back()">Back</button>
                    <button class="btn btn-secondary">Register</button>
                </div>
            </div>
        </form>
    </div>
    <script language='javascript' type='text/javascript'>
        function check(input) {
            if (input.value != document.getElementById('password').value) {
                input.setCustomValidity('Password Must be Matching.');
            } else {
                // input is valid -- reset the error message
                input.setCustomValidity('');
            }
        }
    </script>
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
</div>
</body>
</html>
