package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;
import com.ita.u1.library.controller.util.Converter;
import com.ita.u1.library.entity.CopyBook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CloseOrder implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        List<CopyBook> books = Converter.toListCopies(request.getParameterValues("copyId"));
        for(int i=0;i<books.size();i++){
            String rating = "rating"+i;
            double ratingBook=Converter.toNullIfEmptyOrInt(request.getParameter(rating));
            books.get(i).setRating(ratingBook);
            System.out.println(ratingBook);
        }
    }
}
