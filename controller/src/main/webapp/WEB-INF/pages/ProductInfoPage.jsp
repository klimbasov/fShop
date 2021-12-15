<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 10/11/2021
  Time: 8:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
    <script type="text/javascript" src="resources/js/BarBuilder.js"></script>
    <script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="resources/js/ProductInfoScript.js"></script>
</head>
<body class="product_info" onload="buildBar('bar', '${sessionScope.role}'); getProductInfo('${requestScope.product_id}', 'content', '${sessionScope.role}')">
    <div id="bar"></div>
    <div class="productInfo" id="content"></div>
</body>
</html>
