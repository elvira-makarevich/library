function checkFirstName() {
    let firstName = document.getElementById("firstName").value;
    let error = document.querySelector('#firstName + span.error');
    error.textContent = "";

    if (firstName == "") {
        error.textContent = "The field cannot be empty.";
        return false;
    } else {
        let regex = /([a-zA-Z]{2,20}$)|(^[\p{L}]{2,20}$)/u;
        if (regex.test(firstName) === false) {
            error.textContent = "Please enter the correct name.";
            return false;
        }
    }
    return true;
}

function checkLastName() {

    let lastName = document.getElementById("lastName").value;
    let error = document.querySelector('#lastName + span.error');
    error.textContent = "";

    if (lastName == "") {
        error.textContent = "The field cannot be empty.";
        return false;
    } else {
        let regex = /([a-zA-Z]{2,20}$)|(^[\p{L}]{2,20}$)/u;
        if (regex.test(lastName) === false) {
            error.textContent = "Please enter the correct last name.";
            return false;
        }
    }
    return true;
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
    checkImage();
}

function checkImage() {

    let image = document.getElementById("file");
    let error = document.getElementById("fileError");
    error.innerHTML = "";

    if (image.value == "") {
        error.innerHTML = "Upload a photo!";
        return false;
    }
    return true;
}