package com.jwd.fShop.controller.command.impl;

import com.jwd.fShop.controller.command.Command;
import com.jwd.fShop.controller.command.Role;
import com.jwd.fShop.controller.exception.CommandException;
import com.jwd.fShop.controller.exception.InvalidSessionException;
import com.jwd.fShop.service.ServiceFactory;
import com.jwd.fShop.service.UserService;
import com.jwd.fShop.service.domain.User;
import com.jwd.fShop.service.exception.ServiceException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;

import static com.jwd.fShop.controller.constant.Parameters.*;
import static com.jwd.fShop.controller.constant.Attributes.*;
import static java.util.Objects.nonNull;


public class RegisterUserCommand extends AbstractCommand implements Command {

    private static final Role role = Role.UNREGISTERED_USER;
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    public RegisterUserCommand() {
        super(role);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        String userName = req.getParameter(PARAMETER_USERNAME);
        String hashPass = req.getParameter(PARAMETER_PASSWORD);
        HttpSession session = req.getSession();
        boolean approved = false;
        Date registrationDate = new Date(Date.from(Instant.now()).getTime());
        Time registrationTime = new Time(Time.from(Instant.now()).getTime());
        Role role = Role.REGISTERED_USER;

        try {
            super.validateSessionRole((String) (req.getSession().getAttribute(ATTRIBUTE_ROLE)));

            User newUser = new User.Builder()
                    .setUserName(userName)
                    .setHashPass(hashPass)
                    .setRegistrationDate(registrationDate)
                    .setRegistrationTime(registrationTime)
                    .setRole(role.ordinal())
                    .build();

            newUser = userService.registerUser(newUser);

            if (nonNull(newUser)) {
                session.setAttribute(ATTRIBUTE_USERNAME, userName);
                session.setAttribute(ATTRIBUTE_ROLE, role.name());
            } else {
                JSONObject responseData = new JSONObject();
                responseData.put("error", "wrong!");
                resp.getWriter().write(responseData.toString());
            }
        } catch (InvalidSessionException | ServiceException | IOException exception) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new CommandException("in " + this.getClass().getName() + " , while validate session", exception);
        }

    }
}
