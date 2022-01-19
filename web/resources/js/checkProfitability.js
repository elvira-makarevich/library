window.onload = () => init();

function init() {

    document.getElementById("from").addEventListener('input', checkDateFrom);
    document.getElementById("to").addEventListener('input', checkDateTo);
    document.getElementById('profitability').addEventListener('submit', async function (event) {
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
    let command = "/Controller?command=check_profitability&";
    let url = pageContext + command + params;

    let response = await fetch(url);

    if (response.ok) {
        let json = await response.json();
        viewProfit(json);
    } else {
        if (response.status === 400) {
            alert("Invalid data!");
        }
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
    let minYear = 2020;
    return checkDate(date, error, minYear);
}

function checkDateTo() {
    let date = document.getElementById('to').value;
    let error = document.querySelector('#to + span.error');
    let minYear = 2020;
    return checkDate(date, error, minYear);
}