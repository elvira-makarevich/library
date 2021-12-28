window.onload = () => init();

function init() {
    getBooks(${currentPage});

}

function getBooks(page) {

    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + "/Controller?command=view_all_books&currentPage=" + page;
    let response =  fetch(url);

    if (response.ok) {
        let json =  response.json();

        viewBooksInTable(json);
        createNavigation(page, ${numberOfPages});
    } else {
        console.log("Response.status: " + response.status);
    }

}

function removeTable() {

    let table = document.getElementsByTagName("table")[0];
    table.parentNode.removeChild(table);


}


function viewBooksInTable(books) {

    let table = document.createElement('table');
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    table.appendChild(thead);
    table.appendChild(tbody);

    document.getElementById('booksTable').appendChild(table);

    let row_1 = document.createElement('tr');
    let heading_1 = document.createElement('th');
    heading_1.innerHTML = "Title";
    let heading_2 = document.createElement('th');
    heading_2.innerHTML = "Genre(s)";
    let heading_3 = document.createElement('th');
    heading_3.innerHTML = "Publishing year";
    let heading_4 = document.createElement('th');
    heading_4.innerHTML = "Copies number";
    let heading_5 = document.createElement('th');
    heading_5.innerHTML = "Available copies number";
    let heading_6 = document.createElement('th');
    heading_6.innerHTML = "Action";

    row_1.appendChild(heading_1);
    row_1.appendChild(heading_2);
    row_1.appendChild(heading_3);
    row_1.appendChild(heading_4);
    row_1.appendChild(heading_5);
    row_1.appendChild(heading_6);
    thead.appendChild(row_1);

    let i;
    for (i in books) {
        let row = document.createElement('tr');
        let row_data_1 = document.createElement('td');
        row_data_1.innerHTML = books[i].title;
        let row_data_2 = document.createElement('td');
        row_data_2.innerHTML = books[i].genres;
        let row_data_3 = document.createElement('td');
        row_data_3.innerHTML = books[i].publishingYear;
        let row_data_4 = document.createElement('td');
        row_data_4.innerHTML = books[i].numberOfCopies;
        let row_data_5 = document.createElement('td');
        row_data_5.innerHTML = books[i].numberOfAvailableCopies;

        let row_data_6 = document.createElement('td');


        let a = document.createElement('a');
        let linkText = document.createTextNode("view");
        a.appendChild(linkText);
        a.title = "view";

        let pageContext = document.getElementById('pageContext').value;
        let href = pageContext + "/Controller?command=view_book&id=" + books[i].id;


        a.href = href;
        row_data_6.appendChild(a);

        row.appendChild(row_data_1);
        row.appendChild(row_data_2);
        row.appendChild(row_data_3);
        row.appendChild(row_data_4);
        row.appendChild(row_data_5);
        row.appendChild(row_data_6);
        tbody.appendChild(row);
    }

}

function createNavigation(currentPage, numberOfPages) {
    let pageContainer = document.getElementById('pagination');
    let size = numberOfPages;
    let page = currentPage;
    let step = 3;

    let code = '';

    start();

    function start() {
        if (size < step * 2 + 6) {
            add(1, size + 1);
        } else if (page < step * 2 + 1) {
            add(1, step * 2 + 4);
            last();
        } else if (page > size - step * 2) {
            first();
            add(size - step * 2 - 2, size + 1);
        } else {
            first();
            add(page - step, page*1 + step*1 + 1);
            last();

        }
        finish();
    }


    function add(p, q) {
        for (let l = p; l < q; l++) {
            code += '<a>' + l + '</a>';
        }
    }

    function last() {
        code += '<i>...</i><a>' + size + '</a>';
    }

    function first() {
        code += '<a>1</a><i>...</i>';
    }

    function finish() {

        pageContainer.innerHTML = code;
        code = '';
        bind();
    }

    function bind() {
        let a = pageContainer.getElementsByTagName('a');
        for (let num = 0; num < a.length; num++) {
            if (a[num].innerHTML === page)
                a[num].className = 'current';
            a[num].addEventListener('click', clickA, false);
        }
    }

    function clickA() {
        page = this.innerHTML;
        removeTable();
        getBooks(page);
        start();
    }
}