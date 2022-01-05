<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Indicate violation</title>

    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <script src="resources/js/bookViolation.js"></script>

</head>
<body>
<form id="bookViolation" class="bookViolation" action="Controller" method="post">
    <input type="hidden" name="command" value="indicate_book_violation_and_change_cost"/>
    <h1>Book violation</h1>
    <input type="hidden" name="copyId" value="${copyId}"/>
    <input type="hidden" name="orderId" value="${orderId}"/>
    <div><label>Title: </label>
        <input type="text" value="${title}" readonly>
    </div>
    <br> <br>
    <div><label>Old cost per day, Br: </label>
        <input type="text" value="${costPerDay}" readonly>
    </div>
    <br> <br>
    <div><label>New cost per day, Br: </label>
        <input type="text" value="" name="newCostPerDay">
    </div>
    <br> <br>
    <p id="messageError" class="error"></p>
    <div><label>Violation: </label><br><br>
        <textarea id="violationMessage" rows="10" cols="60" name="violationMessage"></textarea>
    </div>
    <br> <br>

    <div class="containerFiles"><p id="filesError" class="error"></p>
        <label for="files">Image(s):</label>
        <input id="files" type="file" name="images" multiple accept="image/jpeg,image/png,image/gif">
    </div>
    <br>
    <div class="img-item" id="fileListDisplay"></div>
    <br> <br>
    <input type="submit" name="submit" id="submitButton" value="Save">
</form>


<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;">
</body>
</html>
