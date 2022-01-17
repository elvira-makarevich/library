<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Writing off books</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/table.css">
    <script src="resources/js/writingOffBooks.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div id="findBookArea" class="writeOff">
    <h3>Find book area</h3>
    <input id="title" type="text" name="" placeholder="Enter title">
    <button id="findBook" class="" style="inline-size: auto;">Find books</button>
    <br><br><br>

    <div id="possibleBookContainer">
    </div>
    <br>
</div>

<form id="writeOff" class="writeOff" action="Controller" method="post">
    <input type="hidden" name="command" value="write_off_copy_books"/>
    <h1>Writing off</h1>

    <div><label>Date:</label>
        <input id="dateOfWritingOff" type="text" readonly>
    </div>

    <p id="booksError" class="error"></p>
    <div class="realBooksContainer">
        <div id="realBooksContainer"><label>Books:</label><br><br>
        </div>
    </div>
    <br><br>
    <input type="submit" name="submit" id="submitButton" value="Save">
</form>


<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;">
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
