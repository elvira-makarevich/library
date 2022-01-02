<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New order</title>

    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/table.css">
    <script src="resources/js/formOrder.js"></script>

</head>
<body>
<div id="findClientArea" class="findClientArea">

    <input id="initials" type="text" name="" placeholder="Enter last name">
    <button id="findClient" class="" style="inline-size: auto;">Find client</button>
    <button id="addClient" style="inline-size: auto;">Add client</button>
    <br> <br>

    <div id="possibleClientContainer">
    </div>

</div>

<div id="findBookArea" class="findBookArea">

    <input id="title" type="text" name="" placeholder="Enter title">
    <button id="findBook" class="" style="inline-size: auto;">Find book</button>
    <br>

    <div id="possibleBookContainer">
    </div>
    <br>
</div>


<form id="saveOrder" class="saveOrder" action="Controller" method="post">
    <input type="hidden" name="command" value="save_order"/>
    <h1>Order</h1>

    <p id="clientError" class="error"></p>
    <div class="clientContainer"><label>Client:</label><br><br>
        <div id="realClientContainer">
        </div>
    </div>
    <br>
    <p id="booksError" class="error"></p>
    <div class="realBooksContainer">
        <div id="realBooksContainer"><label>Books:</label><br><br>
        </div>
    </div>

    <br><br>
    <div><label>Book return date:</label>
        <input id="inputMaxReturnDate" type="text" readonly>
    </div>

    <br><br>
    <div><label>Preliminary cost, Br:</label>
        <input id="preliminaryCost" name="preliminaryCost" type="text" readonly>
    </div>
    <br><br>
    <input type="submit" name="submit" id="submitButton" value="Save">
</form>

<input id="pageContextAddClient" type="text" name=""
       value="${pageContext.request.contextPath}/Controller?command=go_to_add_new_client_page" style="display: none;">

<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;"></body>
</html>
