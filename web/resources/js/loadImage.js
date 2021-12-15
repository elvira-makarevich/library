window.onload = () => init();

function init() {
    document.getElementById('file1').addEventListener('change', function () {

        if (this.files && this.files[0]) {
            let reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById('image1').setAttribute('src', e.target.result);
            };
            reader.readAsDataURL(this.files[0]);

        }
    })
}

