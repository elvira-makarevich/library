<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new author</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <script src="resources/js/registrationAuthor.js"></script>
    <script src="resources/js/registrationCommon.js"></script>
    <script src="resources/js/common.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<form id="saveAuthor" class="register" method="post" action="Controller" enctype="multipart/form-data">
    <input type="hidden" name="command" value="add_new_author"/>
    <h1>New author</h1>
    <p id="fileError" class="error"></p>
    <div class="img-item">
        <img id="image" class="img-item" />
    </div>

    <div class="file">
        <label for="file">Photo:</label>
        <input type="file" id="file" name="file" accept="image/jpeg,image/png,image/gif"/>
    </div>

    <br>
    <div class="fields">
        <label for="firstName">First name:</label>
        <input type="text" name="firstName" id="firstName">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="lastName">Last name:</label>
        <input type="text" name="lastName" id="lastName">
        <span class="error" aria-live="polite"></span>
    </div>
    <br><br>
    <input type="submit" name="submit" id="submitButton" value="Save">
</form>
<input id="pageContext" type="text" name="" value="${pageContext.request.contextPath}" style="display: none;">
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
