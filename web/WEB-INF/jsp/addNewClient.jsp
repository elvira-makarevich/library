<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new client</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">


    <script>


    </script>
</head>
<body>
<form id="registerClientForm" class="registerClient">
    <input type="hidden" name="command" value="add_new_client"/>

    <p id="fileError" class="error"></p>
    <div class="img-item">
        <img id="image" class="img-item"/>
    </div>

    <div class="file">
        <label for="file">Photo:</label>
        <input type="file" id="file" name="file" accept="image/jpeg,image/png,image/gif"/>
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

    <input type="submit" name="submit" id="submitButton" value="Save">
</form>

<input id="pageContext" type="text" name="" value="${pageContext.request.contextPath}" style="display: none;">
</body>
</html>
