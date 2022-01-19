window.onload = () => init();

function init() {
    defineMaxReturnDate();

    document.getElementById("addClient").addEventListener('click', addNewClient);
    document.getElementById('findClient').addEventListener('click', checkParamClient);
    document.getElementById('findBook').addEventListener('click', checkParamBook);
    document.getElementById('saveOrder').addEventListener('submit', async function (event) {
        event.preventDefault();
        checkClient();
        checkBooks();
        if (checkClient() && checkBooks()) {
            let formData = new FormData(document.getElementById("saveOrder"));
            let command = "/Controller?command=save_order";
            let commandRedirect = "/Controller?command=go_to_main_page";
            await submitValidFormAndRedirect(formData, command, commandRedirect);
        }
    })
}

function defineMaxReturnDate() {
    let inputMaxReturnDate = document.getElementById("inputMaxReturnDate");
    let today = new Date();
    today.setMonth(today.getMonth() + 1);
    let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    let maxReturnDate = today.getDate() + " " + months[(today.getMonth())] + ", " + today.getFullYear();
    inputMaxReturnDate.value = maxReturnDate;
}

function addNewClient() {
    let pageContext = document.getElementById("pageContext").value;
    let command = "/Controller?command=go_to_add_new_client_page";
    let url = pageContext + command;
    window.open(url);
}

function viewInTableClients(clients) {
    createTableForClients("table_clients", "possibleClientContainer");
    let i;
    for (i in clients) {
        let table_clients = document.getElementsByClassName("table_clients")[0];
        let row = document.createElement('tr');
        let row_data_1 = document.createElement('td');
        row_data_1.innerHTML = clients[i].lastName;
        let row_data_2 = document.createElement('td');
        row_data_2.innerHTML = clients[i].firstName;
        let row_data_3 = document.createElement('td');
        row_data_3.innerHTML = clients[i].email;
        let row_data_4 = document.createElement('td');
        row_data_4.innerHTML = clients[i].dateOfBirth.year + "-" + clients[i].dateOfBirth.month + "-" + clients[i].dateOfBirth.day;
        let row_data_5 = document.createElement('td');

        let buttonAdd = document.createElement('button');
        buttonAdd.innerHTML = "Add to form";
        buttonAdd.addEventListener('click', addClient);
        row_data_5.appendChild(buttonAdd);

        let initials = clients[i].lastName + " " + clients[i].firstName;
        let idClient = clients[i].id;

        row.appendChild(row_data_1);
        row.appendChild(row_data_2);
        row.appendChild(row_data_3);
        row.appendChild(row_data_4);
        row.appendChild(row_data_5);
        table_clients.appendChild(row);

        async function addClient() {
            if (await hasClientActiveOrder(idClient) === true) {
                alert("The reader has not returned the books and cannot take others!");
            } else {
                addClientToRealClientContainer(initials, idClient);
            }
        }
    }
}

async function checkParamBook() {

    let title = document.getElementById('title').value;
    if (title.length < 2) {
        alert("Enter title to search!");
    } else
        await findBookRequest();
}

async function findBookRequest() {
    let title = document.getElementById("title").value;
    let pageContext = document.getElementById('pageContext').value;
    let command = "/Controller?command=find_book&";
    let param = 'title=' + title;
    let url = pageContext + command + param;

    let response = await fetch(url);

    if (response.ok) {
        let json = await response.json();

        if (json == "") {
            alert("No books found.");
        } else {
            viewInTableBooks(json);
        }

    } else {
        if (response.status === 400) {
            alert("Invalid data!");
        }
        console.log("Response.status: " + response.status);
    }
}

