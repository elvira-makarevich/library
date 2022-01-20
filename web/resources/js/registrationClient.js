window.onload = () => init();

function init() {

    document.getElementById("file").addEventListener('change', loadImage);
    document.getElementById("file").addEventListener('submit', checkImage);
    document.getElementById("firstName").addEventListener('input', checkFirstName);
    document.getElementById("lastName").addEventListener('input', checkLastName);
    document.getElementById("patronymic").addEventListener('input', checkPatronymic);
    document.getElementById("email").addEventListener('input', checkEmail);
    document.getElementById("dateOfBirth").addEventListener('input', checkDateOfBirth);
    document.getElementById("passportNumber").addEventListener('input', checkPassportNumber);
    document.getElementById("postcode").addEventListener('input', checkPostcode);
    document.getElementById("country").addEventListener('input', checkCountry);
    document.getElementById("locality").addEventListener('input', checkLocality);
    document.getElementById("street").addEventListener('input', checkStreet);
    document.getElementById("houseNumber").addEventListener('input', checkHouseNumber);
    document.getElementById("building").addEventListener('input', checkBuilding);
    document.getElementById("apartmentNumber").addEventListener('input', checkApartmentNumber);
    document.getElementById('registerClientForm').addEventListener('submit', async function (event) {
        event.preventDefault();
        checkFirstName();
        checkLastName();
        checkEmail();
        checkDateOfBirth();
        checkPostcode();
        checkCountry();
        checkLocality();
        checkStreet();
        checkHouseNumber();

        if (checkImage() && checkFirstName() && checkLastName() && checkEmail() && checkDateOfBirth() && checkPostcode() &&
            checkCountry() && checkLocality() && checkStreet() && checkHouseNumber() && checkPatronymic() && checkBuilding() &&
            checkApartmentNumber()) {
            let formData = new FormData(document.getElementById('registerClientForm'));
            let command = "/Controller?command=add_new_client";
            let commandRedirect = "/Controller?command=go_to_main_page";
            await submitValidFormAndRedirect(formData, command, commandRedirect);
        }
    })
}

function checkPatronymic() {

    let patronymic = document.getElementById("patronymic").value;
    let error = document.querySelector('#patronymic + span.error');
    error.textContent = "";

    if (patronymic == '') {
        return true;
    } else {
        let regex = /([a-zA-Z]{2,20}$)|(^[\p{L}]{2,20}$)/u;
        if (regex.test(patronymic) === false) {
            error.textContent = "Please enter the correct patronymic.";
            return false;
        }
    }
    return true;
}

async function checkEmail() {
    let email = document.getElementById("email").value;
    let error = document.querySelector('#email + span.error');
    error.textContent = "";

    if (email == "") {
        error.textContent = "The field cannot be empty.";
        return false;
    } else {
        let regex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
        if (regex.test(email) === false) {
            error.textContent = "Please enter the correct email.";
            return false;
        }
    }

    if (await checkUniquenessEmail() === true) {
        error.textContent = "A client with such email is already registered.";
        return false;
    }
    return true;
}

async function checkUniquenessEmail() {
    let email = document.getElementById("email").value;
    let pageContext = document.getElementById('pageContext').value;
    let param = 'email=' + email;
    let url = pageContext + "/Controller?command=check_uniqueness_email&" + param;

    let response = await fetch(url);

    if (response.ok) {
        let json = await response.json();
        return json;
    } else {
        if (response.status === 400) {
            alert("Invalid data!");
        }
        console.log("Response.status: " + response.status);
    }

}

async function checkPassportNumber() {
    let passportNumber = document.getElementById("passportNumber").value;
    let error = document.querySelector('#passportNumber + span.error');
    error.textContent = "";

    if (passportNumber == '') {
        return true;
    } else {
        let regex = /([A-Z]{2}[0-9]{7}$)|(^\s*$)/;
        if (regex.test(passportNumber) === false) {
            error.textContent = "Please enter the correct passport number.";
            return false;
        } else if (await checkUniquenessPassportNumber() === true) {
            error.textContent = "This passport number already exists.";
            return false;
        }
    }
    return true;
}

