package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddNewBook implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      //  String[] authorsId = request.getParameterValues("authorId");
        String[] genres = request.getParameterValues("genres");
        List<byte[]> covers = new ArrayList<>();

        List<Part> fileParts = request.getParts().stream().filter(part -> "covers".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());

        for (Part filePart : fileParts) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            System.out.println(fileName);
            InputStream inputStream = filePart.getInputStream();
            covers.add(IOUtils.toByteArray(inputStream));
            System.out.println(inputStream);
        }
       // for (String author : authorsId) {
      //      System.out.println(author);
      //  }
        for (String genre : genres) {
            System.out.println(genre);
        }

    }
}
