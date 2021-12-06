package com.jwd.fShop.controller;

import com.jwd.fShop.controller.command.CommandMap;
import com.jwd.fShop.controller.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    private CommandMap commandMap;

    @Override
    public void init() throws ServletException {
        try {
            this.commandMap = new CommandMap();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ServletException(exception.getMessage(), exception);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        System.out.println("[info] Controller:GET" + req.getContextPath());
        System.out.println(req.getPathInfo());
        try {
            String command = req.getParameter("command"); // validate if empty
            commandMap.getCommandByAlias(command).execute(req, resp);
        } catch (CommandException exception) {
            exception.printStackTrace();
            throw new ServletException(exception.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            commandMap.getCommandByAlias(req.getParameter("command")).execute(req, resp);
        } catch (CommandException exception) {
            req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp");
            exception.printStackTrace();
        }
    }

}
