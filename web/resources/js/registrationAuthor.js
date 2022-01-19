window.onload = () => init();

function init() {
    document.getElementById("file").addEventListener('change', loadImage);
    document.getElementById("file").addEventListener('submit', checkImage);
    document.getElementById("firstName").addEventListener('input', checkFirstName);
    document.getElementById("lastName").addEventListener('input', checkLastName);
    document.getElementById('saveAuthor').addEventListener('submit', async function (event) {
        event.preventDefault();
        checkFirstName();
        checkLastName();
        if (checkImage() && checkFirstName() && checkLastName()) {
            await submitValidFormAndCloseWindow("saveAuthor", "/Controller?command=add_new_author");
        }
    })
}
