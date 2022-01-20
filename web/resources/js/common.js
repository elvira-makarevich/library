async function submitValidFormAndCloseWindow(formData, command) {

    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + command;

    let response = await fetch(url, {
        method: 'POST',
        body: formData
    });

    if (response.ok) {
        alert("The information was saved.");
        close();
    } else {
        if (response.status === 400) {
            alert("Invalid data!");
        }
        console.log("Response.status: " + response.status);
    }
}

async function submitValidFormAndRedirect(formData, command, commandRedirect) {

    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + command;
    let urlRedirect = pageContext + commandRedirect;

    let response = await fetch(url, {
        method: 'POST',
        body: formData
    });

    if (response.ok) {
        alert("Information successfully saved.");
        window.location = urlRedirect;
    } else {
        if (response.status === 400) {
            alert("Invalid data!");
        }
        console.log("Response.status: " + response.status);
    }
}


function loadImages() {
    deleteImages();
    let containerImages = document.getElementById("fileListDisplay");
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

function checkCostPerDay() {
    let costPerDay = document.getElementById("costPerDay");
    let errorPrice = document.querySelector('#costPerDay + span.error');

    if (costPerDay.validity.valid) {
        errorPrice.textContent = '';
        errorPrice.className = 'error';
    } else {
        if (costPerDay.validity.valueMissing) {
            errorPrice.textContent = 'The field cannot be empty.';
        } else if (costPerDay.validity.patternMismatch) {
            errorPrice.textContent = 'The cost cannot be negative (2 decimal places are allowed).';
        }
    }
}

function checkDate(date, error, minYear) {
    error.textContent = "";

    if (date == '') {
        error.textContent = "Please enter date.";
        return false;
    }

    let dateArr = date.split('-');

    let year = dateArr[0];
    let month = dateArr[1];
    let day = dateArr[2];

    let today = new Date();
    let todayYear = today.getFullYear();
    let todayMonth = today.getMonth() + 1;
    let todayDay = today.getDate();
    let todayValue = todayDay + "-" + todayMonth + "-" + todayYear;


    let errorMessage = "Incorrect date. Min: 01-01-" + minYear + ", max: " + todayValue + ".";

    if (year < minYear) {
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

    if (year == todayYear && (month > todayMonth && day == todayDay)) {
        error.textContent = errorMessage;
        return false;
    }

    if (year == todayYear && (month > todayMonth && day < todayDay)) {
        error.textContent = errorMessage;
        return false;
    }

    if (year == todayYear && (month > todayMonth && day > todayDay)) {
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

function defineDate(elementId) {
    let date = document.getElementById(elementId);
    let today = new Date();
    let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    let todayDate = today.getDate() + " " + months[(today.getMonth())] + ", " + today.getFullYear();
    date.value = todayDate;
}