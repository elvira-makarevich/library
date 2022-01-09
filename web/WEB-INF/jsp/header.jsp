<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
</head>
<body>

<c:url var="mainPage" value="/Controller">
    <c:param name="command" value="go_to_main_page"/>
</c:url><a href=${mainPage}>
    <div class="logoDiv">
        <img class="logoImage" src="resources/images/owl.jpg"></div>
</a>
</body>
</html>
