package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.entity.Author;
import com.ita.u1.library.service.AuthorService;
import com.ita.u1.library.service.ServiceProvider;
import com.ita.u1.library.service.exception.ServiceException;
import com.ita.u1.library.service.impl.AuthorServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

public class AddNewAuthor implements Command {

    private final AuthorService authorService = ServiceProvider.getInstance().getAuthorService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        Part filePart = request.getPart("file");
        InputStream inputStream = filePart.getInputStream();
        byte[] bytesImage = IOUtils.toByteArray(inputStream);

        Author author = new Author(firstName, lastName, bytesImage);

        try {
            authorService.addAuthor(author);
            response.sendRedirect("Controller?command=Go_To_Main_Page");
        } catch (ServiceException e) {
            System.out.println("FIASKO, BRATAN");
            e.printStackTrace();
        }
    }
}
