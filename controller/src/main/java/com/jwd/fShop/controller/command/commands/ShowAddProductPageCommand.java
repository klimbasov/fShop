package com.jwd.fShop.controller.command.commands;

import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAddProductPageCommand extends AbstractCommand implements Command{

    private static final Role role = Role.ADMIN;

    public ShowAddProductPageCommand(ServicePack servicePack) {
        super(servicePack, Role.ADMIN);
    }


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            req.getRequestDispatcher("WEB-INF/pages/AddProductPage.jsp").forward(req,resp);
        }catch (Exception exception){
            throw new CommandException("in " + this.getClass().getName() + " : in execute() while forwarding request", exception);
        }
    }
}
