<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <title>Main page</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">

</head>

<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="button_in_line">
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="go_to_add_new_book_page"/>
        <button class="button">Add new book</button>
    </form>
</div>
<div class="button_in_line">
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="go_to_add_new_client_page"/>
        <button class="button">Add new client</button>
    </form>
</div>
<div class="button_in_line">
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="go_to_all_books_page"/>
        <button class="button">All books</button>
    </form>
</div>
<div class="button_in_line">
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="go_to_all_clients_page"/>
        <button class="button">All clients</button>
    </form>
</div>
<div class="button_in_line">
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="go_to_new_order_page"/>
        <button class="button">New order</button>
    </form>
</div>
<div class="button_in_line">
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="go_to_close_order_page"/>
        <button class="button">Close order</button>
    </form>
</div>

<div class="button_in_line">
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="go_to_profitability_page"/>
        <button class="button">Check profitability</button>
    </form>
</div>

<div class="button_in_line">
    <form method="post" action="Controller">
        <input type="hidden" name="command" value="go_to_writing_off_books_page"/>
        <button class="button">Writing off books</button>
    </form>
</div>
<div class="mostPopularBooksArea">
    <jsp:include page="/WEB-INF/jsp/mostPopular.jsp"/>
</div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
