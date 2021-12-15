<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 11/7/2021
  Time: 9:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
    <script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="resources/js/BarBuilder.js"></script>
    <title>Add Product</title>
</head>
<body onload="buildBar('bar', '${sessionScope.role}')">
<div id="bar"></div>
<form method="POST" action="FrontController">
    <input type="hidden" name="command" value="AddProduct">
    <p>name: </p>
    <input type="text" name="name" pattern="[A-Za-z0-9]{,20}">
    <p>price: </p>
    <input type="number" name="price" step="0.01" min="0">
    <p>quantity: </p>
    <input type="number" name="quantity" min="0">
    <p>Type: </p>
    <select name="productType" required autofocus>
        <option value="ROD">rod</option>
        <option value="HOOK">hook</option>
        <option value="LINE">line</option>
        <option selected value="ANY" disabled></option>
    </select>
    <button type="submit">Add product</button>
</form>

</body>
</html>
