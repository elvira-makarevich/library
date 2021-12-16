<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new author</title>
    <script src="resources/js/loadImage.js"></script>
</head>
<body>

<form method="post" action="Controller" enctype="multipart/form-data">
    <input type="hidden" name="command" value="add_new_author"/>
    <input type="file" id="file" name="file" accept="image/jpeg,image/png,image/gif" />
    <img id="image" width="150px" height="180px"/>
    <br><br>
    <input type="text" name="firstName" placeholder="Имя автора"
           title="Имя должно содержать от 2 до 20 символов"/><br/>
    <input type="text" name="lastName" placeholder="Фамилия автора"
           title="Фамилия должна содержать от 2 до 20 символов"/><br/>
    <button class="button">Add new author</button>
</form>


</body>
</html>
