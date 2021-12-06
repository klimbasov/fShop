package com.jwd.fShop.service;

import com.jwd.fShop.dao.DaoFactory;
import com.jwd.fShop.dao.ProductDao;
import com.jwd.fShop.service.domain.ProductType;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;
import com.jwd.fShop.service.domain.Product;
import com.jwd.fShop.service.domain.ProductFilter;
import com.jwd.fShop.service.exception.FatalServiceException;
import com.jwd.fShop.service.exception.ServiceException;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    static private final int DEFAULT_PAGE_SIZE = 20;
    static private int pageSize;
    private ProductDao productStorage;


    public ProductServiceImpl() {
        pageSize = DEFAULT_PAGE_SIZE;
        productStorage = DaoFactory.getInstance().getProductDao();
    }

    @Override
    public boolean addProduct(Product product) throws ServiceException {
        boolean result;
        try {
            result = productStorage.setProduct(daoProductFromProduct(product));
        } catch (DaoException exception) {
            throw new ServiceException("in ProductServiceImpl: in addProduct(Product) while setting product in dao", exception);
        }

        return result;
    }

    @Override
    public int getPageQuantity(final ProductFilter productFilter) throws DaoException {
        return productStorage.getPageQuantity(createDaoProductFilter(productFilter), pageSize);
    }

    @Override
    public LinkedList<Product> getProductsPage(final ProductFilter productFilter, int pageNumber) throws ServiceException {
        LinkedList<com.jwd.fShop.dao.domain.Product> daoProductList;
        com.jwd.fShop.dao.domain.ProductFilter daoProductFilter = createDaoProductFilter(productFilter);
        try {
            daoProductList = productStorage.getProductListPage(daoProductFilter, pageSize, pageNumber);
        } catch (DaoException exception) {
            throw new ServiceException("in ProductServiceImpl: in getProduct(ProductFilter) while getting products from dao", exception);
        }
        LinkedList<Product> productList = new LinkedList<>();
        for (com.jwd.fShop.dao.domain.Product daoProduct : daoProductList) {
//            productList.add(productFromDaoProduct(daoProduct)); // change to optional todo
        }
        return productList;
    }

    @Override
    public Optional<Product> getProduct(int id) throws ServiceException {
        try {
            return productFromDaoProduct(productStorage.getProduct(id));
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in getProduct(int) while getting product", exception);
        }
    }

    private com.jwd.fShop.dao.domain.ProductFilter createDaoProductFilter(ProductFilter productFilter) {
        com.jwd.fShop.dao.domain.ProductFilter.Builder builder = new com.jwd.fShop.dao.domain.ProductFilter.Builder();
        if (productFilter.isId())
            builder.setId(productFilter.getId());
        if (productFilter.isName())
            builder.setName(productFilter.getName());
        if (productFilter.isLowPrice())
            builder.setPriceRange(productFilter.getLowPrice(), productFilter.getHighPrice());
        if (productFilter.isProductType())
            builder.setProductType(com.jwd.fShop.dao.domain.ProductType.valueOf(productFilter.getProductType().name()));
        return builder.build();
    }

    private Optional<Product> productFromDaoProduct(Optional<com.jwd.fShop.dao.domain.Product> daoProduct) {
        Product product = null;
        if (daoProduct.isPresent()) {
            product = new Product.Builder()
                    .setId(daoProduct.get().getId())
                    .setName(daoProduct.get().getName())
                    .setPrice(daoProduct.get().getPrice())
                    .setQuantity(daoProduct.get().getQuantity())
                    .setProductType(ProductType.valueOf(daoProduct.get().getProductType().name()))
                    .setParams(daoProduct.get().getParameters())
                    .build();
        }
        return Optional.ofNullable(product);
    }

    private com.jwd.fShop.dao.domain.Product daoProductFromProduct(Product product) {
        com.jwd.fShop.dao.domain.Product daoProduct = null;
        if (Objects.nonNull(product)) {
            daoProduct = new com.jwd.fShop.dao.domain.Product.
                    Builder().
                    setId(product.getId()).
                    setName(product.getName()).
                    setProductType(com.jwd.fShop.dao.domain.ProductType.valueOf(product.getProductType().name())).
                    setPrice(product.getPrice()).
                    setParams(product.getParameters()).
                    build();
        }
        return daoProduct;
    }
}
