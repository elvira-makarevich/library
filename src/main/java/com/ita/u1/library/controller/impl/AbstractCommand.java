package com.ita.u1.library.controller.impl;

import com.ita.u1.library.exception.ControllerException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractCommand {

    protected static void sendResponseJSON(String json, HttpServletResponse response) throws ControllerException {
        try {
            response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.getWriter().write(json);
        } catch (IOException e) {
            throw new ControllerException(e);
        }
    }

    protected static void sendImage(byte[] image, HttpServletResponse response) throws ControllerException {
        try {
            response.setContentType("image/jpeg");
            response.setContentLength(image.length);
            response.getOutputStream().write(image);
        } catch (IOException e) {
            throw new ControllerException(e);
        }
    }

}
