<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new book</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <script src="resources/js/registrationBook.js"></script>

</head>
<body>

<input id="initials" type="text" name="" placeholder="Enter last name">
<button id="findAuthor" class="">Find author</button>
<br>
<br>
<div id="possibleAuthorContainer">
</div>
<br>

<form method="post" action="Controller">
    <input type="hidden" name="command" value="go_to_add_new_author_page"/><br>
    <button class="button">Save new author</button>
</form>

<br> <br>
<form id="saveBook" class="registerBook" action="Controller" method="post" name="registerBook"
      enctype="multipart/form-data">

    <input type="hidden" name="command" value="add_new_book"/>

    <div id="realAuthorContainer">
        <div><span class="errorAuthor" aria-live="polite"></span></div>
        <br>
        <label>Author(s):</label>
    </div>
    <br>
    <div class="titleBook">
        <label for="title">Title:</label>
        <input type="text" name="title" id="title" required minlength="2" maxlength="70">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="titleBook">
        <label for="originalTitle">Original title:</label>
        <input type="text" name="originalTitle" id="originalTitle" minlength="2" maxlength="70">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="genres">
        <div><span class="errorGenre" aria-live="polite"></span></div>
        <br><label>Genre(s):</label><br>
        <input type="checkbox" class="genre" name="genres" value="Fiction">Fiction<br>
        <input type="checkbox" class="genre" name="genres" value="Non_fiction">Non-fiction<br>
        <input type="checkbox" class="genre" name="genres" value="Business">Business<br>
        <input type="checkbox" class="genre" name="genres" value="Novel">Novel<br>
        <input type="checkbox" class="genre" name="genres" value="History">History<br>
        <input type="checkbox" class="genre" name="genres" value="Detective">Detective<br>
        <input type="checkbox" class="genre" name="genres" value="Fantasy">Fantasy<br>
        <input type="checkbox" class="genre" name="genres" value="Biography">Biography<br>
        <input type="checkbox" class="genre" name="genres" value="Thriller">Thriller<br>
        <input type="checkbox" class="genre" name="genres" value="Health">Health<br>
        <input type="checkbox" class="genre" name="genres" value="Children">Children<br>
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
    <div class="containerFiles"><p id="filesError" class="error"></p>
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
