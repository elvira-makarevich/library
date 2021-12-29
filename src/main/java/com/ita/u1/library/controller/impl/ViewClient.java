package com.ita.u1.library.controller.impl;

import com.ita.u1.library.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewClient implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("hi, I'm here");
    }
}
