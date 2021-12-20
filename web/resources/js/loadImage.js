window.onload = () => loadImage();

function loadImage() {
    document.getElementById('file').addEventListener('change', function () {
        if (this.files && this.files[0]) {
            let reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById('image').setAttribute('src', e.target.result);
            };
            reader.readAsDataURL(this.files[0]);
        }
    })
}