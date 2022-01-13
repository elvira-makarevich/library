window.onload = () => init();

function init() {
    document.getElementById('findClient').addEventListener('click', checkParamClient);

    let formCloseOrder = document.getElementById('closeOrder');
    formCloseOrder.addEventListener('submit', function (event) {
        event.preventDefault();
        checkClient();
        checkPenalty();
        if (checkClient() && checkPenalty()) {
            submitValidForm();
        }
    })
}

async function submitValidForm() {

    let formData = new FormData(document.getElementById('closeOrder'));
    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + "/Controller?command=close_order";
    let urlRedirect = pageContext + "/Controller?command=go_to_main_page";

    let response = await fetch(url, {
        method: 'POST',
        body: formData
    });

    if (response.ok) {
        alert("The order has been successfully closed.");
        window.location = urlRedirect;
    } else {
        console.log("Error" + this.status);
        alert("Check the correctness of the entered data.");
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

            } else {
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
        createFormWithOrderInfo(json);
    } else {
        alert("Error while finding books.");
        console.log("Response.status: " + response.status);
    }
}

function createFormWithOrderInfo(order) {
    document.getElementById('penalty').addEventListener('input', function () {
        let penalty = document.getElementById("penalty").value;
        let error = document.getElementById("penaltyError");
        error.innerHTML = "";

        let regex = /(^[0-9]{0,}[.,]?[0-9]{0,2})$/;
        if (regex.test(penalty) === false) {
            error.textContent = "Penalty cannot be negative, 2 decimal places are allowed.";
            return false;
        }
        calculateTheTotalRentalCost(order);
        return true;
    });
    viewBooksInfo(order);
    indicateDates(order);
    calculateTheTotalRentalCost(order);
    indicateHiddenField("orderId", order.id);
    indicateHiddenField("clientId", order.clientId);
    indicateHiddenField("preliminaryCost", order.preliminaryCost);
    indicateHiddenField("orderDate", formDate(order.orderDate));
    indicateHiddenField("possibleReturnDate", formDate(order.possibleReturnDate));
}

function formDate(date) {

    let month = date.month;
    let day = date.day;
    if (month < 10)
        month = "0" + month;
    if (day < 10)
        day = "0" + day;
    let newDate = date.year + '-' + month + '-' + day;
    return newDate;
}

function indicateHiddenField(name, value) {
    let form = document.getElementById("closeOrder");
    let inputHidden = document.createElement("input");
    inputHidden.type = "hidden";
    inputHidden.value = value;
    inputHidden.name = name;
    form.appendChild(inputHidden);
}

function calculateTheTotalRentalCost(order) {

    let totalCost = document.getElementById("totalCost");
    let penalty = document.getElementById("penalty").value;
    penalty=penalty.replace(',','.')*1;
    let numberOfRentalDays = calculateTheNumberOfRentalDays(order);
    let numberOfPossibleRentalDays = calculateTheNumberOfPossibleRentalDays(order);

    if (isReturnDateViolated(order) == true) {
        //term of return is violated
        let numberOfOverdueDays = numberOfRentalDays - numberOfPossibleRentalDays;
        let rate = 0.01;
        let amountOfThePenalty = order.preliminaryCost * numberOfOverdueDays * rate;
        let totalCostValue = (order.preliminaryCost + amountOfThePenalty + penalty).toFixed(2);
        totalCost.value = totalCostValue;
    } else {
        let rentalCostPerDay = order.preliminaryCost / numberOfPossibleRentalDays;
        let totalCostValue = (numberOfRentalDays * rentalCostPerDay + penalty).toFixed(2);
        totalCost.value = totalCostValue;
    }
}

function checkPenalty() {

    let penalty = document.getElementById("penalty").value;
    let error = document.getElementById("penaltyError");
    error.innerHTML = "";

    let regex = /(^[0-9]{1,}[.,]?[0-9]{0,2})$/;
    if (regex.test(penalty) === false) {
        error.textContent = "Penalty cannot be negative, 2 decimal places are allowed.";
        return false;
    }
    return true;
}

function calculateTheNumberOfPossibleRentalDays(order) {

    let orderDate = new Date(order.orderDate.year, order.orderDate.month - 1, order.orderDate.day);
    let possibleReturnDate = new Date(order.possibleReturnDate.year, order.possibleReturnDate.month - 1, order.possibleReturnDate.day);
    let daysLag = Math.ceil(Math.abs(possibleReturnDate.getTime() - orderDate.getTime()) / (1000 * 3600 * 24)) + 1;
    return daysLag;
}

