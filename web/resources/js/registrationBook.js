window.onload = () => init();

function init() {

    document.getElementById('findAuthor').addEventListener('click', checkParamAuthor);
    document.getElementById('files').addEventListener('change', loadImages);
    document.getElementById("title").addEventListener('input', checkTitle);
    document.getElementById("price").addEventListener('input', checkPrice);
    document.getElementById("costPerDay").addEventListener('input', checkCostPerDay);
    document.getElementById("numberOfCopies").addEventListener('input', checkNumberOfCopies);
    document.getElementById("files").addEventListener('submit', checkCovers);
    document.getElementById("publishingYear").addEventListener('input', checkPublishingYear);
    document.getElementById("numberOfPages").addEventListener('input', checkNumberOfPages);
    document.getElementById("submitButton").addEventListener('click', changeValidation);

    let formSaveBook = document.getElementById('saveBook');
    formSaveBook.addEventListener('submit', function (event) {
        event.preventDefault();
     
        checkTitle();
        checkPrice();
        checkCostPerDay();
        checkNumberOfCopies();
        checkPublishingYear();
        checkNumberOfPages();
        checkAuthors();
        checkGenres();

        if (checkAuthors() && checkGenres() && checkCovers()) {
            document.createElement('form').submit.call(document.getElementById('saveBook'));
        }

        formSaveBook.noValidate = false;
    });

    defineDate();
}

function changeValidation() {
    let formSaveBook = document.getElementById('saveBook');
    formSaveBook.noValidate = true;
}

function checkParamAuthor() {
    deleteItems();
    let initials = document.getElementById('initials');
    let initialsValue = initials.value;
    if (initialsValue.length < 2) {
        alert("Enter the last name of the author to search!");
    } else
        findAuthorRequest();
}

function findAuthorRequest() {
    let xhr = new XMLHttpRequest();
    let initialsValue = document.getElementById('initials').value;
    let param = 'lastName=' + initialsValue;
    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + "/Controller?command=find_author&" + param;

    xhr.open("GET", url, false);
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 400) {
            alert("Enter the last name of the author to search!");
            return;
        }
        let answer = JSON.parse(this.responseText);
        if (this.readyState == 4 && this.status == 200) {
            if (answer == "") {
                alert("No authors found. Add the author to the database.");
            } else {
                let i;
                let possibleAuthorContainer = document.getElementById("possibleAuthorContainer");
                for (i in answer) {
                    let input = document.createElement("input");
                    input.type = "text";
                    input.value = answer[i].lastName + " " + answer[i].firstName;
                    input.setAttribute("readonly", "readonly");
                    let initials = input.value;
                    possibleAuthorContainer.appendChild(input);

                    let inputHidden = document.createElement("input");
                    inputHidden.type = "hidden";
                    inputHidden.value = answer[i].id;
                    let id = inputHidden.value;
                    possibleAuthorContainer.appendChild(inputHidden);

                    let button = document.createElement("button");
                    button.innerHTML = "Add";
                    button.id = "addAuthor";
                    button.addEventListener('click', addAuthor)
                    possibleAuthorContainer.appendChild(button);

                    function addAuthor() {
                        let realAuthorContainer = document.getElementById("realAuthorContainer");
                        let input = document.createElement("input");
                        input.type = "text";
                        input.value = initials;
                        input.className = "realAuthorContainer";
                        input.setAttribute("readonly", "readonly");
                        realAuthorContainer.appendChild(input);

                        let inputHidden = document.createElement("input");
                        inputHidden.type = "hidden";
                        inputHidden.value = id;
                        inputHidden.name = "authorId";
                        realAuthorContainer.appendChild(inputHidden);
                        deleteItems();
                    }
                }
            }
        } else {
            alert(this.status);
        }
    };
    xhr.send();
}

function defineDate() {
    let inputRegistrationDate = document.getElementById("registrationDate");
    let today = new Date();
    let registrationDate = today.getDate() + "." + (today.getMonth() + 1) + "." + today.getFullYear();
    inputRegistrationDate.value = registrationDate;

}

