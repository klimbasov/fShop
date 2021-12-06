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
import java.util.Objects;

public class AuthenticateUserCommand extends AbstractCommand implements Command {

    private static final Role role = Role.UNREGISTERED_USER;
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    public AuthenticateUserCommand() {
        super(role);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        boolean approved = false;
        HttpSession session = req.getSession();
        String userName = req.getParameter("userName");
        String hashPass = req.getParameter("password");
        User user = new User.Builder().setUserName(userName).setHashPass(hashPass).build();

        validateRole(req, resp, HttpServletResponse.SC_FORBIDDEN, "in " + this.getClass().getName() + " , while validate session");

        try {
            // select id, name, role from users where userName = ? and hashPass = ?
//            user = userService.isAuthorized(user); // returns cut user data todo
            if (Objects.nonNull(user)) {
                if (user.getHashPass().equals(hashPass)) {
                    session.setAttribute("user", user);
                }
            } else {
                JSONObject responseData = new JSONObject();
                responseData.put("error", "wrong!");
                resp.getWriter().write(responseData.toString());
            }
        } catch (Exception exception) {
            throw new CommandException("in " + this.getClass().getName() + " , adding user", exception);
        }
    }
}
