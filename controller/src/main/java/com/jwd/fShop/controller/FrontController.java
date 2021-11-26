package com.jwd.fShop.controller;

import com.jwd.fShop.controller.command.CommandMap;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.command.commands.AbstractCommand;
import com.jwd.fShop.controller.command.commands.Command;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.dao.ProductDao;
import com.jwd.fShop.dao.ProductDaoImpl;
import com.jwd.fShop.dao.UserDao;
import com.jwd.fShop.dao.UserDaoImpl;
import com.jwd.fShop.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Scanner;

public class FrontController extends HttpServlet {
    private CommandMap commandMap;

    @Override
    public void init() throws ServletException{
        ServicePack servicePack;
        try{
            ProductService productService = ServiceFactory.getInstance().getProductService();
            UserService userService = ServiceFactory.getInstance().getUserService();
            servicePack = new ServicePack(productService, userService);
            this.commandMap = new CommandMap(servicePack);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new ServletException(exception.getMessage(), exception);
        }

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        System.out.println("[info] Controller:GET" + req.getContextPath());
        System.out.println(req.getPathInfo());
        try{
            commandMap.getCommandByAlias(req.getParameter("command")).execute(req, resp);
        }catch (CommandException exception){
            exception.printStackTrace();
            throw new ServletException(exception.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        try{
            commandMap.getCommandByAlias(req.getParameter("command")).execute(req, resp);
        }catch (CommandException exception){
            req.getRequestDispatcher("/WEB-INF/pages/errorPage.jsp");
            exception.printStackTrace();
        }
    }

}
