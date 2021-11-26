package com.jwd.fShop.service;

import com.jwd.fShop.dao.*;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.service.domain.User;
import com.jwd.fShop.service.exception.FatalServiceException;

import java.util.Objects;

public class ServiceFactory {
    private static ServiceFactory INSTANCE;
    private final ProductService productService;
    private final UserService userService;
    private final OrderServise orderService;

    private ServiceFactory() throws FatalServiceException {
        productService = new ProductServiceImpl();
        userService = new UserServiceImpl();
        orderService = null;
    }

    public static ServiceFactory getInstance() throws FatalServiceException {
        if(Objects.isNull(INSTANCE)){
            INSTANCE = new ServiceFactory();
        }
        return INSTANCE;
    }


    public ProductService getProductService() {
        return productService;
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderServise getOrderService() {
        return orderService;
    }
}
