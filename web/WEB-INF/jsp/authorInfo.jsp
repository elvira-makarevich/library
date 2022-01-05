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