function viewInTableBooks(books) {
    createTableForBooks("table_books", "possibleBookContainer");
    let i;
    for (i in books) {
        for (let j = 0; i < books[i].copies.length; j++) {
            let table_books = document.getElementsByClassName("table_books")[0];
            let row = document.createElement('tr');
            let row_data_1 = document.createElement('td');
            row_data_1.innerHTML = books[i].title;
            let row_data_2 = document.createElement('td');
            row_data_2.innerHTML = books[i].copies[j].costPerDay;
            let row_data_3 = document.createElement('td');

            let buttonAdd = document.createElement('button');
            buttonAdd.innerHTML = "Add to order";
            buttonAdd.addEventListener('click', addBook);
            row_data_3.appendChild(buttonAdd);

            let title = books[i].title;
            let costPerDay = books[i].copies[j].costPerDay;
            let id = books[i].copies[j].id;

            row.appendChild(row_data_1);
            row.appendChild(row_data_2);
            row.appendChild(row_data_3);

            table_books.appendChild(row);

            function addBook() {

                if (!isTableExists("books_order")) {
                    createTableForBooks("books_order", "realBooksContainer");
                }

                if (!isItPossibleToAddABookToTheOrder()) {

                    if (!isThereDuplicationOfBooks(title)) {
                        let books_order = document.getElementsByClassName("books_order")[0];

                        let row = document.createElement('tr');
                        let row_data_1 = document.createElement('td');
                        row_data_1.innerHTML = title;
                        let row_data_2 = document.createElement('td');
                        row_data_2.innerHTML = costPerDay;
                        let row_data_3 = document.createElement('td');

                        let inputHidden = document.createElement("input");
                        inputHidden.type = "hidden";
                        inputHidden.value = id;
                        inputHidden.name = "copyId";
                        row_data_3.appendChild(inputHidden);

                        let inputHiddenTitle = document.createElement("input");
                        inputHiddenTitle.type = "hidden";
                        inputHiddenTitle.value = title;
                        inputHiddenTitle.name = "title";

                        row_data_3.appendChild(inputHiddenTitle);
                        let row_data_4 = document.createElement('td');

                        let buttonRemove = document.createElement('button');
                        buttonRemove.innerHTML = "Delete";

                        let attr = document.createAttribute("onclick");
                        attr.value = "deleteRow(this, 'books_order');";
                        buttonRemove.setAttributeNode(attr);

                        row_data_4.appendChild(buttonRemove);

                        row.appendChild(row_data_1);
                        row.appendChild(row_data_2);
                        row.appendChild(row_data_3);
                        row.appendChild(row_data_4);
                        books_order.appendChild(row);
                    }
                }
                removeTable("table_books");
                calculateTheOrderAmount();
                checkBooks();
            }
        }
    }
}

function createTableForBooks(tableClassName, booksContainer) {
    removeTable("table_books");
    let table = document.createElement('table');
    table.className = tableClassName;
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    table.appendChild(thead);
    table.appendChild(tbody);
    document.getElementById(booksContainer).appendChild(table);

    let row_1 = document.createElement('tr');
    let heading_1 = document.createElement('th');
    heading_1.innerHTML = "Title";
    let heading_2 = document.createElement('th');
    heading_2.innerHTML = "Cost per day, Br";
    let heading_3 = document.createElement('th');
    heading_3.innerHTML = "";

    row_1.appendChild(heading_1);
    row_1.appendChild(heading_2);
    row_1.appendChild(heading_3);
    thead.appendChild(row_1);
}

function checkBooks() {

    let error = document.getElementById("booksError");
    error.innerHTML = "";
    if (!isTableExists("books_order")) {
        error.innerHTML = "Add book(s)!";
        return false;
    } else if (document.getElementsByClassName("books_order")[0].rows.length == 1) {
        error.innerHTML = "Add book(s)!";
        return false;
    }
    return true;
}

function calculateTheOrderAmount() {
    let table = document.getElementsByClassName("books_order")[0];
    let daysNumber = calculateTheNumberOfDays();
    let amountOfBooks = table.rows.length - 1;
    let sum = 0;
    for (let r = 1, n = table.rows.length; r < n; r++) {
        let price = table.rows[r].cells[1].innerHTML;
        sum = sum * 1 + price * 1 * daysNumber;
    }

    if (amountOfBooks > 4) {
        sum = sum * 0.85;
    } else if (amountOfBooks > 2) {
        sum = sum * 0.9;
    }
    let preliminaryCost = document.getElementById("preliminaryCost");
    preliminaryCost.value = sum.toFixed(2);
}

function calculateTheNumberOfDays() {
    let today = new Date();
    let aMonthLater = new Date();
    aMonthLater.setMonth(today.getMonth() + 1);
    let daysLag = Math.ceil(Math.abs(aMonthLater.getTime() - today.getTime()) / (1000 * 3600 * 24)) + 1;
    return daysLag;
}

function isThereDuplicationOfBooks(title) {
    let table = document.getElementsByClassName("books_order")[0];
    for (let r = 1, n = table.rows.length; r < n; r++) {
        if (title == table.rows[r].cells[0].innerHTML) {
            alert("The book has already been added to the order!");
            return true;
        }
    }
    return false;
}

function isItPossibleToAddABookToTheOrder() {
    let table = document.getElementsByClassName("books_order")[0];
    if (table.rows.length == 6) {
        alert("The maximum number of books in an order should not exceed 5 copies!");
        return true;
    }
    return false;
}

function deleteRow(r, tableClassName) {
    let i = r.parentNode.parentNode.rowIndex;
    document.getElementsByClassName(tableClassName)[0].deleteRow(i);

    let table = document.getElementsByClassName(tableClassName)[0];
    if (table.rows.length == 1) {
        removeTable(tableClassName);
    }
    checkBooks();
    calculateTheOrderAmount();
}

