package com.ita.u1.library.controller.util;

import com.ita.u1.library.entity.Author;
import com.ita.u1.library.entity.Genre;
import com.ita.u1.library.exception.ControllerValidationException;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converter {

    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date toDate(String s) throws ControllerValidationException {
        if (s == null || s.isEmpty()) {
            throw new ControllerValidationException("Date can not be empty.");
        }
        Date date = null;
        try {
            date = dateFormat.parse(s);
        } catch (ParseException | NumberFormatException e) {
            throw new ControllerValidationException("Value [" + s + "] can not be converted to Date.", e);
        }
        return date;
    }

    public static String toNullIfEmpty(String s) {

        if (s == null || s.isEmpty()) {
            return null;
        }
        return s;
    }

    public static int toNullIfEmptyOrInt(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    public static BigDecimal toBigDecimal(String s) throws ControllerValidationException {
        try {
            return new BigDecimal(s.replace(',', '.'));
        } catch (NullPointerException | NumberFormatException e) {
            throw new ControllerValidationException("Provided parameter [" + s + "] can't be converted to big decimal.", e);
        }
    }

    public static int toInt(String s) throws ControllerValidationException {
        try {
            return Integer.parseInt(s);
        } catch (NullPointerException | NumberFormatException e) {
            throw new ControllerValidationException("Provided parameter [" + s + "] can't be converted to int.", e);
        }
    }

    public static List<Author> toListAuthors(String[] values) throws ControllerValidationException {
        if (values == null || values.length == 0) {
            throw new ControllerValidationException("Provided values can not be empty.");
        }
        List<Author> authors = new ArrayList<>();
        for (String s : values) {
            authors.add(new Author(Integer.parseInt(s)));
        }
        return authors;
    }

    public static List<Genre> toListGenres(String[] values) throws ControllerValidationException {
        if (values == null || values.length == 0) {
            throw new ControllerValidationException("Provided values can not be empty.");
        }
        List<Genre> genres = new ArrayList<>();
        for (String s : values) {
            genres.add(Genre.valueOf(s.toUpperCase()));
        }
        return genres;
    }

    public static List<byte[]> toListBytes(List<Part> fileParts) throws ControllerValidationException, IOException {
        if (fileParts == null || fileParts.isEmpty()) {
            throw new ControllerValidationException("Provided values can not be empty.");
        }
        List<byte[]> covers = new ArrayList<>();

        for (Part filePart : fileParts) {
            InputStream inputStream = filePart.getInputStream();
            covers.add(IOUtils.toByteArray(inputStream));
        }
        return covers;
    }

    public static byte[] toBytes(Part part) throws ControllerValidationException, IOException {
        if (part == null) {
            throw new ControllerValidationException("Provided value can not be empty.");
        }
        byte[] bytes = IOUtils.toByteArray(part.getInputStream());
        return bytes;
    }

}