function deleteItems() {
    let container = document.getElementById('possibleAuthorContainer');
    let deleteInput = container.querySelectorAll('input');
    let deleteButton = container.querySelectorAll('button');
        for (let i = 0; i < deleteInput.length; i++) {
            deleteInput[i].remove();
            deleteButton[i].remove();
        }
}

function loadImages() {

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
}

function checkTitle() {

    let title = document.getElementById("title");
    let errorTitle = document.querySelector('#title + span.error');

    if (title.validity.valid) {
        errorTitle.textContent = '';
        errorTitle.className = 'error';
    } else {
        if (title.validity.valueMissing) {
            errorTitle.textContent = 'The field cannot be empty.';
        } else if (title.validity.tooShort) {
            errorTitle.textContent = 'The title is too short. At least 2 characters.';
        } else if (title.validity.tooLong) {
            errorTitle.textContent = 'The title too long. Maximum 50 characters.';
        }
    }
}

function checkPrice() {
    let price = document.getElementById("price");
    let errorPrice = document.querySelector('#price + span.error');

    if (price.validity.valid) {
        errorPrice.textContent = '';
        errorPrice.className = 'error';
    } else {
        if (price.validity.valueMissing) {
            errorPrice.textContent = 'The field cannot be empty.';
        } else if (price.validity.patternMismatch) {
            errorPrice.textContent = 'Check that the entered data is correct. It is necessary to enter only numbers, there may be 2 decimal places. The number must be positive.';
        }
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
            errorPrice.textContent = 'Check that the entered data is correct. It is necessary to enter only numbers, there may be 2 decimal places. The number must be positive.';
        }
    }
}

function checkNumberOfCopies() {
    let numberOfCopies = document.getElementById("numberOfCopies");
    let error = document.querySelector('#numberOfCopies + span.error');

    if (numberOfCopies.validity.valid) {
        error.textContent = '';
        error.className = 'error';
    } else {
        if (numberOfCopies.validity.valueMissing) {
            error.textContent = 'The field cannot be empty.';
        } else if (numberOfCopies.validity.rangeUnderflow) {
            error.textContent = 'The number of copies cannot be negative.';
        }
    }
}


function checkCovers() {
    let result = true;
    let covers = document.getElementById("files");

    if (covers.value==''){
        alert("Upload one or more book covers!!");
    }
    if (covers.validity.valueMissing) {
        alert("Upload one or more book covers!");
        result = false;
    }
    return result;
}

function checkPublishingYear() {
    let publishingYear = document.getElementById("publishingYear");
    let error = document.querySelector('#publishingYear + span.error');

    if (publishingYear.validity.valid) {
        error.textContent = '';
        error.className = 'error';
    } else {
        if (publishingYear.validity.valueMissing) {
            error.textContent = 'The field cannot be empty.';
        } else if (publishingYear.validity.rangeUnderflow) {
            error.textContent = 'The minimum publication year is 868.';
        } else if (publishingYear.validity.rangeOverflow) {
            error.textContent = 'The maximum publication year is 2022.';
        }
    }
}


function checkNumberOfPages() {
    let numberOfPages = document.getElementById("numberOfPages");
    let error = document.querySelector('#numberOfPages + span.error');

    if (numberOfPages.validity.valid) {
        error.textContent = '';
        error.className = 'error';
    } else {
        if (numberOfPages.validity.valueMissing) {
            error.textContent = 'The field cannot be empty.';
        } else if (numberOfPages.validity.rangeUnderflow) {
            error.textContent = 'The number of pages cannot be negative.';
        } else if (numberOfPages.validity.rangeOverflow) {
            error.textContent = 'The number of pages cannot exceed 2000 pages.';
        }
    }
}


function checkGenres() {

    let result = false;
    let error = document.getElementsByClassName("errorGenre")[0];
    let checkbox = document.getElementsByClassName('genre');
    error.textContent = '';
    for (let i = 0; i < checkbox.length; i++) {

        if (checkbox[i].checked) {
            return true;
        }

    }

    error.textContent = 'Please select at least one genre!';
    return result;
}

function checkAuthors() {

    let authors = document.getElementsByClassName("realAuthorContainer");
    let error = document.getElementsByClassName("errorAuthor")[0];
    error.textContent = "";
    if (authors.length === 0) {
        error.textContent = "Add book authors!";
        return false;
    }
    return true;

}
