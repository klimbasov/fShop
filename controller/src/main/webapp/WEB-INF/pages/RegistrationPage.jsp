<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 10/12/2021
  Time: 7:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="resources/js/ajaxRegistration.js"></script>
</head>
<body>
<div class="registration_block">
    <FORM id="registrationForm" method="post" class="registration" >
        <div>
            <span>username</span>
            <input type="text" size="40" name="password" id="userName" autocomplete="off" spellcheck="false">
            </p>
        </div>
        <div>
            <span>password</span>
            <input type="text" size="40" name="password" id="password" autocomplete="off" spellcheck="false">
            </span>
        </div>
        <input type="hidden" name="command" value="Register" id="command"/>
        <button type="submit">Sign Up</button>
    </FORM>
</div>

</body>
</html>
