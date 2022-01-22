<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="resources/css/mostPopularTable.css">

</head>
<body>

<input id="pageContextMostPopular" type="text" name="" value="${pageContext.request.contextPath}"
       style="display: none;">
<script>
    findMostPopularBooks();

    async function findMostPopularBooks() {

        let pageContext = document.getElementById('pageContextMostPopular').value;
        let command = "/Controller?command=find_the_most_popular_books";
        let url = pageContext + command;

        let response = await fetch(url);

        if (response.ok) {
            let json = await response.json();
            if (json == "") {
                viewAnswerWhenTheListIsEmpty();
            } else {
               await viewInTableMostPopular(json);
            }

        } else {
            alert("Error while finding books.");
            console.log("Response.status: " + response.status);
        }
    }

    function viewAnswerWhenTheListIsEmpty() {
        let div = document.createElement('div');
        div.className = 'emptyMostPopularBook';
        let text = document.createTextNode('There are no orders last 3 months.');
        div.appendChild(text);
        document.getElementById('mostPopularBooks').appendChild(div);
    }

    async function viewInTableMostPopular(books) {

        let rate = "Rating: ";
        let readers = "Readers number: ";
        let table = document.createElement('table');
        let thead = document.createElement('thead');
        let tbody = document.createElement('tbody');
        table.appendChild(thead);
        table.appendChild(tbody);

        let row = document.createElement('tr');
        let row_data_1 = document.createElement('td');
        let img = document.createElement('img');
        img.src = "${pageContext.request.contextPath}/Controller?command=find_book_cover&id=" + books[0].id;
        img.style.height = '230px';
        img.style.width = '180px';
        row_data_1.appendChild(img);
        await new Promise(r => setTimeout(r, 70));
        let row_data_2 = document.createElement('td');
        let img2 = document.createElement('img');
        img2.src = "${pageContext.request.contextPath}/Controller?command=find_book_cover&id=" + books[1].id;
        img2.style.height = '230px';
        img2.style.width = '180px';
        row_data_2.appendChild(img2);
        await new Promise(r => setTimeout(r, 100));
        let row_data_3 = document.createElement('td');
        let img3 = document.createElement('img');
        img3.src = "${pageContext.request.contextPath}/Controller?command=find_book_cover&id=" + books[2].id;
        img3.style.height = '230px';
        img3.style.width = '180px';
        row_data_3.appendChild(img3);

        row.appendChild(row_data_1);
        row.appendChild(row_data_2);
        row.appendChild(row_data_3);

        tbody.appendChild(row);

        let row2 = document.createElement('tr');
        let row2_data_1 = document.createElement('td');
        row2_data_1.innerHTML = readers + books[0].numberOfPeopleWhoRead;
        let row2_data_2 = document.createElement('td');
        row2_data_2.innerHTML = readers + books[1].numberOfPeopleWhoRead;
        let row2_data_3 = document.createElement('td');
        row2_data_3.innerHTML = readers + books[2].numberOfPeopleWhoRead;

        row2.appendChild(row2_data_1);
        row2.appendChild(row2_data_2);
        row2.appendChild(row2_data_3);

        tbody.appendChild(row2);

        let row3 = document.createElement('tr');
        let row3_data_1 = document.createElement('td');
        row3_data_1.innerHTML = rate + books[0].rating;
        let row3_data_2 = document.createElement('td');
        row3_data_2.innerHTML = rate + books[1].rating;
        let row3_data_3 = document.createElement('td');
        row3_data_3.innerHTML = rate + books[2].rating;

        row3.appendChild(row3_data_1);
        row3.appendChild(row3_data_2);
        row3.appendChild(row3_data_3);

        tbody.appendChild(row3);

        document.getElementById('mostPopularBooks').appendChild(table);
    }
</script>

<h2 style="padding: 0 0 0 15px;">Most popular books</h2>
<div id="mostPopularBooks">
</div>

</body>
</html>
