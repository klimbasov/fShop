package com.jwd.fShop.service;

import com.jwd.fShop.dao.DaoFactory;
import com.jwd.fShop.dao.UserDao;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.service.domain.User;
import com.jwd.fShop.service.domain.UserFilter;
import com.jwd.fShop.service.exception.FatalServiceException;
import com.jwd.fShop.service.exception.ServiceException;

import javax.jws.soap.SOAPBinding;
import java.io.Closeable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl() {
        userDao = DaoFactory.getInstance().getUserDao();

    }

    @Override
    public boolean setUser(User user) throws ServiceException {
        try {
            validate(user); // throws invalid exception with descriptive msg

            com.jwd.fShop.dao.domain.User daoUser = new com.jwd.fShop.dao.domain.User.Builder()
                    .setId(user.getId())
                    .setUserName(user.getUserName())
                    .setHashPass(user.getHashPass())
                    .setRegistrationDate(user.getRegistrationDate())
                    .setRegistrationTime(user.getRegistrationTime())
                    .setRole(user.getRole())
                    .build();

            boolean result = Objects.isNull(userDao.getUser(daoUser.getUserName()));

            if (result) {
                userDao.setUser(daoUser);
            }

            return result;
        } catch (DaoException exception) {
            throw new ServiceException("in UserServiceImpl: in setUser(User user) while setting user in dao", exception);
        }
    }

    @Override
    public boolean hasSuchUser(String userName, String hashPass) throws ServiceException {
        com.jwd.fShop.dao.domain.UserFilter daoUserFilter = new com.jwd.fShop.dao.domain.UserFilter.Builder()
                .setUserSubname(userName)
                .setSubHashPass(hashPass)
                .build();
        try {
            com.jwd.fShop.dao.domain.User daoUser = userDao.getUser(userName); // todo! pass daoUserFilter
            return Objects.nonNull(daoUser) && daoUser.getHashPass().equals(hashPass);
        } catch (DaoException exception) {
            throw new ServiceException("in " + this.getClass().getName() + " in getUsers(UserFilter) while getting users from dao", exception);
        }

    }

    @Override
    public User getUser(String userName) throws ServiceException {
        User user = null;
        if (Objects.nonNull(userName)) {
            try {
                com.jwd.fShop.dao.domain.User daoUser = userDao.getUser(userName);
                user = convertUser(daoUser);
            } catch (DaoException exception) {
                throw new ServiceException("in UserServiceImpl: in getUser(String) while getting user from dao", exception);
            }
        }
        return user;
    }

    @Override
    public List<User> getUsers(UserFilter userFilter) throws ServiceException {
        try {
            LinkedList<User> users = new LinkedList<>();
            LinkedList<com.jwd.fShop.dao.domain.User> daoUsers;
            if (Objects.isNull(userFilter)) {
                throw new ServiceException("UserFilter is null");
            }
            com.jwd.fShop.dao.domain.UserFilter daoUserFilter = getUserFilter(userFilter);
            daoUsers = userDao.getUsers(daoUserFilter);
            convertUsers(users, daoUsers);
            return users;
        } catch (DaoException exception) {
            throw new ServiceException("in UserServiceImpl: in getUsers(UserFilter) while getting users from dao", exception);
        }
    }

    @Override
    public int deleteUsers(List<Integer> userList) throws ServiceException {
        int result = 0;
        LinkedList<com.jwd.fShop.dao.domain.User> daoUsers = new LinkedList<>();
        if (Objects.nonNull(userList)) {
            try {
                result = userDao.deleteUsers(userList);
            } catch (DaoException exception) {
                throw new ServiceException("in UserServiceImpl: in deleteUsers(LinkedList<Integer> while getting users from dao", exception);
            }
        }
        return result;
    }

    private boolean validate(User user) {
        boolean result = false;
        if (Objects.nonNull(user)) {
            String name = user.getUserName();
            String hashPass = user.getHashPass();
            if (Objects.nonNull(name)
                    && Objects.nonNull(hashPass)
                    && Objects.nonNull(user.getRegistrationDate())
                    && Objects.nonNull(user.getRegistrationTime())) {
                result = true;
            }
        }
        return result;
    }

    private com.jwd.fShop.dao.domain.UserFilter getUserFilter(UserFilter userFilter) {
        return new com.jwd.fShop.dao.domain.UserFilter.Builder()
                .setUserSubname(userFilter.getUserSubname())
                .setSubHashPass(userFilter.getSubHashPass())
                .setHighDate(userFilter.getHighDate())
                .setHighTime(userFilter.getHighTime())
                .setLowDate(userFilter.getLowDate())
                .setLowTime(userFilter.getLowTime())
                .setRole(userFilter.getRole())
                .build();
    }

    private void convertUsers(LinkedList<User> users, LinkedList<com.jwd.fShop.dao.domain.User> daoUsers) {
        for (com.jwd.fShop.dao.domain.User daoUser : daoUsers) {
            users.add(convertUser(daoUser));
        }
    }

    private User convertUser(com.jwd.fShop.dao.domain.User daoUser) {
        return new User.Builder()
                .setId(daoUser.getId())
                .setUserName(daoUser.getUserName())
                .setHashPass(daoUser.getHashPass())
                .setRegistrationDate(daoUser.getRegistrationDate())
                .setRegistrationTime(daoUser.getRegistrationTime())
                .setRole(daoUser.getRole())
                .build();
    }
}
