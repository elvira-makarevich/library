 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new client</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <script src="resources/js/registrationClient.js"></script>
    <script src="resources/js/registrationCommon.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<form id="registerClientForm" class="register">
    <input type="hidden" name="command" value="add_new_client"/>

    <h1>New client</h1>
    <p id="fileError" class="error"></p>
    <div class="img-item">
        <img id="image" class="img-item" />
    </div>

    <div class="file">
        <label for="file">Photo:</label>

        <input type="file" id="file" name="file" accept="image/jpeg,image/png,image/gif" hidden/>
    </div>

    <br>
    <div class="fields">
        <label for="firstName">First name:</label>
        <input type="text" name="firstName" id="firstName">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="lastName">Last name:</label>
        <input type="text" name="lastName" id="lastName">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="patronymic">Patronymic:</label>
        <input type="text" name="patronymic" id="patronymic">
        <span class="error" aria-live="polite"></span>
    </div>

    <br>
    <div class="fields">
        <label for="email">Email:</label>
        <input type="email" name="email" id="email">
        <span class="error" aria-live="polite"></span>
    </div>

    <br>
    <div class="fields">
        <label for="dateOfBirth">Birthday:</label>
        <input type="date" name="dateOfBirth" id="dateOfBirth" min="1900-01-01">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="passportNumber">Passport number:</label>
        <input type="text" name="passportNumber" id="passportNumber">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="postcode">Postcode:</label>
        <input type="text" name="postcode" id="postcode">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="country">Country:</label>
        <input type="text" name="country" id="country">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="locality">Locality:</label>
        <input type="text" name="locality" id="locality">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="street">Street:</label>
        <input type="text" name="street" id="street">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="houseNumber">House number:</label>
        <input type="text" name="houseNumber" id="houseNumber">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="building">Building:</label>
        <input type="text" name="building" id="building">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <div class="fields">
        <label for="apartmentNumber">Apartment number:</label>
        <input type="text" name="apartmentNumber" id="apartmentNumber">
        <span class="error" aria-live="polite"></span>
    </div>
    <br>

    <input type="submit" name="submit" id="submitButton" value="Save">
</form>

<input id="pageContext" type="text" name="" value="${pageContext.request.contextPath}" style="display: none;">
</body>
</html>
