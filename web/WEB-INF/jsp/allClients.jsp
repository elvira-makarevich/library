<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All clients</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/navigation.css">
    <link rel="stylesheet" type="text/css" href="resources/css/sortTable.css">
    <script src="resources/js/allBooksOrClients.js"></script>
    <script src="resources/js/viewTableClients.js"></script>

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
<div class="allBooksOrClients">
    <h1>Readers</h1>

    <div id="clientsTable"></div>
    <br>
    <div id="pagination"></div>
    <input id="pageContext" type="text" name=""
           value="${pageContext.request.contextPath}/Controller?command=view_all_clients&currentPage="
           style="display: none;">
    <input id="numberOfPages" type="text" name="" value="${numberOfPages}" style="display: none;">
    <input id="currentPage" type="text" name="" value="${currentPage}" style="display: none;">
</div>
</body>
</html>
