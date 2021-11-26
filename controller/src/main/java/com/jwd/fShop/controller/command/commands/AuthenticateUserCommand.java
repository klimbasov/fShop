package com.jwd.fShop.controller.command.commands;

import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidSessionException;
import com.jwd.fShop.service.domain.User;
import com.jwd.fShop.service.exception.ServiceException;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.Objects;

public class AuthenticateUserCommand extends AbstractCommand implements Command{

    private static final Role role = Role.UNREGISTERED_USER;

    public AuthenticateUserCommand(ServicePack servicePack) {
        super(servicePack, role);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        boolean approved = false;
        HttpSession session = req.getSession();
        User user;
        int role = Role.UNREGISTERED_USER.ordinal();
        String userName = req.getParameter("userName");
        String hashPass = req.getParameter("password");

        try {
            validateSessionRole((String) req.getSession().getAttribute("role"));
        }catch (InvalidSessionException exception) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new CommandException("in " + this.getClass().getName() + " , while validate session", exception);
        }
        try {
            user = servicePack.getUserService().getUser(userName);
            if(Objects.nonNull(user)) {
                if (user.getHashPass().equals(hashPass)) {
                    approved = true;
                    role = user.getRole();
                }
            }
        }catch (ServiceException exception) {
            throw new CommandException("in " + this.getClass().getName() + " , adding user", exception);
        }
        if(approved){
            session.setAttribute("userName", user.getUserName());
            session.setAttribute("role", Role.values()[user.getRole()].name());
        }else{
            JSONObject responseData = new JSONObject();
            responseData.put("error", "wrong!");
            try {
                resp.getWriter().write(responseData.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
