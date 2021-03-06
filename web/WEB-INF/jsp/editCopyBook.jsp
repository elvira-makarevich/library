<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit book</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <script src="resources/js/editCopyBook.js"></script>
    <script src="resources/js/common.js"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<form id="editCopyBook" class="edit" action="Controller" method="post">
    <input type="hidden" name="command" value="change_cost_per_day"/>
    <h1>Edit copy of the book</h1>
    <input type="hidden" name="copyId" value="${copyId}"/>
    <div><label>Title: </label>
        <input type="text" class="titleBook" value="${title}" readonly>
    </div>
    <br> <br>
    <div><label>Old cost per day, Br: </label>
        <input type="text" class="number" value="${costPerDay}" readonly>
    </div>
    <br> <br>
    <div class="costPerDay"><label>New cost per day, Br: </label>
        <input type="text" class="number" value="" name="newCostPerDay" id="costPerDay" pattern="^[0-9]{1,}[.,]?[0-9]{0,2}" required>
        <span class="error" aria-live="polite"></span>
    </div>
    <br> <br>
    <input type="submit" class="btnSubmit" name="submit" id="submitButton" value="Save">
</form>


<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;">
<footer>
    <div class="editcontainer">© Lab iTechArt
    </div>
</footer>
</body>
</html>
