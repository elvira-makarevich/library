package com.ita.u1.library.dao.impl;

import com.ita.u1.library.dao.AbstractDAO;
import com.ita.u1.library.dao.BookDAO;
import com.ita.u1.library.dao.connection_pool.ConnectionPool;
import com.ita.u1.library.entity.Book;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Genre;
import com.ita.u1.library.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            psBook = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS);
            psCover = connection.prepareStatement(INSERT_BOOK_COVERS);
            psCopies = connection.prepareStatement(INSERT_BOOK_COPIES);
            psGenres = connection.prepareStatement(INSERT_GENRES);
            psAuthors = connection.prepareStatement(INSERT_BOOK_AUTHORS);

            connection.setAutoCommit(false);

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
            if (connection != null)
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Exception during rollback; operation: add book.", ex);
                }
            throw new DAOException("Adding book to database failed.", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new DAOException("Adding book to database failed.", ex);
                }
            }
            close(generatedKeys);
            close(psBook, psCover, psCopies, psAuthors, psGenres);
            release(connection);
        }
    }

    @Override
    public List<Book> getAllBooks(int startFromBook, int amountOfBooks) {

        List<Book> books = new ArrayList<>();
        Connection connection = take();
        PreparedStatement psBooks = null;
        PreparedStatement psBooksGenres = null;
        ResultSet rsBooks = null;
        ResultSet rsGenres = null;

        try {
            psBooks = connection.prepareStatement(SELECT_LIMIT_BOOKS);
            psBooksGenres = connection.prepareStatement(SELECT_BOOKS_GENRES);

            //  PreparedStatement psBooksAvailable = connection.prepareStatement("SELECT * FROM books_copies where book_id=? and availability=true")
            psBooks.setInt(1, amountOfBooks);
            psBooks.setInt(2, startFromBook);

            rsBooks = psBooks.executeQuery();

            while (rsBooks.next()) {
                Book book = new Book();
                book.setId(rsBooks.getInt(1));
                book.setTitle(rsBooks.getString(2));
                book.setNumberOfCopies(rsBooks.getInt(5));
                book.setPublishingYear(rsBooks.getInt(6));
                book.setNumberOfAvailableCopies(rsBooks.getInt(AVAILABLE));
                psBooksGenres.setInt(1, book.getId());
                //     psBooksAvailable.setInt(1, book.getId());

                rsGenres = psBooksGenres.executeQuery();
                //  ResultSet rsAvailableBooks = psBooksAvailable.executeQuery();

                List<Genre> genres = new ArrayList<>();
                while (rsGenres.next()) {
                    String genre = rsGenres.getString(3);
                    String g = genre.toUpperCase();
                    genres.add(Genre.valueOf(g));
                }
                book.setGenres(genres);

                // int numberOfAvailableCopies = 0;
                //   while (rsAvailableBooks.next()) {
                //        numberOfAvailableCopies++;
                //    }
                //     book.setNumberOfAvailableCopies(numberOfAvailableCopies);
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
    public List<Book> findBook(String title) {

        Connection connection = take();
        List<Book> books = new ArrayList<>();
        PreparedStatement psBook = null;
        PreparedStatement psCopyBook = null;
        ResultSet rsBook = null;
        ResultSet rsCopyBook = null;

        try {
            psBook = connection.prepareStatement(SELECT_BOOK_BY_TITLE);
            psCopyBook = connection.prepareStatement(SELECT_AVAILABLE_BOOKS);

            psBook.setString(1, title);
            rsBook = psBook.executeQuery();

            if (rsBook != null) {
                while (rsBook.next()) {
                    Book book = new Book();
                    book.setId(rsBook.getInt(1));
                    book.setTitle(rsBook.getString(2));

                    int numberOfCopies = rsBook.getInt(5);
                    CopyBook[] copies = new CopyBook[numberOfCopies];
                    psCopyBook.setInt(1, book.getId());

                    rsCopyBook = psCopyBook.executeQuery();
                    while (rsCopyBook.next()) {
                        for (int i = 0; i < copies.length; i++) {
                            CopyBook copy = new CopyBook();
                            copy.setId(rsCopyBook.getInt(1));
                            String cost = rsCopyBook.getString(3).replace(',', '.');
                            copy.setCostPerDay(new BigDecimal(cost.replace(" Br", "")));
                            copies[i] = copy;
                        }
                    }
                    book.setCopies(copies);
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            //log
            throw new DAOException("DAOException: method findBook() failed.", e);
        } finally {
            close(rsBook, rsCopyBook);
            close(psBook, psCopyBook);
            release(connection);
        }

        if (books.isEmpty()) {
            return Collections.emptyList();
        }
        return books;
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
            if (connection != null)
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException("Exception during rollback; operation: changeCostPerDay().", ex);
                }
            throw new DAOException("Method changeCostPerDay() failed.", e);
        } finally {
            close(psBooksCopies);
            release(connection);
        }
    }
}

