<%--
  Created by IntelliJ IDEA.
  User: klimb
  Date: 8/30/2021
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="resources/js/BarBuilder.js"></script>
    <script type="text/javascript" src="resources/js/ajaxTableBuilder.js"></script>
    <script type="text/javascript" src="resources/js/searchBuilder.js"></script>
</head>

<body onload="buildProductTable('productList', 'tableScroller', 1); buildBar('bar', '${sessionScope.role}')">

<div class="barStyle" id="bar"></div>
<div class="search" id="search"></div>
<div class="product_list" id="productList"></div>
<div id="tableScroller"></div>
</body>
</html>
