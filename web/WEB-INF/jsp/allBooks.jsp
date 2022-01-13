<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>All books</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/navigation.css">
    <link rel="stylesheet" type="text/css" href="resources/css/sortTable.css">
    <script src="resources/js/allBooksOrClients.js"></script>
    <script src="resources/js/viewTableBooks.js"></script>
    <style>
        th.sorted[data-order="-1"]::after {
            content: "▼"
        }

        th.sorted[data-order="1"]::after {
            content: "▲"
        }
    </style>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="allBooksOrClients">
<h1>All books</h1>

    <div id="booksTable"></div>
    <br>
    <div id="pagination"></div>
    <input id="pageContext" type="text" name=""
           value="${pageContext.request.contextPath}/Controller?command=view_all_books&currentPage="
           style="display: none;">


    <input id="numberOfPages" type="text" name="" value="${numberOfPages}" style="display: none;">
    <input id="currentPage" type="text" name="" value="${currentPage}" style="display: none;">
</div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>