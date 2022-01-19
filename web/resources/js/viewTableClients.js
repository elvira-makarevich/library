function viewInTable(clients) {

    let table = document.createElement('table');
    table.className = "table_sort";
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    table.appendChild(thead);
    table.appendChild(tbody);

    document.getElementById('clientsTable').appendChild(table);

    let row_1 = document.createElement('tr');
    let heading_1 = document.createElement('th');
    heading_1.innerHTML = "Last name";
    let heading_2 = document.createElement('th');
    heading_2.innerHTML = "First name";
    let heading_3 = document.createElement('th');
    heading_3.innerHTML = "Date of birth";
    let heading_4 = document.createElement('th');
    heading_4.innerHTML = "Address";
    let heading_5 = document.createElement('th');
    heading_5.innerHTML = "Email";

    row_1.appendChild(heading_1);
    row_1.appendChild(heading_2);
    row_1.appendChild(heading_3);
    row_1.appendChild(heading_4);
    row_1.appendChild(heading_5);
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
        row_data_4.innerHTML = clients[i].address.postcode + ", " + clients[i].address.locality + ", " + clients[i].address.street + ", " + clients[i].address.houseNumber + ", " + clients[i].address.apartmentNumber;
        let row_data_5 = document.createElement('td');
        row_data_5.innerHTML = clients[i].email;

        row.appendChild(row_data_1);
        row.appendChild(row_data_2);
        row.appendChild(row_data_3);
        row.appendChild(row_data_4);
        row.appendChild(row_data_5);
        tbody.appendChild(row);
    }
    sortByThead();
}

function viewAnswerWhenTheListIsEmpty() {
    let div = document.createElement('div');
    div.className = 'emptyList';
    let text = document.createTextNode('There are no readers.');
    div.appendChild(text);
    document.getElementById('clientsTable').appendChild(div);
}

