window.onload = () => init();

function init() {
    document.getElementById("costPerDay").addEventListener('input', checkCostPerDay);
    document.getElementById("submitButton").addEventListener('click', changeValidation);
    document.getElementById('editCopyBook').addEventListener('submit', async function (event) {
        event.preventDefault();
        checkCostPerDay();
        let formData = new FormData(document.getElementById("editCopyBook"));
        let command = "/Controller?command=change_cost_per_day";
        await submitValidFormAndCloseWindow(formData, command);
    });
}

function changeValidation() {
    document.getElementById('editCopyBook').noValidate = true;
}