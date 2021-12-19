<%--
  Created by IntelliJ IDEA.
  User: makar
  Date: 17.12.2021
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>
        window.onload = () => init();
        function init() {
            let idAuthor = document.getElementById('hiddenId').value;
            let img = document.getElementById('imageAuthor');
            img.src = "${pageContext.request.contextPath}/Controller?command=find_author_image&id=" + idAuthor;
        }
    </script>

</head>
<body>

<input id="hiddenId" type="hidden" name="id" value="1"/>
<img id="imageAuthor" width="150px" height="180px">

</body>
</html>
