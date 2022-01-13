<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Close order</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/table.css">
    <script src="resources/js/closeOrder.js"></script>
    <script src="resources/js/formAndCloseOrder.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div id="findClientArea" class="findClientArea">
    <h3>Find client area</h3>
    <input id="initials" type="text" name="" placeholder="Enter last name">
    <button id="findClient" class="" style="inline-size: auto;">Find client</button>
    <br> <br>
    <div id="possibleClientContainer">
    </div>

</div>

<form id="closeOrder" class="closeOrder" action="Controller" method="post">

    <input type="hidden" name="command" value="close_order"/>
    <h1>Close order</h1>

    <p id="clientError" class="error"></p>
    <div class="clientContainer"><label>Client: </label><br><br>
        <div id="realClientContainer">
        </div>
    </div>
    <br><br>
    <div class="realBooksContainer">
        <div id="realBooksContainer"><label>Books:</label><br><br>
        </div>
    </div>
    <br> <br>
    <div><label>Order date:</label>
        <input id="inputOrderDate" type="text" readonly>
    </div>

    <br>
    <div><label>Possible return date:</label>
        <input id="inputMaxReturnDate" type="text" readonly>
    </div>
    <br>
    <div><label>Return date:</label>
        <input id="inputReturnDate" type="text" readonly>
    </div>
    <br><br>
    <p id="penaltyError" class="error"></p>
    <div><label>Penalty, Br:</label>
        <input id="penalty" type="text" value="0">
    </div>
    <br><br>
    <div><label>Total cost, Br:</label>
        <input id="totalCost" name="totalCost" type="text" readonly>
    </div>

    <br><br>
    <input type="submit" name="submit" id="submitButton" value="Close order">
</form>

<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;">
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
