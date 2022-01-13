window.onload = () => init();

function init() {
    document.getElementById("costPerDay").addEventListener('input', checkCostPerDay);
    document.getElementById("submitButton").addEventListener('click', changeValidation);

    let formEditCopyBook = document.getElementById('editCopyBook');
    formEditCopyBook.addEventListener('submit', function (event) {
        event.preventDefault();
        checkCostPerDay();
        submitValidFormAndCloseWindow();
    });
}

async function submitValidFormAndCloseWindow() {

    let formData = new FormData(document.getElementById('editCopyBook'));
    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + "/Controller?command=change_cost_per_day";

    let response = await fetch(url, {
        method: 'POST',
        body: formData
    });

    if (response.ok) {
        alert("The information was saved.");
        close();
    } else {
        console.log("Error" + this.status);
        alert("Check the correctness of the entered data.");
    }
}

function checkCostPerDay() {
    let costPerDay = document.getElementById("costPerDay");
    let errorPrice = document.querySelector('#costPerDay + span.error');

    if (costPerDay.validity.valid) {
        errorPrice.textContent = '';
        errorPrice.className = 'error';
    } else {
        if (costPerDay.validity.valueMissing) {
            errorPrice.textContent = 'The field cannot be empty.';
        } else if (costPerDay.validity.patternMismatch) {
            errorPrice.textContent = 'The cost cannot be negative (2 decimal places are allowed).';
        }
    }
}

function changeValidation() {
    let formEditCopyBook = document.getElementById('editCopyBook');
    formEditCopyBook.noValidate = true;
}