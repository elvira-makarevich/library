window.onload = () => init();

function init() {
    document.getElementById('findAuthor').addEventListener('click', checkParamAuthor);
    document.getElementById('files').addEventListener('change', loadImages);
    document.getElementById("title").addEventListener('input', checkTitle);
    document.getElementById("price").addEventListener('input', checkPrice);
    document.getElementById("costPerDay").addEventListener('input', checkCostPerDay);
    document.getElementById("numberOfCopies").addEventListener('input', checkNumberOfCopies);
    document.getElementById("files").addEventListener('input', checkCovers);
    document.getElementById("publishingYear").addEventListener('input', checkPublishingYear);
    document.getElementById("numberOfPages").addEventListener('input', checkNumberOfPages);
    let formSaveBook = document.getElementById('saveBook');
    formSaveBook.addEventListener('submit', function (event) {
        event.preventDefault();
        checkAuthors();
        if (checkGenres()) {
            document.createElement('form').submit.call(document.getElementById('saveBook'));
        } else {

        }

    });

    defineDate();
}



function checkAuthors(){
    let authors = document.getElementsByClassName("realAuthorContainer");
    if(authors.length>0){
alert("Добавьте авторов");
    }


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
    let title = document.getElementById("title")
    let errorTitle = document.querySelector('#title + span.error');

    if (title.validity.valid) {
        errorTitle.textContent = '';
        errorTitle.className = 'error';
    } else {
        if (title.validity.valueMissing) {
            errorTitle.textContent = 'Поле не может быть пустым.';
        } else if (title.validity.tooShort) {
            errorTitle.textContent = 'Слишком короткое значение. Минимум 2 символа.';
        } else if (title.validity.tooLong) {
            errorTitle.textContent = 'Слишком длинное значение. Максимум 50 символов.';
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
            errorPrice.textContent = 'Поле не может быть пустым.';
        } else if (price.validity.patternMismatch) {
            errorPrice.textContent = 'Проверьте соответствие введенных данных. Необходимо ввести только цифры, может быть 2 знака после запятой. Число должно быть положительным.';
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
            errorPrice.textContent = 'Поле не может быть пустым.';
        } else if (costPerDay.validity.patternMismatch) {
            errorPrice.textContent = 'Проверьте соответствие введенных данных. Необходимо ввести только цифры, может быть 2 знака после запятой. Число должно быть положительным.';
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
            error.textContent = 'Поле не может быть пустым.';
        } else if (numberOfCopies.validity.rangeUnderflow) {
            error.textContent = 'Количество копий не может быть отрицательным числом.';
        }
    }
}


function checkCovers() {
    let covers = document.getElementById("files");
    let error = document.querySelector('files + span.error');

    if (covers.validity.valid) {
        error.textContent = '';
        error.className = 'error';
    } else {
        if (covers.validity.valueMissing) {
            error.textContent = 'Загрузите одну или несколько обложек для книги.';
        }
    }
}

function checkPublishingYear() {
    let publishingYear = document.getElementById("publishingYear");
    let error = document.querySelector('#publishingYear + span.error');

    if (publishingYear.validity.valid) {
        error.textContent = '';
        error.className = 'error';
    } else {
        if (publishingYear.validity.valueMissing) {
            error.textContent = 'Поле не может быть пустым.';
        } else if (publishingYear.validity.rangeUnderflow) {
            error.textContent = 'Минимальный год издания 868.';
        } else if (publishingYear.validity.rangeOverflow) {
            error.textContent = 'Максимальный год издания 2022.';
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
            error.textContent = 'Поле не может быть пустым.';
        } else if (numberOfPages.validity.rangeUnderflow) {
            error.textContent = 'Количество страниц не может быть отрицательным числом.';
        }
    }
}


function checkGenres() {

    let result = false;
    let error = document.getElementsByClassName("errorGenre")[0];
    let checkbox = document.getElementsByClassName('genre');

    for (let i = 0; i < checkbox.length; i++) {

        if (checkbox[i].checked) {
            return true;
        }

    }
    alert("Выберите хотя бы один жанр!");
    error.textContent = 'Выберите хотя бы один жанр!';
    return result;
}







