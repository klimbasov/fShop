package com.jwd.fShop.controller.command.commands;

import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAuthenticationPageCommand extends AbstractCommand implements Command {
    private static final Role role = Role.UNREGISTERED_USER;

    public ShowAuthenticationPageCommand(ServicePack servicePack) {
        super(servicePack, role);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            super.validateSessionRole((String) (req.getSession().getAttribute("role")));
            req.getRequestDispatcher("WEB-INF/pages/AuthenticationPage.jsp").forward(req, resp);
        } catch (Exception exception) {
            throw new CommandException("in " + this.getClass().getName() + " : in execute() while forwarding request", exception);
        }
    }
}