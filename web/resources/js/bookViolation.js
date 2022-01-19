window.onload = () => init();

function init() {
    document.getElementById("files").addEventListener('change', loadImages);
    document.getElementById("violationMessage").addEventListener('change', checkMessage);
    document.getElementById("files").addEventListener('submit', checkImages);
    document.getElementById('bookViolation').addEventListener('submit', async function (event) {
        event.preventDefault();
        checkMessage();
        checkImages();
        if (checkMessage() && checkImages()) {
            await submitValidFormAndCloseWindow("bookViolation", "/Controller?command=indicate_book_violation");
        }
    });
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
