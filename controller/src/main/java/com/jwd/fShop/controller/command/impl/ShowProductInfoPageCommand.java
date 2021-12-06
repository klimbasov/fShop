package com.jwd.fShop.controller.command.impl;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowProductInfoPageCommand extends  AbstractCommand implements Command {

    private static final Role role = Role.UNREGISTERED_USER;

    public ShowProductInfoPageCommand() {
        super(Role.UNREGISTERED_USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            int productId = Integer.parseInt(req.getParameter("product_id"));
            req.setAttribute("product_id", productId);
            req.getRequestDispatcher("WEB-INF/pages/ProductInfoPage.jsp").forward(req,resp);
        }catch (Exception exception){
            throw new CommandException("in " + this.getClass().getName() + " : in execute() while forwarding request", exception);
        }
    }
}
