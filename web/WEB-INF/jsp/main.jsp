
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Главная</title>
</head>

<body>

<form method="post" action="Controller" >
    <input type="hidden" name="command" value="go_to_add_new_author_page"/>
        <button class="button">Add new author</button>
</form>
<br>
<br>
<br>

<form method="post" action="Controller" >
    <input type="hidden" name="command" value="go_to_add_new_client_page"/>
    <button class="button">Add new client</button>
</form>

</body>
</html>
