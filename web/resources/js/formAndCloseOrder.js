async function checkParamClient() {

    let initials = document.getElementById('initials').value;
    if (initials.length < 2) {
        alert("Enter the last name of the client to search!");
    } else
        await findClientRequest();
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
        if (response.status === 400) {
            alert("Invalid data!");
        }
        console.log("Response.status: " + response.status);
    }
}

async function hasClientActiveOrder(id) {

    let pageContext = document.getElementById('pageContext').value;
    let command = "/Controller?command=check_client_active_order&";
    let param = 'clientId=' + id;
    let url = pageContext + command + param;

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

function addClientToRealClientContainer(initials, idClient){
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
    inputHidden.value = idClient;
    inputHidden.name = "clientId";
    realClientContainer.appendChild(inputHidden);

    removeTable("table_clients");
    checkClient();
}

function createTableForClients(tableClassName, clientsContainer) {
    removeTable(tableClassName);
    let table = document.createElement('table');
    table.className = tableClassName;
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    table.appendChild(thead);
    table.appendChild(tbody);
    document.getElementById(clientsContainer).appendChild(table);

    let row_1 = document.createElement('tr');
    let heading_1 = document.createElement('th');
    heading_1.innerHTML = "Last name";
    let heading_2 = document.createElement('th');
    heading_2.innerHTML = "First name";
    let heading_3 = document.createElement('th');
    heading_3.innerHTML = "Email";
    let heading_4 = document.createElement('th');
    heading_4.innerHTML = "Date of birth";
    let heading_5 = document.createElement('th');
    heading_5.innerHTML = "";

    row_1.appendChild(heading_1);
    row_1.appendChild(heading_2);
    row_1.appendChild(heading_3);
    row_1.appendChild(heading_4);
    row_1.appendChild(heading_5);
    thead.appendChild(row_1);
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

function removeClient() {

    let div = document.getElementById('realClientContainer');
    while (div.firstChild) {
        div.removeChild(div.firstChild);
    }
}
