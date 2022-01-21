<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new book</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/table.css">
    <script src="resources/js/registrationBook.js"></script>
    <script src="resources/js/common.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div id="findAuthorArea" class="register">
    <h3>Find author(s) area</h3>
    <input id="initials" class="initials" type="text" name="" placeholder="Enter last name">
    <button id="findAuthor" class="btnAction" style="inline-size: auto;">Find author</button>
    <button id="addAuthor" class="btnAction" style="inline-size: auto;">Add new author</button>
    <br> <br>

    <div id="possibleAuthorContainer">
    </div>

</div>


<br> <br>
<form id="saveBook" class="register" action="Controller" method="post" name="registerBook"
      enctype="multipart/form-data">
    <h1>New book</h1>
    <input type="hidden" name="command" value="add_new_book"/>

    <div id="realAuthorContainer">
        <div><span class="errorAuthor" aria-live="polite"></span></div>
        <br>
        <label>Author(s):</label>
    </div>
    <br>
    <div>
        <label for="title">Title:</label>
        <input type="text" class="titleBook" name="title" id="title" required minlength="2" maxlength="70">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div>
        <label for="originalTitle">Original title:</label>
        <input type="text" class="titleBook" name="originalTitle" id="originalTitle" minlength="2" maxlength="70">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="genres">
        <div><span class="errorGenre" aria-live="polite"></span></div>
        <br><label>Genre(s):</label><br><br>
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
        <input type="text" class="number" name="price" id="price" pattern="^[0-9]{1,}[.,]?[0-9]{0,2}" required>
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="costPerDay">
        <label for="costPerDay">Cost per day, BYN:</label>
        <input type="text" class="number" name="costPerDay" id="costPerDay" pattern="^[0-9]{1,}[.,]?[0-9]{0,2}" required>
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="numberOfCopies">
        <label for="numberOfCopies">Number of copies:</label>
        <input type="number" class="number" name="numberOfCopies" id="numberOfCopies" min="1" pattern="\d+" required>
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
        <input type="number" class="number" name="publishingYear" id="publishingYear" min="868" max="2022">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="numberOfPages">
        <label for="numberOfPages">Number of pages:</label>
        <input type="number" class="number" name="numberOfPages" id="numberOfPages" min="1" max="2000" pattern="\d+">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>
    <div class="registrationDate">
        <label for="registrationDate">Registration date:</label>
        <input id="registrationDate" class="dates" type="text" readonly>
    </div>
    <br>
    <input type="submit" class="btnSubmit" name="submit" id="submitButton" value="Save">

</form>

<input id="pageContext" type="text" name="" value="${pageContext.request.contextPath}" style="display: none;">
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