function calculateTheNumberOfRentalDays(order) {
    let today = new Date();
    let orderDate = new Date(order.orderDate.year, order.orderDate.month - 1, order.orderDate.day);
    let daysLag = Math.ceil(Math.abs(today.getTime() - orderDate.getTime()) / (1000 * 3600 * 24));
    return daysLag;
}

function isReturnDateViolated(order) {

    let today = new Date();
    let possibleReturnDate = new Date(order.possibleReturnDate.year, order.possibleReturnDate.month - 1, order.possibleReturnDate.day);
    if (possibleReturnDate.getTime() < today.getTime()) {
        return true;
    }
    return false;
}

function indicateDates(order) {

    let inputMaxReturnDate = document.getElementById("inputMaxReturnDate");
    let inputReturnDate = document.getElementById("inputReturnDate");
    let inputOrderDate = document.getElementById("inputOrderDate");
    let today = new Date();
    let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    inputMaxReturnDate.value = order.possibleReturnDate.day + " " + months[(order.possibleReturnDate.month - 1)] + ", " + order.possibleReturnDate.year;
    inputReturnDate.value = today.getDate() + " " + months[(today.getMonth())] + ", " + today.getFullYear();
    inputOrderDate.value = order.orderDate.day + " " + months[(order.orderDate.month - 1)] + ", " + order.orderDate.year;

}

function viewBooksInfo(order) {
    removeTable("table_books");
    let table = document.createElement('table');
    table.className = "table_books";
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
    heading_3.innerHTML = "Rating";

    row_1.appendChild(heading_1);
    row_1.appendChild(heading_2);
    row_1.appendChild(heading_3);
    thead.appendChild(row_1);

    let orderId = order.id;
    let i;
    for (i in order.books) {

        let title = order.books[i].title;
        let costPerDay = order.books[i].costPerDay;
        let copyId = order.books[i].id;

        let row = document.createElement('tr');
        let row_data_1 = document.createElement('td');
        row_data_1.innerHTML = order.books[i].title;
        let row_data_2 = document.createElement('td');
        row_data_2.innerHTML = order.books[i].costPerDay;
        let row_data_3 = document.createElement('td');

        let divElement = document.createElement('div');
        for (let j = 1; j < 6; j++) {
            let inputRadio = document.createElement('input');
            inputRadio.type = "radio";
            inputRadio.name = "rating" + i;
            inputRadio.value = j;
            let label = document.createElement('label');
            label.innerHTML = j;
            divElement.appendChild(inputRadio);
            divElement.appendChild(label);
        }

        row_data_3.appendChild(divElement);

        let row_data_4 = document.createElement('td');

        let buttonAdd = document.createElement('button');
        buttonAdd.innerHTML = "Indicate violation";
        buttonAdd.addEventListener('click', function (event) {
            event.preventDefault();
            let pageContext = document.getElementById('pageContext').value;
            let command = "/Controller?command=go_to_book_violation_page&";
            let params = 'orderId=' + orderId + '&copyId=' + copyId + '&title=' + title;
            let url = pageContext + command + params;
            window.open(url);
        });
        row_data_4.appendChild(buttonAdd);

        let row_data_5 = document.createElement('td');
        let buttonChange = document.createElement('button');
        buttonChange.innerHTML = "Change cost";
        buttonChange.addEventListener('click', function (event) {
            event.preventDefault();
            let pageContext = document.getElementById('pageContext').value;
            let command = "/Controller?command=go_to_copy_book_edit_page&";
            let params = 'copyId=' + copyId + '&title=' + title + '&costPerDay=' + costPerDay;
            let url = pageContext + command + params;
            window.open(url);
        });
        row_data_5.appendChild(buttonChange);

        let row_data_6 = document.createElement('td');
        let inputHidden = document.createElement("input");
        inputHidden.type = "hidden";
        inputHidden.value = copyId;
        inputHidden.name = "copyId";
        row_data_6.appendChild(inputHidden);

        row.appendChild(row_data_1);
        row.appendChild(row_data_2);
        row.appendChild(row_data_3);
        row.appendChild(row_data_4);
        row.appendChild(row_data_5);
        row.appendChild(row_data_6);
        tbody.appendChild(row);
    }
}

