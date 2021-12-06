package com.jwd.fShop.controller.command;

import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException;
}
