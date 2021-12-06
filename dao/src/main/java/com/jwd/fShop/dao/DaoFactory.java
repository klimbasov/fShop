package com.jwd.fShop.dao;

import com.jwd.fShop.dao.exception.FatalDaoException;

import java.util.Objects;

public class DaoFactory {

    private static DaoFactory INSTANCE;
    private final UserDao userDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;

    private DaoFactory() {
        userDao = new UserDaoImpl();
        orderDao = null;
        productDao = new ProductDaoImpl();
    }

    public static DaoFactory getInstance() {
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new DaoFactory();
        }
        return INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }
}
