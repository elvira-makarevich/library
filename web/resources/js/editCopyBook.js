window.onload = () => init();

function init() {
    document.getElementById("costPerDay").addEventListener('input', checkCostPerDay);
    document.getElementById("submitButton").addEventListener('click', changeValidation);
    document.getElementById('editCopyBook').addEventListener('submit', async function (event) {
        event.preventDefault();
        checkCostPerDay();
        await submitValidFormAndCloseWindow("editCopyBook", "/Controller?command=change_cost_per_day");
    });
}

function changeValidation() {
    let formEditCopyBook = document.getElementById('editCopyBook');
    formEditCopyBook.noValidate = true;
}