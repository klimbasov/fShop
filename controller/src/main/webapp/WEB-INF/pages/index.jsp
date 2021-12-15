<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="resources/css/stylesheets.css">
    <script type="text/javascript" src="resources/js/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="resources/js/BarBuilder.js"></script>
</head>
<body onload="buildBar('bar', '${sessionScope.role}')">

<div id="bar"></div>

<h1>Hello my custom servlet</h1>
<FORM method="get" action="FrontController">
    <input type="hidden" name="command" value="ShowProductInfoPage"/>
    <button type="submit" name="pressMe" value="press">toInfo</button>
</FORM>
<FORM method="get" action="FrontController">
    <input type="hidden" name="command" value="ShowProductListPage"/>
    <button type="submit" name="pressMe" value="press">toList</button>
</FORM>
</body>
</html>
<!-- https://www.youtube.com/watch?v=lEfp6_ByY-Q -->