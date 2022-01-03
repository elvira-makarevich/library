<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Indicate violation</title>

    <link rel="stylesheet" type="text/css" href="resources/css/common.css">

    <script>
        window.onload = () => init();

        function init() {
            document.getElementById("files").addEventListener('change', loadImages);
            document.getElementById("violationMessage").addEventListener('change', checkMessage);
            document.getElementById("files").addEventListener('submit', checkImages);
            let formViolation = document.getElementById('bookViolation');
            formViolation.addEventListener('submit', function (event) {
                event.preventDefault();
                checkMessage();
                checkImages();
                if (checkMessage() && checkImages()) {
                    submitValidForm();
                }
            });
        }

        async function submitValidForm() {

            let formData = new FormData(document.getElementById('bookViolation'));
            let pageContext = document.getElementById('pageContext').value;
            let url = pageContext + "/Controller?command=indicate_book_violation_and_change_cost";

            let response = await fetch(url, {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                alert("The information was saved.");
                close();
            } else {
                console.log("Error" + this.status);
                alert("Check the correctness of the entered data.");
            }

        }

        function checkImages() {

            let images = document.getElementById("files");
            let error = document.getElementById("filesError");
            error.innerHTML = "";

            if (images.value == "") {
                error.innerHTML = "Upload image(s) confirming the violation!";
                return false;
            }
            return true;
        }

        function checkMessage() {

            let message = document.getElementById("violationMessage").value;
            let error = document.getElementById("messageError");
            error.innerHTML = "";

            if (message == "") {
                error.textContent = "The field cannot be empty.";
                return false;
            } else {
                let regex = /.{10,500}$/;
                if (regex.test(message) === false) {
                    error.textContent = "Min length of the message should be 10 letters; max - 500 letters.";
                    return false;
                }
            }
            return true;
        }

        function loadImages() {

            let containerImages = document.getElementById("fileListDisplay");
            deleteImages();
            let fileInput = document.getElementById("files");
            let files = fileInput.files;
            let file;

            for (let i = 0; i < files.length; i++) {
                let image = document.createElement("img");
                let reader = new FileReader();
                file = files[i];
                reader.onload = function () {

                    image.className = "img-item";
                    image.src = reader.result;
                };
                reader.readAsDataURL(file);
                containerImages.appendChild(image);

            }
            checkImages();
        }

        function deleteImages() {
            let div = document.getElementById('fileListDisplay');
            while (div.firstChild) {
                div.removeChild(div.firstChild);
            }
        }
    </script>
</head>
<body>
<form id="bookViolation" class="bookViolation" action="Controller" method="post">
    <input type="hidden" name="command" value="indicate_book_violation_and_change_cost"/>
    <h1>Book violation</h1>
    <input type="hidden" name="copyId" value="${copyId}"/>
    <input type="hidden" name="orderId" value="${orderId}"/>
    <div><label>Title: </label>
        <input type="text" value="${title}" readonly>
    </div>
    <br> <br>
    <div><label>Old cost per day, Br: </label>
        <input type="text" value="${costPerDay}" readonly>
    </div>
    <br> <br>
    <div><label>New cost per day, Br: </label>
        <input type="text" value="" name="newCostPerDay">
    </div>
    <br> <br>
    <p id="messageError" class="error"></p>
    <div><label>Violation: </label><br><br>
        <textarea id="violationMessage" rows="10" cols="60" name="violationMessage"></textarea>
    </div>
    <br> <br>

    <div class="containerFiles"><p id="filesError" class="error"></p>
        <label for="files">Image(s):</label>
        <input id="files" type="file" name="images" multiple accept="image/jpeg,image/png,image/gif">
    </div>
    <br>
    <div class="img-item" id="fileListDisplay"></div>
    <br> <br>
    <input type="submit" name="submit" id="submitButton" value="Save">
</form>


<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;">
</body>
</html>
