package com.ita.u1.library.util;

public class ConstantParameter {

    public static final String FIRST_NAME = "firstName",
            LAST_NAME = "lastName",
            FILE = "file",
            AUTHOR_ID = "authorId",
            TITLE = "title",
            ORIGINAL_TITLE = "originalTitle",
            GENRES = "genres",
            PRICE = "price",
            COST_PER_DAY = "costPerDay",
            NUMBER_OF_COPIES = "numberOfCopies",
            PUBLISHING_YEAR = "publishingYear",
            NUMBER_OF_PAGES = "numberOfPages",
            COVERS = "covers",
            PATRONYMIC = "patronymic",
            PASSPORT_NUMBER = "passportNumber",
            EMAIL = "email",
            DATE_OF_BIRTH = "dateOfBirth",
            POSTCODE = "postcode",
            COUNTRY = "country",
            LOCALITY = "locality",
            STREET = "street",
            HOUSE_NUMBER = "houseNumber",
            BUILDING = "building",
            APARTMENT_NUMBER = "apartmentNumber",
            CLIENT_ID = "clientId",
            ORDER_ID = "orderId",
            ORDER_DATE = "orderDate",
            POSSIBLE_RETURN_DATE = "possibleReturnDate",
            PRELIMINARY_COST = "preliminaryCost",
            PENALTY = "penalty",
            TOTAL_COST = "totalCost",
            COPY_ID = "copyId",
            CLIENT_ID_IN_SESSION = "clientIdInSession",
            RATING = "rating",
            CURRENT_PAGE = "currentPage",
            VIOLATION_MESSAGE = "violationMessage",
            NEW_COST_PER_DAY = "newCostPerDay",
            IMAGES = "images",
            ID = "id",
            PATH_ADD_AUTHOR_PAGE = "/WEB-INF/jsp/addNewAuthor.jsp",
            PATH_ADD_BOOK_PAGE = "/WEB-INF/jsp/addNewBook.jsp",
            PATH_ADD_CLIENT_PAGE = "/WEB-INF/jsp/addNewClient.jsp",
            PATH_ALL_BOOKS_PAGE = "/WEB-INF/jsp/allBooks.jsp",
            PATH_ALL_CLIENTS_PAGE = "/WEB-INF/jsp/allClients.jsp",
            PATH_AUTHOR_PAGE = "/WEB-INF/jsp/authorInfo.jsp",
            PATH_BOOK_VIOLATION_PAGE = "/WEB-INF/jsp/bookViolation.jsp",
            PATH_CLOSE_ORDER_PAGE = "/WEB-INF/jsp/closeOrder.jsp",
            PATH_MAIN_PAGE = "/WEB-INF/jsp/main.jsp",
            PATH_ORDER_PAGE = "/WEB-INF/jsp/formOrder.jsp";

    public static final String PATTERN_FIRST_NAME_LAST_NAME = "([a-zA-Z]{2,20}$)|([а-яА-яёЁ]{2,20}$)",
            PATTERN_PATRONYMIC = "([a-zA-Z]{2,20}$)|([а-яА-яёЁ]{2,20}$)|(^\\s*$)",
            PATTERN_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$",
            PATTERN_PASSPORT_NUMBER = "([A-Z]{2}[0-9]{7}$)|(^\\s*$)",
            PATTERN_POST_CODE = "^[0-9]{6}$",
            PATTERN_COUNTRY_LOCALITY_STREET = "([a-zA-Z ]{2,40}$)|(^[\\p{L} ]{2,40}$)",
            PATTERN_HOUSE_NUMBER = "^[0-9]{1,3}$",
            PATTERN_BUILDING = "(^[0-9A-Za-z]$)|(^\\s*$)",
            PATTERN_APARTMENT_NUMBER = "^[0-9]{1,4}$|(^\\s*$)",
            PATTERN_TITLE = ".{2,70}$",
            PATTERN_ORIGINAL_TITLE = "(.{2,70}$)|(^\\s*$)",
            PATTERN_COST = "^[0-9]{1,}[.,]?[0-9]{0,2}",
            PATTERN_VIOLATION_MESSAGE = ".{10,500}$";

    public final static String INSERT_AUTHOR = "INSERT INTO authors (first_name, last_name) VALUES (?,?) ",
            INSERT_AUTHOR_IMAGE = "INSERT INTO authors_images (author_id, image) VALUES (?,?)",
            SELECT_AUTHOR_BY_LAST_NAME = "SELECT * FROM authors WHERE last_name = ?",
            SELECT_AUTHOR_IMAGE = "SELECT image FROM authors_images WHERE author_id = ?";

