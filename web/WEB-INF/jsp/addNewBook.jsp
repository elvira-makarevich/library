<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new book</title>
    <script src="resources/js/loadImage.js"></script>
    <script>
        window.onload = () => init();

        function init() {
            document.getElementById('findAuthor').addEventListener("click", checkParam);

        }

        function checkParam() {

            let initials = document.getElementById('initials');
            let initialsValue = initials.value;
            if (initialsValue.length < 2) {
                alert("Недопустимая длина строки");
            } else
                alert("Отправка разрешена");
            findAuthorRequest();

        }


        function findAuthorRequest() {
            let xhr = new XMLHttpRequest();
            let initials = document.getElementById('initials');
            let initialsValue = initials.value;
            let param = 'lastName=' + initialsValue;
            alert(initialsValue);
            xhr.open("GET", '${pageContext.request.contextPath}/Controller?command=find_author&' + param, false);
            xhr.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
            xhr.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    let answer = JSON.parse(this.responseText);
                    alert(answer.name);
                    document.getElementById("demo").innerHTML = answer.name;
                }
            };
            xhr.send();

        }


    </script>
</head>
<body>

<input id="initials" type="text" name="" placeholder="Введите фамилию автора">
<button id="findAuthor" class="">НАЙТИ АВТОРА</button>
<input id="demo" type="text" name="" >


<br> <br>
<form action="Controller" method="post">
    <input type="hidden" name="command" value="add_new_book"/>

    <input type="text" name="title" placeholder="Название"> <br>
    <input type="text" name="originalTitle" placeholder="Название на языке оригинала"> <br>
    <br>
    <input type="checkbox" name="Fiction" value="Fiction">Художественная литература<br>
    <input type="checkbox" name="Non_fiction" value="Non-fiction">Научная литература<br>
    <input type="checkbox" name="Business" value="Business">Бизнес<br>
    <input type="checkbox" name="Novel" value="Novel">Роман<br>
    <input type="checkbox" name="History" value="History">История<br>
    <input type="checkbox" name="Detective" value="Detective">Детектив<br>
    <input type="checkbox" name="Fantasy" value="Fantasy">Фантастика<br>
    <input type="checkbox" name="Biography" value="Biography">Биография<br>
    <input type="checkbox" name="Thriller" value="Thriller">Триллер<br>
    <input type="checkbox" name="Health" value="Health">Здоровье<br>
    <input type="checkbox" name="Children" value="Children">Детская литература<br>
    <br>
    <input type="text" name="price" placeholder="Цена"> <br>
    <input type="text" name="numberOfCopies" placeholder="Количество экземпляров"> <br>
    <input type="text" name="author" placeholder="Авторы"> <br>

    <div class="form-row">
        <div class="img-list" id="js-file-list"></div>
        <input id="file" type="file" name="covers" multiple accept="image/jpeg,image/png,image/gif"> <br>
    </div>
    <img id="image" width="150px" height="180px"/>
    <br><br>


    <input type="text" name="costPerDay" placeholder="Цена за день использования"> <br>
    <input type="text" name="publishingYear" placeholder="Год издания"> <br>
    <input type="text" name="numberOfPages" placeholder="Количество страниц"> <br>


    <input type="submit" name="submit" value="Сохранить">
</form>


</body>
</html>
