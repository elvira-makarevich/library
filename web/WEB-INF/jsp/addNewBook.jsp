<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new book</title>
    <link rel="stylesheet" type="text/css" href="resources/css/registrationBook.css">
    <script src="resources/js/registrationBook.js"></script>

</head>
<body>

<input id="initials" type="text" name="" placeholder="Введите фамилию автора">
<button id="findAuthor" class="">НАЙТИ АВТОРА</button>
<br>

<div id="possibleAuthorContainer">
</div>


<form method="post" action="Controller">
    <input type="hidden" name="command" value="go_to_add_new_author_page"/>
    <button class="button">Добавить нового автора</button>
</form>

<br> <br>
<form id="saveBook" action="Controller" method="post" enctype="multipart/form-data">

    <input type="hidden" name="command" value="add_new_book"/>

    <div id="realAuthorContainer">
        <div><span class="errorAuthor" aria-live="polite"></span></div>
        <br>
        Автор(ы):
    </div>
    <br>
    <div class="titleBook">
        <label for="title">Название:</label>
        <input type="text" name="title" id="title" required minlength="2" maxlength="50">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="titleBook">
        <label for="originalTitle">Название на языке оригинала:</label>
        <input type="text" name="originalTitle" id="originalTitle">
    </div>
    <br>
    <div class="genres">
        <div><span class="errorGenre" aria-live="polite"></span></div>
        <br>
        <input type="checkbox" class="genre" name="genres" value="Fiction">Художественная литература<br>
        <input type="checkbox" class="genre" name="genres" value="Non_fiction">Научная литература<br>
        <input type="checkbox" class="genre" name="genres" value="Business">Бизнес<br>
        <input type="checkbox" class="genre" name="genres" value="Novel">Роман<br>
        <input type="checkbox" class="genre" name="genres" value="History">История<br>
        <input type="checkbox" class="genre" name="genres" value="Detective">Детектив<br>
        <input type="checkbox" class="genre" name="genres" value="Fantasy">Фантастика<br>
        <input type="checkbox" class="genre" name="genres" value="Biography">Биография<br>
        <input type="checkbox" class="genre" name="genres" value="Thriller">Триллер<br>
        <input type="checkbox" class="genre" name="genres" value="Health">Здоровье<br>
        <input type="checkbox" class="genre" name="genres" value="Children">Детская литература<br>
    </div>
    <br>
    <div class="price">
        <label for="price">Цена, бел. руб.:</label>
        <input type="text" name="price" id="price" pattern="^[0-9]{0,}[.,]?[0-9]{0,2}" required>
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="costPerDay">
        <label for="costPerDay">Стоимость за день использования, бел. руб.:</label>
        <input type="text" name="costPerDay" id="costPerDay" pattern="^[0-9]{0,}[.,]?[0-9]{0,2}" required>
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="numberOfCopies">
        <label for="numberOfCopies">Количество экземпляров:</label>
        <input type="number" name="numberOfCopies" id="numberOfCopies" min="1" pattern="\d+" required>
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="containerFiles">

        <input id="files" type="file" name="covers" multiple accept="image/jpeg,image/png,image/gif" required>
    </div>
    <br>
    <div class="img-item" id="fileListDisplay"></div>
    <br>
    <div class="publishingYear">
        <label for="publishingYear">Год издания:</label>
        <input type="number" name="publishingYear" id="publishingYear" min="868" max="2022">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="numberOfPages">
        <label for="numberOfPages">Количество страниц:</label>
        <input type="number" name="numberOfPages" id="numberOfPages" min="1" max="2000" pattern="\d+">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="registrationDate">
        <label for="registrationDate">Дата регистрации:</label>
        <input id="registrationDate" type="text" readonly>
    </div>
    <br>
    <input type="submit" name="submit" id="submitButton" value="Сохранить">

</form>

<input id="pageContext" type="text" name="" value="${pageContext.request.contextPath}" style="display: none;">
</body>
</html>
