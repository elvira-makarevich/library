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
