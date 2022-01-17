window.onload = () => init();

 function init() {
    document.getElementById("files").addEventListener('change', loadImages);
    document.getElementById("violationMessage").addEventListener('change', checkMessage);
    document.getElementById("files").addEventListener('submit', checkImages);
    let formViolation = document.getElementById('bookViolation');
    formViolation.addEventListener('submit', async function (event) {
        event.preventDefault();
        checkMessage();
        checkImages();
        if (checkMessage() && checkImages()) {
            await submitValidFormAndCloseWindow();
        }
    });
}

async function submitValidFormAndCloseWindow() {

    let formData = new FormData(document.getElementById('bookViolation'));
    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + "/Controller?command=indicate_book_violation";

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