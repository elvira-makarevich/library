<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New order</title>

    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/table.css">

    <style>
        th.sorted[data-order="-1"]::after {
            content: "▼"
        }

        th.sorted[data-order="1"]::after {
            content: "▲"
        }

    </style>

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

            document.querySelectorAll('.table_clients').forEach(tableTH => tableTH.addEventListener('click', () => getSort(event)));
            const getSort = ({target}) => {
                const order = (target.dataset.order = -(target.dataset.order || -1));
                const index = [...target.parentNode.cells].indexOf(target);
                const collator = new Intl.Collator(['en', 'ru'], {numeric: true});
                const comparator = (index, order) => (a, b) => order * collator.compare(
                    a.children[index].innerHTML,
                    b.children[index].innerHTML
                );

                for (const tBody of target.closest('table').tBodies)
                    tBody.append(...[...tBody.rows].sort(comparator(index, order)));

                for (const cell of target.parentNode.cells)
                    cell.classList.toggle('sorted', cell === target);
            };

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
            removeTable();
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
            heading_4.innerHTML = "Action";

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
                    deleteClient();
                    let realClientContainer = document.getElementById("realClientContainer");
                    let input = document.createElement("input");
                    input.type = "text";
                    input.value = initials;
                    input.setAttribute("readonly", "readonly");
                    realClientContainer.appendChild(input);

                    let inputHidden = document.createElement("input");
                    inputHidden.type = "hidden";
                    inputHidden.value = id;
                    inputHidden.name = "clientId";
                    realClientContainer.appendChild(inputHidden);

                    removeTable();
                }
            }

        }

        function removeTable() {
            let table = document.getElementsByTagName("table")[0];
            if (!table) {
                return;
            }
            table.parentNode.removeChild(table);
        }

        function deleteClient() {

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

            //    viewInTableBooks(json);

            } else {
                alert("Error while finding book.");
                console.log("Response.status: " + response.status);
            }
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
    <div class="clientContainer"><label>Client:</label><br><br>
        <div id="realClientContainer">
        </div>
    </div>
    <br>
    <div class="realBooksContainer">
        <div id="realBooksContainer"><label>Books:</label><br><br>
        </div>
    </div>

    <br><br>
    <div><label>Book return date:</label>
        <input id="inputMaxReturnDate" type="text" readonly>
    </div>

    <br><br>
    <div id="preliminaryCost"><label>Preliminary cost:</label><br><br>
    </div>

    <input type="submit" name="submit" id="submitButton" value="Save">
</form>

<input id="pageContextAddClient" type="text" name=""
       value="${pageContext.request.contextPath}/Controller?command=go_to_add_new_client_page" style="display: none;">

<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;"></body>
</html>
