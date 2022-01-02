window.onload = () => init();

function init() {
    let page = document.getElementById('currentPage').value;
    getList(page);

}

async function getList(page) {

    let numberOfPages = document.getElementById('numberOfPages').value;
    let pageContext = document.getElementById('pageContext').value;
    let url = pageContext + page;
    let response = await fetch(url);

    if (response.ok) {
        let json = await response.json();
        viewInTable(json);
        createNavigation(page, numberOfPages);
    } else {
        console.log("Response.status: " + response.status);
    }

}
function sortByThead() {
    document.querySelectorAll('.table_sort thead').forEach(tableTH => tableTH.addEventListener('click', () => getSort(event)));
    const getSort = ({target}) => {
        const order = (target.dataset.order = -(target.dataset.order || -1));
        const index = [...target.parentNode.cells].indexOf(target);
        const collator = new Intl.Collator(['en', 'ru'], {numeric: true});
        const comparator = (index, order) => (a, b) => order * collator.compare(
            a.children[index].innerHTML,
            b.children[index].innerHTML
        );

        for (const tBody of target.closest('table').tBodies)
            tBody.append(...[...tBody.rows].sort(comparator(index, order)));

        for (const cell of target.parentNode.cells)
            cell.classList.toggle('sorted', cell === target);
    };
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
            add(1, size * 1 + 1);
        } else if (page < step * 2 + 1) {
            add(1, step * 2 + 4);
            last();
        } else if (page > size * 1 - step * 2) {
            first();
            add(size * 1 - step * 2 - 2, size * 1 + 1);
        } else {
            first();
            add(page - step, page * 1 + step * 1 + 1);
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
        code += '<i>...</i><a>' + size * 1 + '</a>';
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
            a[num].addEventListener('click', click, false);
        }
    }

    function click() {
        page = this.innerHTML;
        removeTable();
        getList(page);
        start();
    }

}

function removeTable() {
    let table = document.getElementsByTagName("table")[0];
    if (!table){
        return;
    }
    table.parentNode.removeChild(table);
}