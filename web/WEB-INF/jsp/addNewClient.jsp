
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new client</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">


    <script>
        window.onload = () => init();

        function init() {
            setMaxDateOfBirth();
            document.getElementById("file").addEventListener('change', loadImage);

        }


        function loadImage() {

            let fileInput = document.getElementById("file");
            let file = fileInput.files[0];

            let image = document.getElementById("image");
            let reader = new FileReader();
            reader.onload = function () {
                image.src = reader.result;
            };
            reader.readAsDataURL(file);
        }

        function setMaxDateOfBirth() {

            let dateOfBirth = document.getElementById('dateOfBirth');
            let d = new Date();
            let day = d.getDate();
            let month = d.getMonth() + 1;
            let year = d.getFullYear();
            let today = year + "-" + month + "-" + day;
            dateOfBirth.setAttribute('max', today);
        }

    </script>
</head>
<body>
<div id="registerClientForm">

    <div class="img-item">
        <img id="image" class="img-item"/>
    </div>

    <div class="file">
        <input type="file" id="file" name="file" accept="image/jpeg,image/png,image/gif"/>
    </div>

    <br>
    <div class="fields">
        <label for="firstName">First name:</label>
        <input type="text" name="firstName" id="firstName">
    </div>
    <br>

    <div class="fields">
        <label for="lastName">Last name:</label>
        <input type="text" name="lastName" id="lastName">
    </div>
    <br>

    <div class="fields">
        <label for="patronymic">Patronymic:</label>
        <input type="text" name="patronymic" id="patronymic">
    </div>

    <br>
    <div class="fields">
        <label for="email">Email:</label>
        <input type="email" name="email" id="email">
    </div>

    <br>
    <div class="fields">
        <label for="dateOfBirth">Birthday:</label>
        <input type="date" name="dateOfBirth" id="dateOfBirth" min="1900-01-01">
    </div>
    <br>

    <div class="fields">
        <label for="passportNumber">Passport number:</label>
        <input type="text" name="passportNumber" id="passportNumber">
    </div>
    <br>

</div>


</body>
</html>
