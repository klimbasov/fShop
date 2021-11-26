package com.jwd.fShop.controller.domain;

import com.jwd.fShop.service.ProductService;
import com.jwd.fShop.service.UserService;

public class ServicePack {
    private final ProductService productService;
    private final UserService userService;

    public ServicePack(final ProductService productService, final UserService userService){
        this.productService = productService;
        this.userService = userService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public UserService getUserService() {
        return userService;
    }
}
