window.onload = () => init();

function init() {

    defineDate("registrationDate");
    document.getElementById('findAuthor').addEventListener('click', checkParamAuthor);
    document.getElementById("files").addEventListener('change', loadImages);
    document.getElementById("title").addEventListener('input', checkTitle);
    document.getElementById("originalTitle").addEventListener('input', checkOriginalTitle);
    document.getElementById("price").addEventListener('input', checkPrice);
    document.getElementById("costPerDay").addEventListener('input', checkCostPerDay);
    document.getElementById("numberOfCopies").addEventListener('input', checkNumberOfCopies);
    document.getElementById("files").addEventListener('submit', checkImages);
    document.getElementById("publishingYear").addEventListener('input', checkPublishingYear);
    document.getElementById("numberOfPages").addEventListener('input', checkNumberOfPages);
    document.getElementById("submitButton").addEventListener('click', changeValidation);
    document.getElementById("addAuthor").addEventListener('click', addNewAuthor);
    document.getElementById('saveBook').addEventListener('submit', async function (event) {
        event.preventDefault();
        checkTitle();
        checkPrice();
        checkCostPerDay();
        checkNumberOfCopies();
        checkPublishingYear();
        checkNumberOfPages();
        checkAuthors();
        checkGenres();
        if (checkImages() && checkAuthors() && checkGenres()) {
            let formData = new FormData(document.getElementById('saveBook'));
            let command = "/Controller?command=add_new_book";
            let commandRedirect = "/Controller?command=go_to_main_page";
            await submitValidFormAndRedirect(formData, command, commandRedirect);
        }
        document.getElementById('saveBook').noValidate = false;
    });

}

function addNewAuthor() {
    let pageContext = document.getElementById("pageContext").value;
    let command = "/Controller?command=go_to_add_new_author_page";
    let url = pageContext + command;
    window.open(url);
}

function changeValidation() {
    document.getElementById('saveBook').noValidate = true;
}

async function checkParamAuthor() {
    removeTable("table_authors");
    let initials = document.getElementById('initials');
    let initialsValue = initials.value;
    if (initialsValue.length < 2) {
        alert("Enter the last name of the author to search!");
    } else
        await findAuthorRequest();
}

async function findAuthorRequest() {

    let initialsValue = document.getElementById('initials').value;
    let param = 'lastName=' + initialsValue;
    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + "/Controller?command=find_author&" + param;

    let response = await fetch(url);

    if (response.ok) {
        let json = await response.json();

        if (json == "") {
            alert("No authors found.");
        } else {
            viewInTableAuthors(json);
        }

    } else {
        if (response.status === 400) {
            alert("Invalid data.");
        }
        console.log("Response.status: " + response.status);
    }
}

function viewInTableAuthors(authors) {
    removeTable("table_authors");
    createTableForAuthors("table_authors", "possibleAuthorContainer");

    let i;
    for (i in authors) {
        let tableAuthors = document.getElementsByClassName("table_authors")[0];
        let firstName = authors[i].firstName;
        let lastName = authors[i].lastName;
        let id = authors[i].id;
        let row = document.createElement('tr');
        let row_data_1 = document.createElement('td');
        row_data_1.innerHTML = lastName;
        let row_data_2 = document.createElement('td');
        row_data_2.innerHTML = firstName;
        let row_data_4 = document.createElement('td');

        let buttonAdd = document.createElement('button');
        buttonAdd.className="btnAction";
        buttonAdd.innerHTML = "Add";
        buttonAdd.addEventListener('click', addAuthor);
        row_data_4.appendChild(buttonAdd);

        row.appendChild(row_data_1);
        row.appendChild(row_data_2);
        row.appendChild(row_data_4);
        tableAuthors.appendChild(row);

        async function addAuthor() {
            if (!isTableExists("book_authors")) {
                createTableForAuthors("book_authors", "realAuthorContainer");
            }

            let book_authors = document.getElementsByClassName("book_authors")[0];
            let row = document.createElement('tr');
            let row_data_1 = document.createElement('td');
            row_data_1.innerHTML = lastName;
            let row_data_2 = document.createElement('td');
            row_data_2.innerHTML = firstName;
            let row_data_3 = document.createElement('td');

            let inputHidden = document.createElement("input");
            inputHidden.type = "hidden";
            inputHidden.value = id;
            inputHidden.name = "authorId";
            row_data_3.appendChild(inputHidden);

            let row_data_4 = document.createElement('td');
            let buttonRemove = document.createElement('button');
            buttonRemove.className="btnAction";
            buttonRemove.innerHTML = "Delete";

            let attr = document.createAttribute("onclick");
            attr.value = "deleteRow(this, 'book_authors');";
            buttonRemove.setAttributeNode(attr);
            row_data_4.appendChild(buttonRemove);

            row.appendChild(row_data_1);
            row.appendChild(row_data_2);
            row.appendChild(row_data_3);
            row.appendChild(row_data_4);
            book_authors.appendChild(row);

            removeTable("table_authors");
            checkAuthors();
        }
    }
}

