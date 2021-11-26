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

public class ProductServiceImpl implements ProductService {
    static private final int DEFAULT_PAGE_SIZE = 20;
    static private int pageSize;
    private ProductDao productStorage;


    public ProductServiceImpl() throws FatalServiceException{
        try {
            pageSize = DEFAULT_PAGE_SIZE;
            productStorage = DaoFactory.getInstance().getProductDao();
        } catch (FatalDaoException e) {
            throw new FatalServiceException("in " + this.getClass().getName() + " while setting productDao", e);
        }
    }

    @Override
    public boolean addProduct(Product product) throws ServiceException {
        boolean result;
        try {
            result = productStorage.setProduct(daoProductFromProduct(product));
        }catch (DaoException exception){
            throw new ServiceException("in ProductServiceImpl: in addProduct(Product) while setting product in dao", exception);
        }

        return result;
    }

    @Override
    public int getPageQuantity(final ProductFilter productFilter) throws DaoException {
        return productStorage.getPageQuantity(createDaoProductFilter(productFilter), pageSize);
    }

    @Override
    public LinkedList<Product> getProductsPage(final ProductFilter productFilter, int pageNumber) throws ServiceException{
        LinkedList<com.jwd.fShop.dao.domain.Product> daoProductList;
        com.jwd.fShop.dao.domain.ProductFilter daoProductFilter = createDaoProductFilter(productFilter);
        try {
            daoProductList = productStorage.getProductListPage(daoProductFilter, pageSize, pageNumber);
        }catch (DaoException exception){
            throw new ServiceException("in ProductServiceImpl: in getProduct(ProductFilter) while getting products from dao", exception);
        }
        LinkedList<Product> productList = new LinkedList<>();
        for(com.jwd.fShop.dao.domain.Product daoProduct: daoProductList){
            productList.add(productFromDaoProduct(daoProduct));
        }
        return productList;
    }

    @Override
    public Product getProduct(int id) throws ServiceException {
        Product product = null;
        try{
            product = productFromDaoProduct(productStorage.getProduct(id));
        } catch (DaoException exception) {
            throw new ServiceException("In " + this.getClass().getName() + " in getProduct(int) while getting product", exception);
        }
        return product;
    }

    private com.jwd.fShop.dao.domain.ProductFilter createDaoProductFilter(ProductFilter productFilter){
        com.jwd.fShop.dao.domain.ProductFilter.Builder builder = new com.jwd.fShop.dao.domain.ProductFilter.Builder();
        if(productFilter.isId())
            builder.setId(productFilter.getId());
        if(productFilter.isName())
            builder.setName(productFilter.getName());
        if(productFilter.isLowPrice())
            builder.setPriceRange(productFilter.getLowPrice(), productFilter.getHighPrice());
        if(productFilter.isProductType())
            builder.setProductType(com.jwd.fShop.dao.domain.ProductType.valueOf(productFilter.getProductType().name()));
        return builder.build();
    }

    private Product productFromDaoProduct(com.jwd.fShop.dao.domain.Product daoProduct){
        Product product = null;
        if(Objects.nonNull(daoProduct)){
            product = new Product.Builder()
                    .setId(daoProduct.getId())
                    .setName(daoProduct.getName())
                    .setPrice(daoProduct.getPrice())
                    .setQuantity(daoProduct.getQuantity())
                    .setProductType(ProductType.valueOf(daoProduct.getProductType().name()))
                    .setParams(daoProduct.getParameters())
                    .build();
        }
        return product;
    }

    private com.jwd.fShop.dao.domain.Product daoProductFromProduct(Product product){
        com.jwd.fShop.dao.domain.Product daoProduct = null;
        if(Objects.nonNull(product)){
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
