package com.jwd.fShop.controller.command.commands;

import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowDefaultPageCommand extends AbstractCommand implements Command {

    private static final Role role = Role.UNREGISTERED_USER;

    public ShowDefaultPageCommand(final ServicePack servicePack) {
        super(servicePack, Role.UNREGISTERED_USER);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        try {
            req.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(req,resp);
        }catch (Exception exception){
            throw new CommandException("in " + this.getClass().getName() + " : in execute() while forwarding request", exception);
        }
    }
}
