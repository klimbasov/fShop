<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 11/7/2021
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="resources/js/BarBuilder.js"></script>
    <title>Administration</title>
</head>
<body onload="buildBar('bar', '${sessionScope.role}')">
<div id="bar"></div>
<nav>
    <ul>
        <li>
            <form method="GET" action="FrontController">
                <input type="hidden" name="command" value="ShowAddProductPage">
                <button type="submit">Add product</button>
            </form>
        </li>
        <li>
            <form method="GET" action="FrontController">
                <input type="hidden" name="command" value="ShowUsersListPage">
                <button type="submit">Manage Users</button>
            </form>
        </li>
    </ul>
</nav>
</body>
</html>
