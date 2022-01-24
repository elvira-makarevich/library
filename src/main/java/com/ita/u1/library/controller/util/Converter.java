package com.ita.u1.library.controller.util;

import com.ita.u1.library.entity.Author;
import com.ita.u1.library.entity.CopyBook;
import com.ita.u1.library.entity.Genre;
import com.ita.u1.library.exception.ControllerValidationException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converter {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Logger log = LogManager.getLogger(Converter.class);

    public static LocalDate toDate(String s) throws ControllerValidationException {
        if (s == null || s.isEmpty()) {
            log.error("Date can not be empty.");
            throw new ControllerValidationException("Date can not be empty.");
        }
        LocalDate date = null;
        try {
            date = LocalDate.parse(s, formatter);
        } catch (NumberFormatException e) {
            log.error("Value [" + s + "] can not be converted to Date.", e);
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
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            log.error("Value [" + s + "] can't be converted to int.", e);
            throw new ControllerValidationException("Value [" + s + "] can't be converted to int.", e);
        }
    }

    public static BigDecimal toBigDecimal(String s) throws ControllerValidationException {
        try {
            return new BigDecimal(s.replace(',', '.'));
        } catch (NullPointerException | NumberFormatException e) {
            log.error("Value [" + s + "] can't be converted to big decimal.", e);
            throw new ControllerValidationException("Value [" + s + "] can't be converted to big decimal.", e);
        }
    }

    public static BigDecimal toBigDecimalOrNull(String s) throws ControllerValidationException {
        if (s == null || s.isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(s.replace(',', '.'));
        } catch (NullPointerException | NumberFormatException e) {
            log.error("Provided parameter [" + s + "] can't be converted to big decimal.", e);
            throw new ControllerValidationException("Provided parameter [" + s + "] can't be converted to big decimal.", e);
        }
    }

    public static int toInt(String s) throws ControllerValidationException {
        try {
            return Integer.parseInt(s);
        } catch (NullPointerException | NumberFormatException e) {
            log.error("Value [" + s + "] can't be converted to int.", e);
            throw new ControllerValidationException("Value [" + s + "] can't be converted to int.", e);
        }
    }

    public static List<Author> toListAuthors(String[] values) throws ControllerValidationException {
        if (values == null || values.length == 0) {
            log.error("Author(s) parameter can not be empty.");
            throw new ControllerValidationException("Author(s) parameter can not be empty.");
        }
        List<Author> authors = new ArrayList<>();

        try {
            for (String s : values) {
                authors.add(new Author(Integer.parseInt(s)));
            }
        } catch (NullPointerException | NumberFormatException e) {
            log.error("Values [" + Arrays.toString(values) + "] can't be converted to int.", e);
            throw new ControllerValidationException("Values [" + Arrays.toString(values) + "] can't be converted to int.", e);
        }
        return authors;
    }

    public static List<CopyBook> toListCopies(String[] values) throws ControllerValidationException {
        if (values == null || values.length == 0) {
            log.error("Provided parameters can not be empty.");
            throw new ControllerValidationException("Provided parameters can not be empty.");
        }
        List<CopyBook> copies = new ArrayList<>();
        try {
            for (String s : values) {
                copies.add(new CopyBook(Integer.parseInt(s)));
            }
        } catch (NullPointerException | NumberFormatException e) {
            log.error("Values [" + Arrays.toString(values) + "] can't be converted to int.", e);
            throw new ControllerValidationException("Values [" + Arrays.toString(values) + "] can't be converted to int.", e);
        }
        return copies;
    }

    public static List<CopyBook> toListCopiesWithTitle(String[] valuesId, String[] valuesTitle) throws ControllerValidationException {
        if (valuesId == null || valuesId.length == 0 || valuesTitle == null || valuesTitle.length == 0) {
            log.error("Provided parameters can not be empty.");
            throw new ControllerValidationException("Provided parameters can not be empty.");
        }
        List<CopyBook> copies = new ArrayList<>();
        try {

            for (int i = 0; i < valuesId.length; i++) {
                copies.add(new CopyBook(Integer.parseInt(valuesId[i]), valuesTitle[i]));
            }
        } catch (NullPointerException | NumberFormatException e) {
            log.error("Provided parameters can't be converted to int.", e);
            throw new ControllerValidationException("Provided parameters can't be converted to int.", e);
        }
        return copies;
    }

    public static List<Genre> toListGenres(String[] values) throws ControllerValidationException {
        if (values == null || values.length == 0) {
            log.error("Provided parameters can not be empty.");
            throw new ControllerValidationException("Provided parameters can not be empty.");
        }
        List<Genre> genres = new ArrayList<>();
        for (String s : values) {
            genres.add(Genre.valueOf(s.toUpperCase()));
        }
        return genres;
    }

    public static List<byte[]> toListBytes(List<Part> fileParts) throws ControllerValidationException {
        if (fileParts == null || fileParts.isEmpty()) {
            log.error("Provided parameters can not be empty.");
            throw new ControllerValidationException("Provided parameters can not be empty.");
        }
        List<byte[]> images = new ArrayList<>();
        try {
            for (Part filePart : fileParts) {
                InputStream inputStream = filePart.getInputStream();
                images.add(IOUtils.toByteArray(inputStream));
            }
        } catch (IOException e) {
            log.error("Values [" + fileParts + "] can't be converted to ByteArray.", e);
            throw new ControllerValidationException("Values [" + fileParts + "] can't be converted to ByteArray.", e);
        }
        return images;
    }

    public static byte[] toBytes(Part part) throws ControllerValidationException {
        if (part == null) {
            log.error("Provided parameter can not be empty.");
            throw new ControllerValidationException("Provided parameter can not be empty.");
        }
        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray(part.getInputStream());
        } catch (IOException e) {
            log.error("Value [" + part + "] can't be converted to ByteArray.", e);
            throw new ControllerValidationException("Values [" + part + "] can't be converted to ByteArray.", e);
        }
        return bytes;
    }

}
