package com.jwd.fShop.dao;

import com.jwd.fShop.dao.domain.User;
import com.jwd.fShop.dao.domain.UserFilter;
import com.jwd.fShop.dao.exception.DaoException;

import java.util.LinkedList;
import java.util.List;

public interface UserDao {
    boolean setUser(final User user) throws DaoException;

    User getUser(final String userName) throws DaoException;

    LinkedList<User> getUsers(final UserFilter userFilter) throws DaoException;

    boolean deleteUser(int id) throws DaoException;

    int deleteUsers(List<Integer> listId) throws DaoException;


}
