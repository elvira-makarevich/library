<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New order</title>

    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/table.css">
    <script src="resources/js/formOrder.js"></script>
    <script src="resources/js/formAndCloseOrder.js"></script>
    <script src="resources/js/common.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div id="findClientArea" class="findClientArea">
    <h3>Find client area</h3>
    <input id="initials" type="text" name="" placeholder="Enter last name">
    <button id="findClient" class="btnAction" style="inline-size: auto;">Find client</button>
    <button id="addClient" class="btnAction" style="inline-size: auto;">Add new client</button>
    <br> <br>

    <div id="possibleClientContainer">
    </div>

</div>

<div id="findBookArea" class="findBookArea">
    <h3>Find book area</h3>
    <input id="title" class="titleBook" type="text" name="" placeholder="Enter title">
    <button id="findBook" class="btnAction" style="inline-size: auto;">Find book</button>
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
        <input id="inputMaxReturnDate" class="dates" type="text" readonly>
    </div>

    <br><br>
    <div><label>Preliminary cost, Br:</label>
        <input id="preliminaryCost" class="number" name="preliminaryCost" type="text" readonly>
    </div>
    <br><br>
    <input type="submit" class="btnSubmit" name="submit" id="submitButton" value="Save">
</form>

<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;">
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
