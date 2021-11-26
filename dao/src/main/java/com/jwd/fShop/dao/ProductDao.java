package com.jwd.fShop.dao;

import com.jwd.fShop.dao.domain.Product;
import com.jwd.fShop.dao.domain.ProductFilter;
import com.jwd.fShop.dao.exception.DaoException;

import java.util.LinkedList;

public interface ProductDao {
    boolean setProduct(final Product product)throws DaoException;

    int setProducts(LinkedList<Product> products) throws DaoException;

    boolean deleteProduct(final int id)throws DaoException;

    Product getProduct(int id) throws DaoException;

    LinkedList<Product> getProductListPage(final ProductFilter productFilter, int pageSize, int pageNumber)throws DaoException;

    int getPageQuantity(final ProductFilter productFilter, int pageSize) throws DaoException;
}
