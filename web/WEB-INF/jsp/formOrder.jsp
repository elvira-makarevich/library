<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New order</title>

    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/table.css">

    <script>

        window.onload = () => init();

        function init() {
            defineMaxReturnDate();

            let url = document.getElementById("pageContextAddClient").value;
            document.getElementById("addClient").addEventListener('click', () => {
                window.open(url);
            });

            document.getElementById('findClient').addEventListener('click', checkParamClient);
            document.getElementById('findBook').addEventListener('click', checkParamBook);


            let formSaveOrder = document.getElementById('saveOrder');
            formSaveOrder.addEventListener('submit', function (event) {
                event.preventDefault();

                checkClient();
                checkBooks();
                if (checkClient() && checkBooks()) {
                    submitValidForm();
                }
            })

        }

        async function submitValidForm() {

            let formData = new FormData(document.getElementById('saveOrder'));
            let pageContext = document.getElementById('pageContext').value;
            let url = pageContext + "/Controller?command=save_order";
            let urlRedirect = pageContext + "/Controller?command=go_to_main_page";

            let response = await fetch(url, {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                alert("The order has been successfully completed.");
                window.location = urlRedirect;
            } else {
                console.log("Error" + this.status);
                alert("Check the correctness of the entered data.");
            }

        }

        function checkClient() {
            let client = document.getElementById("realClient");
            let error = document.getElementById("clientError");
            error.innerHTML = "";

            if (!client) {
                error.innerHTML = "Add reader!";
                return false;
            }
            return true;
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

        function defineMaxReturnDate() {
            let inputMaxReturnDate = document.getElementById("inputMaxReturnDate");
            let today = new Date();
            today.setMonth(today.getMonth() + 1);
            let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
            let maxReturnDate = today.getDate() + " " + months[(today.getMonth())] + ", " + today.getFullYear();
            inputMaxReturnDate.value = maxReturnDate;

        }

        function checkParamClient() {

            let initials = document.getElementById('initials').value;
            if (initials.length < 2) {
                alert("Enter the last name of the client to search!");
            } else
                findClientRequest();
        }

        async function findClientRequest() {
            let initials = document.getElementById("initials").value;
            let pageContext = document.getElementById('pageContext').value;
            let command = "/Controller?command=find_client&";
            let param = 'lastName=' + initials;
            let url = pageContext + command + param;

            let response = await fetch(url);

            if (response.ok) {
                let json = await response.json();

                viewInTableClients(json);

            } else {
                alert("Error while finding client.");
                console.log("Response.status: " + response.status);
            }
        }


        function viewInTableClients(clients) {
            removeTable("table_clients");
            let table = document.createElement('table');
            table.className = "table_clients";
            let thead = document.createElement('thead');
            let tbody = document.createElement('tbody');


            table.appendChild(thead);
            table.appendChild(tbody);
            document.getElementById('possibleClientContainer').appendChild(table);

            let row_1 = document.createElement('tr');
            let heading_1 = document.createElement('th');
            heading_1.innerHTML = "Last name";
            let heading_2 = document.createElement('th');
            heading_2.innerHTML = "First name";
            let heading_3 = document.createElement('th');
            heading_3.innerHTML = "Date of birth";
            let heading_4 = document.createElement('th');
            heading_4.innerHTML = "";

            row_1.appendChild(heading_1);
            row_1.appendChild(heading_2);
            row_1.appendChild(heading_3);
            row_1.appendChild(heading_4);
            thead.appendChild(row_1);

            let i;
            for (i in clients) {

                let row = document.createElement('tr');
                let row_data_1 = document.createElement('td');
                row_data_1.innerHTML = clients[i].lastName;
                let row_data_2 = document.createElement('td');
                row_data_2.innerHTML = clients[i].firstName;
                let row_data_3 = document.createElement('td');
                row_data_3.innerHTML = clients[i].dateOfBirth;
                let row_data_4 = document.createElement('td');

                let buttonAdd = document.createElement('button');
                buttonAdd.innerHTML = "Add";
                buttonAdd.addEventListener('click', addClient);
                row_data_4.appendChild(buttonAdd);

                let initials = clients[i].lastName + " " + clients[i].firstName;
                let id = clients[i].id;

                row.appendChild(row_data_1);
                row.appendChild(row_data_2);
                row.appendChild(row_data_3);
                row.appendChild(row_data_4);

                tbody.appendChild(row);

                function addClient() {
                    removeClient();
                    let realClientContainer = document.getElementById("realClientContainer");
                    let input = document.createElement("input");
                    input.type = "text";
                    input.id = "realClient";
                    input.value = initials;
                    input.setAttribute("readonly", "readonly");
                    realClientContainer.appendChild(input);

                    let inputHidden = document.createElement("input");
                    inputHidden.type = "hidden";
                    inputHidden.value = id;
                    inputHidden.name = "clientId";
                    realClientContainer.appendChild(inputHidden);

                    removeTable("table_clients");
                    checkClient();
                }
            }

        }

        function removeTable(className) {
            let table = document.getElementsByClassName(className)[0];
            if (!table) {
                return;
            }
            table.parentNode.removeChild(table);
        }

        function removeClient() {

            let div = document.getElementById('realClientContainer');

            while (div.firstChild) {
                div.removeChild(div.firstChild);
            }

        }

        function checkParamBook() {

            let title = document.getElementById('title').value;
            if (title.length < 2) {
                alert("Enter title to search!");
            } else
                findBookRequest();
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

                viewInTableBooks(json);

            } else {
                alert("Error while finding book.");
                console.log("Response.status: " + response.status);
            }
        }

        function viewInTableBooks(books) {
            removeTable("table_books");
            let table = document.createElement('table');
            table.className = "table_books";
            let thead = document.createElement('thead');
            let tbody = document.createElement('tbody');

            table.appendChild(thead);
            table.appendChild(tbody);
            document.getElementById('possibleBookContainer').appendChild(table);

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

            let i;
            for (i in books) {
                for (let j = 0; i < books[i].copies.length; j++) {

                    let row = document.createElement('tr');
                    let row_data_1 = document.createElement('td');
                    row_data_1.innerHTML = books[i].title;
                    let row_data_2 = document.createElement('td');
                    row_data_2.innerHTML = books[i].copies[j].costPerDay;
                    let row_data_3 = document.createElement('td');

                    let buttonAdd = document.createElement('button');
                    buttonAdd.innerHTML = "Add";
                    buttonAdd.addEventListener('click', addBook);
                    row_data_3.appendChild(buttonAdd);

                    let title = books[i].title;
                    let costPerDay = books[i].copies[j].costPerDay;
                    let id = books[i].copies[j].id;

                    row.appendChild(row_data_1);
                    row.appendChild(row_data_2);
                    row.appendChild(row_data_3);

                    tbody.appendChild(row);

                    function addBook() {

                        if (!isTableExists("books_order")) {
                            createTableForBooksOrder();
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

                                let row_data_4 = document.createElement('td');

                                let buttonRemove = document.createElement('button');
                                buttonRemove.innerHTML = "Delete";

                                let attr = document.createAttribute("onclick");
                                attr.value = "deleteRow(this);";
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
            let daysLag = Math.ceil(Math.abs(aMonthLater.getTime() - today.getTime()) / (1000 * 3600 * 24));
            return daysLag;
        }

        function createTableForBooksOrder() {
            let table = document.createElement('table');
            table.className = "books_order";
            let thead = document.createElement('thead');
            let tbody = document.createElement('tbody');

            table.appendChild(thead);
            table.appendChild(tbody);
            document.getElementById('realBooksContainer').appendChild(table);

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

        function deleteRow(r) {
            let i = r.parentNode.parentNode.rowIndex;
            document.getElementsByClassName("books_order")[0].deleteRow(i);
            checkBooks();
            calculateTheOrderAmount();
        }

        function isTableExists(className) {
            let table = document.getElementsByClassName(className)[0];
            if (!table) {
                return false;
            }
            return true;
        }

    </script>
</head>
<body>
<div id="findClientArea" class="findClientArea">

    <input id="initials" type="text" name="" placeholder="Enter last name">
    <button id="findClient" class="" style="inline-size: auto;">Find client</button>
    <button id="addClient" style="inline-size: auto;">Add client</button>
    <br> <br>

    <div id="possibleClientContainer">
    </div>

</div>

<div id="findBookArea" class="findBookArea">

    <input id="title" type="text" name="" placeholder="Enter title">
    <button id="findBook" class="" style="inline-size: auto;">Find book</button>
    <br>

    <div id="possibleBookContainer">
    </div>
    <br>
</div>


<form id="saveOrder" class="saveOrder" action="Controller" method="post">
    <input type="hidden" name="command" value="save_order"/>
    <h1>Order</h1>

    <p id="clientError" class="error"></p>
    <div class="clientContainer"><label>Client:</label><br><br>
        <div id="realClientContainer">
        </div>
    </div>
    <br>
    <p id="booksError" class="error"></p>
    <div class="realBooksContainer">
        <div id="realBooksContainer"><label>Books:</label><br><br>
        </div>
    </div>

    <br><br>
    <div><label>Book return date:</label>
        <input id="inputMaxReturnDate" type="text" readonly>
    </div>

    <br><br>
    <div><label>Preliminary cost, Br:</label>
        <input id="preliminaryCost" type="text" readonly>
    </div>
    <br><br>
    <input type="submit" name="submit" id="submitButton" value="Save">
</form>

<input id="pageContextAddClient" type="text" name=""
       value="${pageContext.request.contextPath}/Controller?command=go_to_add_new_client_page" style="display: none;">

<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;"></body>
</html>
