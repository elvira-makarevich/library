window.onload = () => init();

function init() {
    document.getElementById("file").addEventListener('change', loadImage);
    document.getElementById("file").addEventListener('submit', checkImage);
    document.getElementById("firstName").addEventListener('input', checkFirstName);
    document.getElementById("lastName").addEventListener('input', checkLastName);

    let formSaveAuthor = document.getElementById('saveAuthor');
    formSaveAuthor.addEventListener('submit', async function (event) {
        event.preventDefault();
        checkFirstName();
        checkLastName();

        if (checkImage() && checkFirstName() && checkLastName()) {
            await submitValidFormAndCloseWindow();
        }
    })
}

async function submitValidFormAndCloseWindow() {

    let formData = new FormData(document.getElementById('saveAuthor'));
    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + "/Controller?command=add_new_author";

    let response = await fetch(url, {
        method: 'POST',
        body: formData
    });

    if (response.ok) {
        alert("The author was saved.");
        close();
    } else if (response.status === 400) {
        alert("Invalid data.");
        console.log("Response.status: " + response.status);
    } else if (response.status === 500) {
        alert("Database connection error.");
        console.log("Response.status: " + response.status);
    } else {
        console.log("Response.status: " + response.status);
    }
}