    public final static String INSERT_BOOK = "INSERT INTO books (title, original_title, price, number_of_copies, publishing_year, registration_date, number_of_pages) VALUES (?,?,?,?,?,?,?)",
            INSERT_BOOK_COVERS = "INSERT INTO books_covers (book_id, cover) VALUES (?,?)",
            INSERT_BOOK_COPIES = "INSERT INTO books_copies (book_id, cost_per_day) VALUES (?,?)",
            INSERT_GENRES = "INSERT INTO genres (book_id, genre) VALUES (?,?)",
            INSERT_BOOK_AUTHORS = "INSERT INTO authors_books (author_id, book_id) VALUES (?,?)",
            SELECT_LIMIT_BOOKS = "SELECT books.*, count(availability) as available  FROM books inner join books_copies on books.id=books_copies.book_id where books_copies.availability=true group by books.id order by available DESC, title LIMIT ? OFFSET ? ",
            SELECT_BOOKS_GENRES = "SELECT * FROM genres where book_id=?",
            AVAILABLE = "available",
            RATING_BOOK = "rating_book",
            NUMBER_OF_PEOPLE_WHO_READ = "numberPeopleRead",
            COUNT_NUMBER_OF_AVAILABLE_BOOKS = "SELECT Count(id) FROM books_copies where availability=true group by book_id",
            SELECT_BOOK_BY_TITLE = "SELECT books.*, count(availability) as available  FROM books inner join books_copies on books.id=books_copies.book_id where books_copies.availability=true and books.title = ? group by books.id ",
            SELECT_AVAILABLE_BOOKS = "SELECT * FROM books_copies where book_id=? and availability=true",
            UPDATE_COST_PER_DAY = "UPDATE books_copies SET cost_per_day=? WHERE id=?",
            SELECT_MOST_POPULAR_BOOKS = "SELECT books_copies.book_id, avg(rating) as rating_book, count(copy_id) as numberPeopleRead FROM books_copies inner join books_orders on books_copies.id=books_orders.copy_id  where rating>-1 group by books_copies.book_id order by numberPeopleRead DESC limit 3",
            SELECT_BOOK_COVER = "SELECT cover FROM books_covers WHERE book_id = ? limit 1";

    public final static String INSERT_CLIENT = "INSERT INTO clients (first_name, last_name, patronymic, passport_number, email, birthday, postcode, country, locality, street, house_number, building, apartment_number) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",
            INSERT_CLIENT_IMAGE = "INSERT INTO clients_images (client_id, image) VALUES (?,?)",
            SELECT_CLIENT_BY_PASSPORT_NUMBER = "SELECT * FROM clients WHERE passport_number=?",
            SELECT_CLIENT_BY_EMAIL = "SELECT * FROM clients WHERE email=? ",
            SELECT_ALL_CLIENTS = "SELECT * FROM clients",
            SELECT_LIMIT_CLIENTS = "SELECT * FROM clients order by last_name LIMIT ? OFFSET ?",
            SELECT_CLIENTS_BY_LAST_NAME = "SELECT * FROM clients WHERE last_name = ?";

    public final static String INSERT_ORDER = "INSERT INTO orders (client_id, preliminary_cost, order_date, possible_return_date) VALUES (?,?,?,?)",
            INSERT_BOOKS_ORDER = "INSERT INTO books_orders (order_id, copy_id) VALUES (?,?)",
            UPDATE_BOOKS_COPIES_AVAILABILITY_FALSE = "UPDATE books_copies SET availability = false WHERE id = ?",
            SELECT_ACTIVE_CLIENT_ORDER = "SELECT * FROM orders WHERE client_id=? and status=true ",
            SELECT_BOOKS_ORDER_BY_ID = "SELECT * FROM books_orders WHERE order_id=?",
            SELECT_BOOKS_TITLE = "SELECT title FROM books inner join books_copies on books.id=books_copies.book_id where books_copies.id=?",
            SELECT_BOOK_COPY_COST_PER_DAY = "SELECT cost_per_day FROM books_copies where id=?",
            UPDATE_BOOKS_ORDER_WITH_VIOLATION = "UPDATE books_orders SET violation=? WHERE order_id=? and copy_id=?",
            INSERT_VIOLATION_IMAGES = "INSERT INTO violation_images (order_id, copy_id, image) VALUES (?,?,?)",
            UPDATE_ORDER_CLOSE = "UPDATE orders SET total_cost=?, real_return_date=?, status=false, penalty=? WHERE id=?",
            UPDATE_BOOKS_ORDER_WITH_RATING = "UPDATE books_orders SET rating=? where order_id=? and copy_id=?",
            UPDATE_BOOKS_COPIES_AVAILABILITY_TRUE = "UPDATE books_copies SET availability=true where id=?",
            SELECT_BOOKS_COPIES_ORDER = "SELECT * FROM books_copies where id=?",
            SELECT_COPY_BOOK_FROM_ORDER = "SELECT * FROM books_orders WHERE order_id=? and copy_id=? ";

}
