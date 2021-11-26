package com.jwd.fShop.controller.command.commands;

import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidSessionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAdministrationPageCommand extends AbstractCommand implements Command{
    public ShowAdministrationPageCommand(ServicePack servicePack) {
        super(servicePack, Role.ADMIN);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            validateSessionRole((String)req.getSession().getAttribute("role"));
            req.getRequestDispatcher("WEB-INF/pages/AdministrationPage.jsp").forward(req, resp);
        } catch (Exception exception) {
            throw new CommandException("In " + this.getClass().getName() + " while exequting command", exception);
        }
    }
}
