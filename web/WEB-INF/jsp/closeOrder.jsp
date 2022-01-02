<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Close order</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css">
    <link rel="stylesheet" type="text/css" href="resources/css/table.css">
    <script>
        window.onload = () => init();

        function init() {
            document.getElementById('findClient').addEventListener('click', checkParamClient);
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

                if (json == "") {
                    alert("No clients found.");
                } else {
                    viewInTableClients(json);
                }

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
                row_data_3.innerHTML = clients[i].dateOfBirth.year + "-" + clients[i].dateOfBirth.month + "-" + clients[i].dateOfBirth.day;
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

                async function addClient() {
                    if (await hasClientActiveOrder(id) === true) {
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
                        findOrderBooks(id);

                    } else{
                        alert("The reader has no orders!");
                    }
                }
            }
        }

        async function findOrderBooks(clientId) {

            let pageContext = document.getElementById('pageContext').value;
            let command = "/Controller?command=find_order_info&";
            let param = 'clientId=' + clientId;
            let url = pageContext + command + param;

            let response = await fetch(url);

            if (response.ok) {
                let json = await response.json();

                if (json == "") {
                    alert("No books found.");
                } else {
                   // viewInTableBooksToCloseOrder(json);
                }

            } else {
                alert("Error while finding books.");
                console.log("Response.status: " + response.status);
            }
        }

        async function hasClientActiveOrder(id) {

            let pageContext = document.getElementById('pageContext').value;
            let param = 'clientId=' + id;
            let url = pageContext + "/Controller?command=check_client_active_order&" + param;

            let response = await fetch(url);

            if (response.ok) {
                let json = await response.json();
                return json;

            } else {
                console.log("Response.status: " + response.status);
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

    </script>
</head>
<body>

<div id="findClientArea" class="findClientArea">

    <input id="initials" type="text" name="" placeholder="Enter last name">
    <button id="findClient" class="" style="inline-size: auto;">Find client</button>

    <br> <br>

    <div id="possibleClientContainer">
    </div>

</div>


<form id="saveOrder" class="saveOrder" action="Controller" method="post">

    <input type="hidden" name="command" value="close_order"/>
    <h1>Close order</h1>

    <p id="clientError" class="error"></p>
    <div class="clientContainer"><label>Client:</label><br><br>
        <div id="realClientContainer">
        </div>
    </div>

    <input type="submit" name="submit" id="submitButton" value="Close order">
</form>


<input id="pageContext" type="text" name=""
       value="${pageContext.request.contextPath}" style="display: none;">
</body>
</html>