async function checkUniquenessPassportNumber() {
    let passport = document.getElementById("passportNumber").value;
    let pageContext = document.getElementById('pageContext').value;
    let param = 'passportNumber=' + passport;
    let url = pageContext + "/Controller?command=check_uniqueness_passport_number&" + param;

    let response = await fetch(url);

    if (response.ok) {
        let json = await response.json();
        return json;

    } else {
        if (response.status === 400) {
            alert("Invalid data!");
        }
        console.log("Response.status: " + response.status);
    }

}

function checkDateOfBirth() {
    let dateOfBirth = document.getElementById("dateOfBirth").value;
    let error = document.querySelector('#dateOfBirth + span.error');
    let minYear = 1900;
    return checkDate(dateOfBirth, error, minYear);
}

function checkPostcode() {
    let postcode = document.getElementById("postcode").value;
    let error = document.querySelector('#postcode + span.error');
    error.textContent = "";

    if (postcode == "") {
        error.textContent = "The field cannot be empty.";
        return false;
    } else {
        let regex = /^[0-9]{6}$/;
        if (regex.test(postcode) === false) {
            error.textContent = "Enter the correct postcode. 6 numbers.";
            return false;
        }
    }
    return true;
}

function checkCountry() {
    let country = document.getElementById("country").value;
    let error = document.querySelector('#country + span.error');
    error.textContent = "";

    if (country == "") {
        error.textContent = "The field cannot be empty.";
        return false;
    } else {
        let regex = /([a-zA-Z ]{2,40}$)|(^[\p{L} ]{2,40}$)/u;
        if (regex.test(country) === false) {
            error.textContent = "Enter the correct state.";
            return false;
        }
    }
    return true;
}

function checkLocality() {
    let locality = document.getElementById("locality").value;
    let error = document.querySelector('#locality + span.error');
    error.textContent = "";

    if (locality == "") {
        error.textContent = "The field cannot be empty.";
        return false;
    } else {
        let regex = /([a-zA-Z ]{2,40}$)|(^[\p{L} ]{2,40}$)/u;
        if (regex.test(locality) === false) {
            error.textContent = "Enter the correct locality.";
            return false;
        }
    }
    return true;
}

function checkStreet() {
    let street = document.getElementById("street").value;
    let error = document.querySelector('#street + span.error');
    error.textContent = "";

    if (street == "") {
        error.textContent = "The field cannot be empty.";
        return false;
    } else {
        let regex = /([a-zA-Z ]{2,40}$)|(^[\p{L} ]{2,40}$)/u;
        if (regex.test(street) === false) {
            error.textContent = "Enter the correct street.";
            return false;
        }
    }
    return true;
}

function checkHouseNumber() {
    let houseNumber = document.getElementById("houseNumber").value;
    let error = document.querySelector('#houseNumber + span.error');
    error.textContent = "";

    if (houseNumber == "") {
        error.textContent = "The field cannot be empty.";
        return false;
    } else {
        let regex = /^[0-9]{1,3}$/;
        if (regex.test(houseNumber) === false) {
            error.textContent = "Enter the correct house number.";
            return false;
        }
    }
    return true;
}

function checkBuilding() {
    let building = document.getElementById("building").value;
    let error = document.querySelector('#building + span.error');
    error.textContent = "";

    if (building == "") {
        return true;
    } else {
        let regex = /(^[0-9A-Za-z]$)|(^\s*$)/;
        if (regex.test(building) === false) {
            error.textContent = "Enter the correct building.";
            return false;
        }
    }
    return true;
}

function checkApartmentNumber() {
    let apartmentNumber = document.getElementById("apartmentNumber").value;
    let error = document.querySelector('#apartmentNumber + span.error');
    error.textContent = "";

    if (apartmentNumber == "") {
        return true;
    } else {
        let regex = /^[0-9]{1,4}$|(^\s*$)/;
        if (regex.test(apartmentNumber) === false) {
            error.textContent = "Enter the correct apartment number.";
            return false;
        }
    }
    return true;
}