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
<button id="findAuthor" class="">Find author</button>
<br>
<br>
<div id="possibleAuthorContainer">
</div>
<br>

<form method="post" action="Controller">
    <input type="hidden" name="command" value="go_to_add_new_author_page"/><br>
    <button class="button">Add new author</button>
</form>

<br> <br>
<form id="saveBook" action="Controller" method="post" enctype="multipart/form-data">

    <input type="hidden" name="command" value="add_new_book"/>

    <div id="realAuthorContainer">
        <div><span class="errorAuthor" aria-live="polite"></span></div>
        <br>
        Author(s):
    </div>
    <br>
    <div class="titleBook">
        <label for="title">Title:</label>
        <input type="text" name="title" id="title" required minlength="2" maxlength="50">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="titleBook">
        <label for="originalTitle">Original title:</label>
        <input type="text" name="originalTitle" id="originalTitle">
    </div>
    <br>
    <div class="genres">
        <div><span class="errorGenre" aria-live="polite"></span></div>
        <br>
        <input type="checkbox" class="genre" name="genres" value="Fiction">FICTION<br>
        <input type="checkbox" class="genre" name="genres" value="Non_fiction">NON-FICTION<br>
        <input type="checkbox" class="genre" name="genres" value="Business">BUSINESS<br>
        <input type="checkbox" class="genre" name="genres" value="Novel">NOVEL<br>
        <input type="checkbox" class="genre" name="genres" value="History">HISTORY<br>
        <input type="checkbox" class="genre" name="genres" value="Detective">DETECTIVE<br>
        <input type="checkbox" class="genre" name="genres" value="Fantasy">FANTASY<br>
        <input type="checkbox" class="genre" name="genres" value="Biography">BIOGRAPHY<br>
        <input type="checkbox" class="genre" name="genres" value="Thriller">THRILLER<br>
        <input type="checkbox" class="genre" name="genres" value="Health">HEALTH<br>
        <input type="checkbox" class="genre" name="genres" value="Children">CHILDREN<br>
    </div>
    <br>
    <div class="price">
        <label for="price">Price, BYN:</label>
        <input type="text" name="price" id="price" pattern="^[0-9]{0,}[.,]?[0-9]{0,2}" required>
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="costPerDay">
        <label for="costPerDay">Cost per day, BYN:</label>
        <input type="text" name="costPerDay" id="costPerDay" pattern="^[0-9]{0,}[.,]?[0-9]{0,2}" required>
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="numberOfCopies">
        <label for="numberOfCopies">Number of copies:</label>
        <input type="number" name="numberOfCopies" id="numberOfCopies" min="1" pattern="\d+" required>
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="containerFiles"><p>
        <label for="files">Cover (s):</label>
        <input id="files" type="file" name="covers" multiple accept="image/jpeg,image/png,image/gif" required>
    </div>
    <br>
    <div class="img-item" id="fileListDisplay"></div>
    <br>
    <div class="publishingYear">
        <label for="publishingYear">Publishing year:</label>
        <input type="number" name="publishingYear" id="publishingYear" min="868" max="2022">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="numberOfPages">
        <label for="numberOfPages">Number of pages:</label>
        <input type="number" name="numberOfPages" id="numberOfPages" min="1" max="2000" pattern="\d+">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="registrationDate">
        <label for="registrationDate">Registration date:</label>
        <input id="registrationDate" type="text" readonly>
    </div>
    <br>
    <input type="submit" name="submit" id="submitButton" value="Save">

</form>

<input id="pageContext" type="text" name="" value="${pageContext.request.contextPath}" style="display: none;">
</body>
</html>
