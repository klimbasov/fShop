package com.jwd.fShop.service;

import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.service.domain.Product;
import com.jwd.fShop.service.domain.ProductFilter;
import com.jwd.fShop.service.exception.ServiceException;

import java.util.LinkedList;
import java.util.Optional;

public interface ProductService {

    boolean addProduct(final Product product) throws ServiceException;

    LinkedList<Product> getProductsPage(final ProductFilter productFilter, int pageNumber) throws ServiceException;

    int getPageQuantity(final ProductFilter productFilter) throws DaoException;

    Optional<Product> getProduct(int id) throws ServiceException;
}
