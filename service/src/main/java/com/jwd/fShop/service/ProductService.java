package com.jwd.fShop.service;

import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.service.domain.Product;
import com.jwd.fShop.service.domain.ProductFilter;
import com.jwd.fShop.service.exception.ServiceException;

import java.util.LinkedList;

public interface ProductService {

    boolean addProduct(final Product product) throws ServiceException;

    LinkedList<Product> getProductsPage(final ProductFilter productFilter, int pageNumber) throws ServiceException;

    int getPageQuantity(final ProductFilter productFilter) throws DaoException;

    Product getProduct(int id) throws ServiceException;
}