function createTableForAuthors(tableClassName, authorContainer) {
    let table = document.createElement('table');
    table.className = tableClassName;
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    table.appendChild(thead);
    table.appendChild(tbody);
    document.getElementById(authorContainer).appendChild(table);

    let row_1 = document.createElement('tr');
    let heading_1 = document.createElement('th');
    heading_1.innerHTML = "Last name";
    let heading_2 = document.createElement('th');
    heading_2.innerHTML = "First name";
    let heading_3 = document.createElement('th');
    heading_3.innerHTML = "";

    row_1.appendChild(heading_1);
    row_1.appendChild(heading_2);
    row_1.appendChild(heading_3);
    thead.appendChild(row_1);
}

function isTableExists(className) {
    let table = document.getElementsByClassName(className)[0];
    if (!table) {
        return false;
    }
    return true;
}

function removeTable(className) {
    let table = document.getElementsByClassName(className)[0];
    if (!table) {
        return;
    }
    table.parentNode.removeChild(table);
}

function deleteRow(r, tableClassName) {
    let i = r.parentNode.parentNode.rowIndex;
    document.getElementsByClassName(tableClassName)[0].deleteRow(i);
    let table = document.getElementsByClassName(tableClassName)[0];
    if (table.rows.length === 1) {
        removeTable(tableClassName);
    }
    checkAuthors();
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
            errorTitle.textContent = 'The title too long. Maximum 70 characters.';
        }
    }
}

function checkOriginalTitle() {

    let originalTitle = document.getElementById("originalTitle");
    let errorTitle = document.querySelector('#originalTitle + span.error');

    if (originalTitle.validity.valid) {
        errorTitle.textContent = '';
        errorTitle.className = 'error';
    } else {
        if (originalTitle.validity.tooShort) {
            errorTitle.textContent = 'The title is too short. At least 2 characters.';
        } else if (originalTitle.validity.tooLong) {
            errorTitle.textContent = 'The title too long. Maximum 70 characters.';
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
            errorPrice.textContent = 'The price cannot be negative (2 decimal places are allowed).';
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
            error.textContent = 'The number must be greater than zero.';
        }
    }
}

function checkImages() {

    let covers = document.getElementById("files");
    let error = document.getElementById("filesError");
    error.innerHTML = "";

    if (covers.validity.valueMissing) {
        error.innerHTML = "Upload one or more book covers!";
        return false;
    }
    return true;
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
            error.textContent = 'The number must be greater than zero.';
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

    let error = document.getElementsByClassName("errorAuthor")[0];
    error.innerHTML = "";
    if (!isTableExists("book_authors")) {
        error.innerHTML = "Add author(s)!";
        return false;
    } else if (document.getElementsByClassName("book_authors")[0].rows.length == 1) {
        error.innerHTML = "Add author(s)!";
        return false;
    }
    return true;
}
