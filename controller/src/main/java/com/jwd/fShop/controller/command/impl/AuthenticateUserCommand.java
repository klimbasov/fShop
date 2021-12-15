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
import static com.jwd.fShop.controller.constant.Parameters.*;
import static com.jwd.fShop.controller.constant.Attributes.*;

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
        String userName = req.getParameter(PARAMETER_USERNAME);
        String hashPass = req.getParameter(PARAMETER_PASSWORD);
        User user = new User.Builder().setUserName(userName).setHashPass(hashPass).build();

        validateRole(req, resp, HttpServletResponse.SC_FORBIDDEN, "in " + this.getClass().getName() + " , while validate session");

        try {
                user = userService.authorizeUser(user);
//            user = userService.isAuthorized(user); // returns cut user data todo
            if (Objects.nonNull(user)) {
                    session.setAttribute(ATTRIBUTE_USERNAME, user.getUserName());
                    session.setAttribute(ATTRIBUTE_ROLE, Role.values()[user.getRole()].name());
                }
            else {
                JSONObject responseData = new JSONObject();
                responseData.put("error", "wrong!");
                resp.getWriter().write(responseData.toString());
            }
        } catch (Exception exception) {
            throw new CommandException("in " + this.getClass().getName() + " , adding user", exception);
        }
    }
}
