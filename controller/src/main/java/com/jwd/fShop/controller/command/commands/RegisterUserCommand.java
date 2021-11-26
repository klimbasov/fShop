package com.jwd.fShop.controller.command.commands;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.domain.ServicePack;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidSessionException;
import com.jwd.fShop.service.domain.User;
import com.jwd.fShop.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Scanner;

public class RegisterUserCommand extends AbstractCommand implements Command{

    private static final Role role = Role.UNREGISTERED_USER;

    public RegisterUserCommand(ServicePack servicePack) {
        super(servicePack, role);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String userName = req.getParameter("userName");
        String hashPass = req.getParameter("password");
        HttpSession session = req.getSession();
        boolean approved = false;
        Date registrationDate = new Date(Date.from(Instant.now()).getTime());
        Time registrationTime = new Time(Time.from(Instant.now()).getTime());
        Role role = Role.REGISTERED_USER;

        try {
            super.validateSessionRole((String) (req.getSession().getAttribute("role")));
            User newUser = new User.Builder()
                .setUserName(userName)
                .setHashPass(hashPass)
                .setRegistrationDate(registrationDate)
                .setRegistrationTime(registrationTime)
                .setRole(role.ordinal())
                .build();

        approved = servicePack.getUserService().setUser(newUser);
        }catch (InvalidSessionException exception) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new CommandException("in " + this.getClass().getName() + " , while validate session", exception);
        }
        catch (ServiceException exception) {
            throw new CommandException("in " + this.getClass().getName() + " , adding user", exception);
        }
        if(approved){
            session.setAttribute("userName", userName);
            session.setAttribute("role", role.name());
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
