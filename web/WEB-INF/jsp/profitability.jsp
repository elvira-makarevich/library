<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profitability</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <script src="resources/js/checkProfitability.js"></script>
    <script src="resources/js/common.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<form id="profitability" class="profitability" action="Controller" method="post">
    <input type="hidden" name="command" value="check_profitability"/>
    <h1>Check profitability</h1>

    <div class="fields">
        <label for="from">From:</label><br><br>
        <input type="date" name="from" id="from" min="2020-01-01">
        <span class="error" aria-live="polite"></span>
    </div>
    <br><br>
    <div class="fields">
        <label for="from">To:</label><br><br>
        <input type="date" name="to" id="to" min="2020-01-01">
        <span class="error" aria-live="polite"></span>
    </div>
    <br><br>
    <input type="submit" class="btnSubmit" name="submit" id="submitButton" value="Check">
</form>

<div id="profitInfo">
</div>

<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;">
<footer>
    <div class="editcontainer">© Lab iTechArt
    </div>
</footer>
</body>
</html>
