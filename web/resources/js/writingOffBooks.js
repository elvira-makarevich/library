window.onload = () => init();

function init() {

    defineDate("dateOfWritingOff");
    document.getElementById('findBook').addEventListener('click', checkParamBook);
    document.getElementById('writeOff').addEventListener('submit', async function (event) {
        event.preventDefault();
        if (checkBooks("books_write_off")) {
            let formData = new FormData(document.getElementById('writeOff'));
            let command = "/Controller?command=write_off_books";
            let commandRedirect = "/Controller?command=go_to_main_page";
            await submitValidFormAndRedirect(formData, command, commandRedirect);
        }
    })

}

async function checkParamBook() {
    let title = document.getElementById('title').value;
    if (title.length < 2) {
        alert("Enter title to search!");
    } else
        await findBooksForWringOffRequest();
}

async function findBooksForWringOffRequest() {
    let title = document.getElementById("title").value;
    let pageContext = document.getElementById('pageContext').value;
    let command = "/Controller?command=find_books_for_writing_off&";
    let param = 'title=' + title;
    let url = pageContext + command + param;

    let response = await fetch(url);

    if (response.ok) {
        let json = await response.json();

        if (json == "") {
            alert("No books found.");
        } else {
            viewInTableCopyBooksForWringOff(json);
        }

    } else {
        alert("Error while finding book.");
        console.log("Response.status: " + response.status);
    }
}

function viewInTableCopyBooksForWringOff(booksCopies) {
    removeTable("table_books");
    createTableForBooksWithViolation("table_books", "possibleBookContainer");

    let i;
    for (i in booksCopies) {
        let table_books = document.getElementsByClassName("table_books")[0];
        let row = document.createElement('tr');
        let row_data_1 = document.createElement('td');
        row_data_1.innerHTML = booksCopies[i].id;

        let row_data_2 = document.createElement('td');
        row_data_2.innerHTML = booksCopies[i].title;

        let allViolations = "";
        for (let j = 0; j < booksCopies[i].copyBooksViolations.length; j++) {
            allViolations = allViolations + booksCopies[i].copyBooksViolations[j].message + '<br>';
        }
        let row_data_3 = document.createElement('td');
        row_data_3.innerHTML = allViolations;

        let row_data_4 = document.createElement('td');

        let buttonAdd = document.createElement('button');
        buttonAdd.innerHTML = "Add for writing off";
        buttonAdd.addEventListener('click', addBookToWriteOff);
        let attr = document.createAttribute("onclick");
        attr.value = "deleteRow(this, 'table_books');";
        buttonAdd.setAttributeNode(attr);

        row_data_4.appendChild(buttonAdd);

        let id = booksCopies[i].id;
        let title = booksCopies[i].title;
        let violations = allViolations;

        row.appendChild(row_data_1);
        row.appendChild(row_data_2);
        row.appendChild(row_data_3);
        row.appendChild(row_data_4);
        table_books.appendChild(row);

        function addBookToWriteOff() {

            if (!isTableExists("books_write_off")) {
                createTableForBooksWithViolation("books_write_off", "realBooksContainer");
            }

            if (!isThereDuplicationOfBooks(id)) {
                let books_write_off = document.getElementsByClassName("books_write_off")[0];

                let row = document.createElement('tr');
                let row_data_1 = document.createElement('td');
                row_data_1.innerHTML = id;
                let row_data_2 = document.createElement('td');
                row_data_2.innerHTML = title;
                let row_data_3 = document.createElement('td');
                row_data_3.innerHTML = violations;

                let inputHidden = document.createElement("input");
                inputHidden.type = "hidden";
                inputHidden.value = id;
                inputHidden.name = "copyId";
                row_data_2.appendChild(inputHidden);

                let row_data_4 = document.createElement('td');

                let buttonRemove = document.createElement('button');
                buttonRemove.innerHTML = " - ";
                let attr = document.createAttribute("onclick");
                attr.value = "deleteRow(this, 'books_write_off');";
                buttonRemove.setAttributeNode(attr);

                row_data_4.appendChild(buttonRemove);

                row.appendChild(row_data_1);
                row.appendChild(row_data_2);
                row.appendChild(row_data_3);
                row.appendChild(row_data_4);
                books_write_off.appendChild(row);
            }
            checkBooks('books_write_off');
        }
    }
}

function createTableForBooksWithViolation(tableClassName, booksContainer) {
    let table = document.createElement('table');
    table.className = tableClassName;
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    table.appendChild(thead);
    table.appendChild(tbody);
    document.getElementById(booksContainer).appendChild(table);

    let row_1 = document.createElement('tr');
    let heading_1 = document.createElement('th');
    heading_1.innerHTML = "ID";
    let heading_2 = document.createElement('th');
    heading_2.innerHTML = "Title";
    let heading_3 = document.createElement('th');
    heading_3.innerHTML = "Violations";
    let heading_4 = document.createElement('th');
    heading_4.innerHTML = "Action";

    row_1.appendChild(heading_1);
    row_1.appendChild(heading_2);
    row_1.appendChild(heading_3);
    row_1.appendChild(heading_4);
    thead.appendChild(row_1);
}

0

function isThereDuplicationOfBooks(parameter) {
    let table = document.getElementsByClassName("books_write_off")[0];
    for (let r = 1, n = table.rows.length; r < n; r++) {
        if (parameter == table.rows[r].cells[0].innerHTML) {
            alert("The book has already been added to the list of books!");
            return true;
        }
    }
    return false;
}

function removeTable(className) {
    let table = document.getElementsByClassName(className)[0];
    if (!table) {
        return;
    }
    table.parentNode.removeChild(table);
}

function isTableExists(className) {
    let table = document.getElementsByClassName(className)[0];
    if (!table) {
        return false;
    }
    return true;
}

function deleteRow(r, tableClassName) {
    let i = r.parentNode.parentNode.rowIndex;
    let table = document.getElementsByClassName(tableClassName)[0];
    document.getElementsByClassName(tableClassName)[0].deleteRow(i);
    if (table.rows.length == 1) {
        removeTable(tableClassName);
    }
}

function checkBooks(tableClassName) {

    let error = document.getElementById("booksError");
    error.innerHTML = "";
    if (!isTableExists(tableClassName)) {
        error.innerHTML = "Add book(s)!";
        return false;
    } else if (document.getElementsByClassName(tableClassName)[0].rows.length == 1) {
        error.innerHTML = "Add book(s)!";
        return false;
    }
    return true;
}
