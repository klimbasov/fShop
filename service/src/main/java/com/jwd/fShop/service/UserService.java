package com.jwd.fShop.service;

import com.jwd.fShop.service.domain.User;
import com.jwd.fShop.service.domain.UserFilter;
import com.jwd.fShop.service.exception.ServiceException;

import java.util.LinkedList;

public interface UserService {
    boolean setUser(final User user) throws ServiceException;

    boolean hasSuchUser(final String userName, final String hashPass) throws ServiceException;

    User getUser(final String userName) throws ServiceException;

    LinkedList<User> getUsers(final UserFilter userFilter) throws ServiceException;

    int deleteUsers(final LinkedList<Integer> userList) throws ServiceException;
}
