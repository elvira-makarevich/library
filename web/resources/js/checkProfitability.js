window.onload = () => init();

function init() {

    document.getElementById("from").addEventListener('input', checkDateFrom);
    document.getElementById("to").addEventListener('input', checkDateTo);

    let formProfitability = document.getElementById('profitability');
    formProfitability.addEventListener('submit', async function (event) {
            event.preventDefault();
            removeProfit();
            checkDateFrom();
            checkDateTo();
            if (checkDateFrom() && checkDateTo() && compareDates()) {
                await getProfitabilityData();
            }
        }
    )
}

function compareDates() {
    let from = document.getElementById("from").value;
    let to = document.getElementById("to").value;
    if (from > to) {
        alert("Please, enter the correct time period.");
        return false;
    }
    return true;
}

async function getProfitabilityData() {

    let from = document.getElementById("from").value;
    let to = document.getElementById("to").value;
    let pageContext = document.getElementById('pageContext').value;
    let params = 'from=' + from + '&to=' + to;
    let url = pageContext + "/Controller?command=check_profitability&" + params;

    let response = await fetch(url);

    if (response.ok) {
        let json = await response.json();
        viewProfit(json);
    } else {
        console.log("Response.status: " + response.status);
    }
}

function viewProfit(profitability) {

    let divInfo = document.getElementById("profitInfo");
    let div = document.createElement('div');
    div.className = "profitability";
    let h3 = document.createElement('h2');
    h3.innerText = "The profitability of the library for the specified period is:";
    div.appendChild(h3);
    let input = document.createElement('input');
    input.type = "text";
    input.value = profitability.profit + " Br";
    input.setAttribute("readonly", "readonly");
    div.appendChild(input);
    divInfo.appendChild(div);
}

function removeProfit() {

    let div = document.getElementById('profitInfo');
    while (div.firstChild) {
        div.removeChild(div.firstChild);
    }
}

function checkDateFrom() {
    let date = document.getElementById('from').value;
    let error = document.querySelector('#from + span.error');
    return checkDate(date, error);
}

function checkDateTo() {
    let date = document.getElementById('to').value;
    let error = document.querySelector('#to + span.error');
    return checkDate(date, error);
}

function checkDate(date, error) {
    error.textContent = "";

    if (date == '') {
        error.textContent = "Please enter date.";
        return false;
    }

    let dateArr = date.split('-');

    let year = dateArr[0];
    let month = dateArr[1];
    let day = dateArr[2];

    let today = new Date();
    let todayYear = today.getFullYear();
    let todayMonth = today.getMonth() + 1;
    let todayDay = today.getDate();
    let todayValue = todayDay + "-" + todayMonth + "-" + todayYear;

    let errorMessage = "Incorrect date. Min: 01-01-2020, max: " + todayValue + ".";

    if (year < 2020) {
        error.textContent = errorMessage;
        return false;
    }

    if (year > todayYear) {
        error.textContent = errorMessage;
        return false;
    }

    if (year == todayYear && (month == todayMonth && day > todayDay)) {
        error.textContent = errorMessage;
        return false;
    }

    if (year == todayYear && (month > todayMonth && day == todayDay)) {
        error.textContent = errorMessage;
        return false;
    }

    if (year == todayYear && (month > todayMonth && day < todayDay)) {
        error.textContent = errorMessage;
        return false;
    }

    if (year == todayYear && (month > todayMonth && day > todayDay)) {
        error.textContent = errorMessage;
        return false;
    }

    if (isNaN(parseInt(day)) ||
        isNaN(parseInt(month)) ||
        isNaN(parseInt(year))) {
        error.textContent = errorMessage;
        return false;
    }

    let newDate = new Date(year, month - 1, day);

    if (newDate.getDate() != day ||
        newDate.getMonth() + 1 != month ||
        newDate.getFullYear() != year) {
        error.textContent = errorMessage;
        return false;
    }
    return true;
}