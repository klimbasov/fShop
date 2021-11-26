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

    public UserServiceImpl() throws FatalServiceException {
        try {
            userDao = DaoFactory.getInstance().getUserDao();
        } catch (FatalDaoException e) {
            throw new FatalServiceException("in " + this.getClass().getName() + " while setting userDao", e);
        }

    }

    @Override
    public boolean setUser(User user) throws ServiceException {
        boolean result = false;
        if(validate(user)){
            com.jwd.fShop.dao.domain.User daoUser = new com.jwd.fShop.dao.domain.User.Builder()
                    .setId(user.getId())
                    .setUserName(user.getUserName())
                    .setHashPass(user.getHashPass())
                    .setRegistrationDate(user.getRegistrationDate())
                    .setRegistrationTime(user.getRegistrationTime())
                    .setRole(user.getRole())
                    .build();
            try{
                if(Objects.isNull(userDao.getUser(daoUser.getUserName()))){
                    userDao.setUser(daoUser);
                    result = true;
                }else {
                    result = false;
                }
            } catch (DaoException exception) {
                throw new ServiceException("in UserServiceImpl: in setUser(User user) while setting user in dao", exception);
            }
        }
        return result;
    }

    @Override
    public boolean hasSuchUser(String userName, String hashPass) throws ServiceException {
        boolean result = false;
        com.jwd.fShop.dao.domain.UserFilter daoUserFilter = new com.jwd.fShop.dao.domain.UserFilter.Builder()
                .setUserSubname(userName)
                .setSubHashPass(hashPass)
                .build();
        try {
            com.jwd.fShop.dao.domain.User daoUser = userDao.getUser(userName);
            if(Objects.nonNull(daoUser)){
                if(daoUser.getHashPass().equals(hashPass)){
                    result = true;
                }
            }
        } catch (DaoException exception) {
            throw new ServiceException("in " + this.getClass().getName() + " in getUsers(UserFilter) while getting users from dao", exception);
        }
        return result;

    }

    @Override
    public User getUser(String userName) throws ServiceException {
        User user = null;
        if(Objects.nonNull(userName)){
            try{
                com.jwd.fShop.dao.domain.User daoUser= userDao.getUser(userName);
                user = new User.Builder()
                        .setId(daoUser.getId())
                        .setUserName(daoUser.getUserName())
                        .setHashPass(daoUser.getHashPass())
                        .setRegistrationDate(daoUser.getRegistrationDate())
                        .setRegistrationTime(daoUser.getRegistrationTime())
                        .setRole(daoUser.getRole())
                        .build();
            } catch (DaoException exception) {
                throw new ServiceException("in UserServiceImpl: in getUser(String) while getting user from dao", exception);
            }
        }
        return user;
    }

    @Override
    public LinkedList<User> getUsers(UserFilter userFilter) throws ServiceException {
        LinkedList<User> users = new LinkedList<>();
        LinkedList<com.jwd.fShop.dao.domain.User> daoUsers;
        if(Objects.nonNull(userFilter)){
            com.jwd.fShop.dao.domain.UserFilter daoUserFilter = new com.jwd.fShop.dao.domain.UserFilter.Builder()
                    .setUserSubname(userFilter.getUserSubname())
                    .setSubHashPass(userFilter.getSubHashPass())
                    .setHighDate(userFilter.getHighDate())
                    .setHighTime(userFilter.getHighTime())
                    .setLowDate(userFilter.getLowDate())
                    .setLowTime(userFilter.getLowTime())
                    .setRole(userFilter.getRole())
                    .build();
            try{
                daoUsers = userDao.getUsers(daoUserFilter);
            } catch (DaoException exception) {
                throw new ServiceException("in UserServiceImpl: in getUsers(UserFilter) while getting users from dao", exception);
            }
            for(com.jwd.fShop.dao.domain.User daoUser : daoUsers){
                users.add(new User.Builder()
                        .setId(daoUser.getId())
                        .setUserName(daoUser.getUserName())
                        .setHashPass(daoUser.getHashPass())
                        .setRegistrationDate(daoUser.getRegistrationDate())
                        .setRegistrationTime(daoUser.getRegistrationTime())
                        .setRole(daoUser.getRole())
                        .build());
            }

        }
        return users;
    }

    @Override
    public int deleteUsers(LinkedList<Integer> userList) throws ServiceException {
        int result = 0;
        LinkedList<com.jwd.fShop.dao.domain.User> daoUsers = new LinkedList<>();
        if(Objects.nonNull(userList)){
            try {
                result = userDao.deleteUsers(userList);
            } catch (DaoException exception) {
                throw new ServiceException("in UserServiceImpl: in deleteUsers(LinkedList<Integer> while getting users from dao", exception);
            }
        }
        return result;
    }

    private boolean validate(User user){
        boolean result = false;
        if(Objects.nonNull(user)){
            String name = user.getUserName();
            String hashPass = user.getHashPass();
            if(Objects.nonNull(name)
                    && Objects.nonNull(hashPass)
                    && Objects.nonNull(user.getRegistrationDate())
                    && Objects.nonNull(user.getRegistrationTime())){
                result = true;
            }
        }
        return result;
    }
}
