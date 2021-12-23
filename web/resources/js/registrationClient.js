window.onload = () => init();


function init() {
    setMaxDateOfBirth();
    document.getElementById("file").addEventListener('change', loadImage);
    document.getElementById("file").addEventListener('submit', checkImage);
    document.getElementById("firstName").addEventListener('input', checkFirstName);
    document.getElementById("lastName").addEventListener('input', checkLastName);
    document.getElementById("patronymic").addEventListener('input', checkPatronymic);
    document.getElementById("email").addEventListener('input', checkEmail);
    document.getElementById("dateOfBirth").addEventListener('input', checkDateOfBirth);
    document.getElementById("passportNumber").addEventListener('input', checkPassportNumber);


    let formSaveClient = document.getElementById('registerClientForm');
    formSaveClient.addEventListener('submit', function (event) {
        event.preventDefault();
        checkFirstName();
        checkLastName();
        checkEmail();
        checkDateOfBirth();
        //checkUniquenessPassportNumber();
        if (checkImage() && checkFirstName() && checkLastName() && checkEmail() && checkDateOfBirth()) {
            submitValidForm();
        }
    })
}

function submitValidForm() {

    let formData = new FormData(document.getElementById('registerClientForm'));
    let xhr = new XMLHttpRequest();

    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + "/Controller?command=add_new_client";
    let urlRedirect = pageContext + "/Controller?command=go_to_main_page";
    xhr.onloadend = function () {
        if (xhr.status == 200) {
            alert("The reader was saved.");
            window.location = urlRedirect;
        } else if (xhr.status == 500) {
            console.log("Error" + this.status);
            alert("Check the correctness of the entered data.");
        } else {
            console.log("Error" + this.status);
            alert("Try later.");
        }

    };

    xhr.open("POST", url, true);
    xhr.send(formData);

}

function checkUniquenessPassportNumber() {
    let passport = document.getElementById("passportNumber").value;
    let pageContext = document.getElementById('pageContext').value;
    let param = 'passportNumber=' + passport;
    let url = pageContext + "/Controller?command=check_uniqueness_passport&" + param;

    let xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);


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

function setMaxDateOfBirth() {

    let dateOfBirth = document.getElementById('dateOfBirth');
    let d = new Date();
    let day = d.getDate();
    let month = d.getMonth() + 1;
    let year = d.getFullYear();
    let today = year + "-" + month + "-" + day;
    dateOfBirth.setAttribute('max', today);
}

function checkImage() {

    let image = document.getElementById("file");
    let error = document.getElementById("fileError");
    error.innerHTML = "";

    if (image.value == "") {
        error.innerHTML = "Upload a photo of the reader!";
        return false;
    }
    return true;
}

function checkFirstName() {
    let firstName = document.getElementById("firstName").value;
    let error = document.querySelector('#firstName + span.error');
    error.textContent = "";

    if (firstName == "") {
        error.textContent = "Required field.";
        return false;
    } else {
        let regex = /([a-zA-Z]{2,20}$)|([а-яА-яёЁ]{2,20}$)/;
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
        error.textContent = "Required field.";
        return false;
    } else {
        let regex = /([a-zA-Z]{2,20}$)|([а-яА-яёЁ]{2,20}$)/;
        if (regex.test(lastName) === false) {
            error.textContent = "Please enter the correct last name.";
            return false;
        }
    }
    return true;
}

function checkPatronymic() {

    let patronymic = document.getElementById("patronymic").value;
    let error = document.querySelector('#patronymic + span.error');
    error.textContent = "";

    let regex = /([a-zA-Z]{2,20}$)|([а-яА-яёЁ]{2,20}$|(^\s*$))/;
    if (regex.test(patronymic) === false) {
        error.textContent = "Please enter the correct patronymic.";
    }
}

function checkEmail() {
    let email = document.getElementById("email").value;
    let error = document.querySelector('#email + span.error');
    error.textContent = "";

    if (email == "") {
        error.textContent = "Required field.";
        return false;
    } else {
        let regex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
        if (regex.test(email) === false) {
            error.textContent = "Please enter the correct email.";
            return false;
        }
    }
    return true;
}

function checkDateOfBirth() {
    let dateOfBirth = document.getElementById("dateOfBirth").value;
    let error = document.querySelector('#dateOfBirth + span.error');
    error.textContent = "";

    if (dateOfBirth == '') {
        error.textContent = "Please enter date of birth.";
        return false;
    }

    let dateArr = dateOfBirth.split('-');

    let year = dateArr[0];
    let month = dateArr[1];
    let day = dateArr[2];

    let today = new Date();
    let todayYear = today.getFullYear();
    let todayMonth = today.getMonth() + 1;
    let todayDay = today.getDate();
    let todayValue = todayDay + "-" + todayMonth + "-" + todayYear;

    let errorMessage = "Incorrect date of birth. Min: 01-01-1900, max: " + todayValue + ".";

    if (year < 1900) {
        error.textContent = errorMessage;
        return false;
    }

    if (year > todayYear) {
        error.textContent = errorMessage;
        return false;
    }

    if (year == todayYear && (month == todayMonth && day > todayDay)) {
        error.textContent = errorMessage;
        return false;
    }

    if (isNaN(parseInt(day)) ||
        isNaN(parseInt(month)) ||
        isNaN(parseInt(year))) {
        error.textContent = errorMessage;
        return false;
    }

    let newDate = new Date(year, month - 1, day);

    if (newDate.getDate() != day ||
        newDate.getMonth() + 1 != month ||
        newDate.getFullYear() != year) {
        error.textContent = errorMessage;
        return false;
    }
    return true;
}

function checkPassportNumber() {
    let passportNumber = document.getElementById("passportNumber").value;
    let error = document.querySelector('#passportNumber + span.error');
    error.textContent = "";

    let regex = /([A-Z]{2}[0-9]{7})|(^\s*$)/;
    if (regex.test(passportNumber) === false) {
        error.textContent = "Please enter the correct passport number.";
    }

}