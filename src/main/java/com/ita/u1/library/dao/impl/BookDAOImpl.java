package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.BookDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.*;
import com.ita.u1.library.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ita.u1.library.util.ConstantParameter.*;

public class BookDAOImpl extends AbstractDAO implements BookDAO {

    public BookDAOImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public void add(Book book) {

        Connection connection = take();
        PreparedStatement psBook = null;
        PreparedStatement psCover = null;
        PreparedStatement psCopies = null;
        PreparedStatement psGenres = null;
        PreparedStatement psAuthors = null;
        ResultSet generatedKeys = null;

        try {
            connection.setAutoCommit(false);
            psBook = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS);
            psCover = connection.prepareStatement(INSERT_BOOK_COVERS);
            psCopies = connection.prepareStatement(INSERT_BOOK_COPIES);
            psGenres = connection.prepareStatement(INSERT_GENRES);
            psAuthors = connection.prepareStatement(INSERT_BOOK_AUTHORS);

            psBook.setString(1, book.getTitle());
            psBook.setString(2, book.getOriginalTitle());
            psBook.setBigDecimal(3, book.getPrice());
            psBook.setInt(4, book.getNumberOfCopies());
            psBook.setInt(5, book.getPublishingYear());
            psBook.setDate(6, Date.valueOf(LocalDate.now()));
            psBook.setInt(7, book.getNumberOfPages());

            int affectedRows = psBook.executeUpdate();

            if (affectedRows == 0) {
                throw new DAOException("Adding new book failed, no rows affected.");
            }

            generatedKeys = psBook.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getInt(1));
            } else {
                throw new DAOException("Adding new book failed, no ID obtained.");
            }

            for (int i = 0; i < book.getCovers().size(); i++) {
                psCover.setInt(1, book.getId());
                psCover.setBytes(2, book.getCovers().get(i));
                psCover.executeUpdate();
            }

            for (int i = 0; i < book.getCopies().length; i++) {
                psCopies.setInt(1, book.getId());
                psCopies.setBigDecimal(2, book.getCopies()[i].getCostPerDay());
                psCopies.executeUpdate();
            }

            for (int i = 0; i < book.getGenres().size(); i++) {
                psGenres.setInt(1, book.getId());
                psGenres.setString(2, book.getGenres().get(i).toString());
                psGenres.executeUpdate();
            }

            for (int i = 0; i < book.getAuthors().size(); i++) {
                psAuthors.setInt(1, book.getAuthors().get(i).getId());
                psAuthors.setInt(2, book.getId());
                psAuthors.executeUpdate();
            }

            connection.commit();

        } catch (SQLException e) {
            rollback(connection);
            throw new DAOException("Adding book to database failed.", e);
        } finally {
            close(generatedKeys);
            close(psBook, psCover, psCopies, psAuthors, psGenres);
            setAutoCommitTrue(connection);
            release(connection);
        }
    }

    @Override
    public List<Book> getAllBooks(int startFromBook, int amountOfBooks) {

        Connection connection = take();
        PreparedStatement psBooks = null;
        PreparedStatement psBooksGenres = null;
        ResultSet rsBooks = null;
        ResultSet rsGenres = null;
        List<Book> books = new ArrayList<>();

        try {
            psBooks = connection.prepareStatement(SELECT_LIMIT_BOOKS);
            psBooksGenres = connection.prepareStatement(SELECT_BOOKS_GENRES);

            psBooks.setInt(1, amountOfBooks);
            psBooks.setInt(2, startFromBook);

            rsBooks = psBooks.executeQuery();

            while (rsBooks.next()) {
                Book book = new Book();
                book.setId(rsBooks.getInt(1));
                book.setTitle(rsBooks.getString(2));
                book.setNumberOfCopies(rsBooks.getInt(COPIES_NUMBER_WITHOUT_WRITTEN_OFF));
                book.setPublishingYear(rsBooks.getInt(6));
                book.setNumberOfAvailableCopies(rsBooks.getInt(AVAILABLE_COPIES));
                psBooksGenres.setInt(1, book.getId());

                rsGenres = psBooksGenres.executeQuery();

                List<Genre> genres = new ArrayList<>();
                while (rsGenres.next()) {
                    String genre = rsGenres.getString(3);
                    String g = genre.toUpperCase();
                    genres.add(Genre.valueOf(g));
                }
                book.setGenres(genres);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DAOException("Method getAllBooks() failed.", e);
        } finally {
            close(rsBooks, rsGenres);
            close(psBooks, psBooksGenres);
            release(connection);
        }
        return books;
    }

    @Override
    public int getNumberOfBooks() {

        int numberOfRecords = 0;
        Connection connection = take();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(COUNT_NUMBER_OF_AVAILABLE_BOOKS, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = ps.executeQuery();
            rs.last();
            numberOfRecords = rs.getRow();

        } catch (SQLException e) {
            throw new DAOException("Method getNumberOfRecords() failed.", e);
        } finally {
            close(rs);
            close(ps);
            release(connection);
        }
        return numberOfRecords;
    }

    @Override
    public List<CopyBook> findBooks(String title) {

        Connection connection = take();
        PreparedStatement psBook = null;
        PreparedStatement psCopyBook = null;
        ResultSet rsBook = null;
        ResultSet rsCopyBook = null;
        List<CopyBook> copyBooks = new ArrayList<>();

        try {
            psBook = connection.prepareStatement(SELECT_BOOK_BY_TITLE);
            psCopyBook = connection.prepareStatement(SELECT_AVAILABLE_COPY_BOOKS_BY_BOOK_ID);
            psBook.setString(1, title);
            rsBook = psBook.executeQuery();

            if (rsBook != null) {
                while (rsBook.next()) {
                    int bookId = rsBook.getInt(1);
                    String booksTitle = rsBook.getString(2);
                    psCopyBook.setInt(1, bookId);
                    rsCopyBook = psCopyBook.executeQuery();

                    while (rsCopyBook.next()) {
                        CopyBook copy = new CopyBook();
                        copy.setId(rsCopyBook.getInt(1));
                        copy.setTitle(booksTitle);
                        copy.setCostPerDay(convertToBigDecimal(rsCopyBook.getString(3)));
                        copyBooks.add(copy);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DAOException("DAOException: method findBooks() failed.", e);
        } finally {
            close(rsBook, rsCopyBook);
            close(psBook, psCopyBook);
            release(connection);
        }
        return copyBooks;
    }

    @Override
    public void changeCostPerDay(CopyBook copyBook) {
        Connection connection = take();
        PreparedStatement psBooksCopies = null;

        try {
            connection.setAutoCommit(false);
            psBooksCopies = connection.prepareStatement(UPDATE_COST_PER_DAY);
            psBooksCopies.setBigDecimal(1, copyBook.getCostPerDay());
            psBooksCopies.setInt(2, copyBook.getId());
            psBooksCopies.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DAOException("Method changeCostPerDay() failed.", e);
        } finally {
            close(psBooksCopies);
            setAutoCommitTrue(connection);
            release(connection);
        }
    }

    @Override
    public List<Book> findTheMostPopularBooks() {
        Connection connection = take();
        PreparedStatement psOrder = null;
        PreparedStatement psBooks = null;
        ResultSet rsOrder = null;
        ResultSet rsBook = null;

        List<Book> books = new ArrayList<>();
        try {
            LocalDate threeMonthAgo = LocalDate.now().minusMonths(3);
            int minOrderIdThreeMonthAgo = 0;
            psOrder = connection.prepareStatement(SELECT_MIN_ORDER_ID_THREE_MONTHS_AGO);
            psOrder.setDate(1, Date.valueOf(threeMonthAgo));
            rsOrder = psOrder.executeQuery();

            while (rsOrder.next()) {
                minOrderIdThreeMonthAgo = rsOrder.getInt(1);
            }

            psBooks = connection.prepareStatement(SELECT_MOST_POPULAR_BOOKS);
            psBooks.setInt(1, minOrderIdThreeMonthAgo);

            rsBook = psBooks.executeQuery();
            while (rsBook.next()) {
                Book book = new Book(rsBook.getInt(1));
                book.setRating(cutOffNumbers(rsBook.getDouble(RATING_BOOK)));
                book.setNumberOfPeopleWhoRead(rsBook.getInt(NUMBER_OF_PEOPLE_WHO_READ));
                books.add(book);
            }

        } catch (SQLException e) {
            throw new DAOException("Method findTheMostPopularBooks() failed.", e);
        } finally {
            close(rsBook, rsOrder);
            close(psBooks, psOrder);
            release(connection);
        }
        return books;
    }

    @Override
    public Book findBookCover(int id) {
        Connection connection = take();
        PreparedStatement psBookCover = null;
        ResultSet rsBookCover = null;
        Book book = new Book();
        try {
            psBookCover = connection.prepareStatement(SELECT_BOOK_COVER);
            psBookCover.setInt(1, id);
            rsBookCover = psBookCover.executeQuery();
            while (rsBookCover.next()) {
                book.setCovers(new ArrayList<>(Collections.singleton(rsBookCover.getBytes(1))));
            }

        } catch (SQLException e) {
            throw new DAOException("Method findBookCover() failed.", e);
        } finally {
            close(rsBookCover);
            close(psBookCover);
            release(connection);
        }
        return book;
    }

    @Override
    public List<CopyBook> findBooksForWritingOff(String title) {
        Connection connection = take();
        PreparedStatement psBook = null;
        PreparedStatement psCopyBook = null;
        PreparedStatement psCopyBookViolations = null;
        ResultSet rsBook = null;
        ResultSet rsCopyBook = null;
        ResultSet rsCopyBookViolations = null;

        List<CopyBook> copyBooks = new ArrayList<>();

        try {
            psBook = connection.prepareStatement(SELECT_BOOK_BY_TITLE);
            psCopyBook = connection.prepareStatement(SELECT_AVAILABLE_COPY_BOOKS_BY_BOOK_ID);
            psCopyBookViolations = connection.prepareStatement(SELECT_COPY_BOOK_VIOLATION);

            psBook.setString(1, title);
            rsBook = psBook.executeQuery();

            if (rsBook != null) {
                while (rsBook.next()) {
                    int bookId = rsBook.getInt(1); //there may be several, different deliveries, year of publication ...
                    String booksTitle = rsBook.getString(2);
                    psCopyBook.setInt(1, bookId);
                    rsCopyBook = psCopyBook.executeQuery();

                    while (rsCopyBook.next()) {
                        CopyBook copy = new CopyBook();
                        copy.setId(rsCopyBook.getInt(1));
                        copy.setTitle(booksTitle);

                        List<Violation> violationsCopyBook = new ArrayList<>();
                        psCopyBookViolations.setInt(1, copy.getId());
                        rsCopyBookViolations = psCopyBookViolations.executeQuery();
                        while (rsCopyBookViolations.next()) {
                            violationsCopyBook.add(new Violation(rsCopyBookViolations.getString(1)));
                        }
                        copy.setCopyBooksViolations(violationsCopyBook);
                        copyBooks.add(copy);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException: method findBooksForWritingOff() failed.", e);
        } finally {
            close(rsBook, rsCopyBook, rsCopyBookViolations);
            close(psBook, psCopyBook, psCopyBookViolations);
            release(connection);
        }
        return copyBooks;
    }

    @Override
    public void writeBooksOff(List<CopyBook> copyBooks) {
        Connection connection = take();
        PreparedStatement psBooksCopies = null;
        LocalDate today = LocalDate.now();

        try {
            connection.setAutoCommit(false);
            psBooksCopies = connection.prepareStatement(UPDATE_EXISTENCE);
            psBooksCopies.setDate(1, Date.valueOf(today));

            for (CopyBook copy : copyBooks) {
                psBooksCopies.setInt(2, copy.getId());
                psBooksCopies.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DAOException("Method writeBooksOff() failed.", e);
        } finally {
            close(psBooksCopies);
            setAutoCommitTrue(connection);
            release(connection);
        }
    }

    @Override
    public boolean doesTheCopyBookExist(CopyBook copyBook) {
        Connection connection = take();
        PreparedStatement psCopyBook = null;
        ResultSet rs = null;

        try {
            psCopyBook = connection.prepareStatement(SELECT_COPY_BOOK_BY_ID);
            psCopyBook.setInt(1, copyBook.getId());
            rs = psCopyBook.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException("Method doesTheOrderExist() failed.", e);
        } finally {
            close(rs);
            close(psCopyBook);
            release(connection);
        }
        return false;
    }

    private double cutOffNumbers(double rating) {
        double scale = Math.pow(10, 1);
        double result = Math.ceil(rating * scale) / scale;
        return result;
    }

    private BigDecimal convertToBigDecimal(String money) {
        String cost = money.replace(',', '.');
        BigDecimal copyCost = new BigDecimal(cost.replace(BR, EMPTY));
        return copyCost;
    }
}

