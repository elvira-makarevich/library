
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Главная</title>

</head>

<body>

<form method="post" action="Controller" >
    <input type="hidden" name="command" value="go_to_author_info_page"/>
      <button class="button">Author image</button>
</form>
<br>
<br>
<form method="post" action="Controller" >
    <input type="hidden" name="command" value="go_to_add_new_author_page"/>
    <button class="button">Add new author</button>
</form>

<form method="post" action="Controller" >
    <input type="hidden" name="command" value="go_to_add_new_book_page"/>
        <button class="button">Add new book</button>
</form>

<br>
<br>

<form method="post" action="Controller" >
    <input type="hidden" name="command" value="go_to_add_new_client_page"/>
    <button class="button">Add new client</button>
</form>

<br>
<br>

<form method="post" action="Controller" >
    <input type="hidden" name="command" value="go_to_all_books_page"/>
    <button class="button">All books</button>
</form>

<form method="post" action="Controller" >
    <input type="hidden" name="command" value="go_to_all_clients_page"/>
    <button class="button">All clients</button>
</form>

</body>
</html>
