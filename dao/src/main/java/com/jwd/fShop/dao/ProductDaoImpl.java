package com.jwd.fShop.dao;

import com.jwd.fShop.dao.connectionPool.ConnectionPool;
import com.jwd.fShop.dao.domain.Product;
import com.jwd.fShop.dao.domain.ProductFilter;
import com.jwd.fShop.dao.domain.ProductType;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao{
    private static int PRODUCT_FIELDS_QUANTITY = 5;
    private static final String SQL_INSERT_SINGLE_ITEM = "INSERT INTO productsTable (name, quantity, productType, price, params) Values (?, ?, ?, ?, ?)";
    private static final String SQL_ITEM = "(?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_ITEM = "DELETE FROM productsTable WHERE id = ?;";
    private static final String SQL_SELECT_MULTIPLE_ITEMS = "SELECT id, name, price, quantity, productType, params FROM productsTable"; //+FILTER_STR+LIMIT_STR
    private static final String SQL_SELECT_SINGLE_ITEM_BY_ID = "SELECT id, name, price, quantity, productType, params FROM productsTable WHERE id = ?";
    private static final String SQL_SELECT_LIMIT = " LIMIT ?, ?";
    private static final String SQL_UPDATE_ITEM_BY_ID = "UPDATE productsTable SET (name = '?', price = ?, quantity = ?, productType = '?', params = '?') WHERE id=?;";
    private static final String SQL_GET_PAGE_QUANTITY = "SELECT COUNT(*) FROM  productsTable";

    private ConnectionPool connectionPool;

    public ProductDaoImpl() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public boolean setProduct(Product product) throws DaoException{
        Connection connection;
        PreparedStatement statement;
        boolean result;
        if(Objects.nonNull(product)) {
            try{
                connection = connectionPool.getConnection();
            }catch (Exception exception){
                throw new DaoException("in ProductDaoImpl: in setProduct(Product) while getting connection from connection pool", exception);
            }
            try {
                statement = connection.prepareStatement(SQL_INSERT_SINGLE_ITEM);

                statement.setString(1, product.getName());
                statement.setInt(2, product.getQuantity());
                statement.setString(3, product.getProductType().name());
                statement.setFloat(4, product.getPrice());
                statement.setString(5, "");

                result = statement.executeUpdate()== 0 ? false : true;
            }catch (Exception exception){
                throw new DaoException("in ProductDaoImpl: in setProduct(Product) while setting prepared statement parameters", exception);
            }finally {
                connectionPool.retrieveConnection(connection);
            }

            return  result;
        }
        return false;
    }

    @Override
    public int setProducts(LinkedList<Product> products) throws DaoException{
        Connection connection;
        PreparedStatement statement;
        int result;
        if(Objects.nonNull(products) && !products.isEmpty()){
            try{
                connection = connectionPool.getConnection();
            }catch (Exception exception){
                throw new DaoException("in ProductDaoImpl: in setProducts(LinkedList<Product>) while getting connection from connection pool", exception);
            }

            String sqlInsertStatement = SQL_INSERT_SINGLE_ITEM;
            for (int counter = 1; counter < products.size(); counter++){
                sqlInsertStatement +=  "," + SQL_ITEM;
            }
            sqlInsertStatement +=";";
            try {
                statement = connection.prepareStatement(sqlInsertStatement);
                int counter =0;
                for(Product product: products){
                    statement.setString(counter* PRODUCT_FIELDS_QUANTITY+1, product.getName());
                    statement.setInt(counter* PRODUCT_FIELDS_QUANTITY+2, product.getQuantity());
                    statement.setString(counter* PRODUCT_FIELDS_QUANTITY+3, product.getProductType().name());
                    statement.setFloat(counter* PRODUCT_FIELDS_QUANTITY+4, product.getPrice());
                    statement.setString(counter* PRODUCT_FIELDS_QUANTITY+5, "");
                    ++counter;

                    statement.executeUpdate();
                }
            }catch (Exception exception){
                throw new DaoException("in ProductDaoImpl: in setProducts(LinkedList<Product>) while getting prepared statement", exception);
            }finally {
                connectionPool.retrieveConnection(connection);
            }
        }

        return 0;
    }

    @Override
    public boolean deleteProduct(int id) throws DaoException{
        Connection connection;
        PreparedStatement statement;
        boolean result;

        try{
            connection = connectionPool.getConnection();
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in deleteProduct(int) while getting connection from connection pool", exception);
        }

        try {
            statement = connection.prepareStatement(SQL_DELETE_ITEM);

            statement.setInt(1, id);

            result = statement.executeUpdate()== 0 ? false : true;
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in deleteProduct(int) while getting prepared statement", exception);
        }finally {
            connectionPool.retrieveConnection(connection);
        }

        return  result;
    }

    @Override
    public Optional<Product> getProduct(int id) throws DaoException {
        Connection connection = null;

        PreparedStatement statement;
        Product product = null;
        ResultSet resultSet;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_SINGLE_ITEM_BY_ID);

            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()){
                product = new Product.Builder()
                        .setName(resultSet.getString("name"))
                        .setId(resultSet.getInt("id"))
                        .setPrice(resultSet.getFloat("price"))
                        .setQuantity(resultSet.getInt("quantity"))
                        .setProductType(ProductType.valueOf(resultSet.getString("productType")))
                        .setParams(resultSet.getString("params"))
                        .build();
            }

            return Optional.ofNullable(product);
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in getProductList(ProductFilter) while getting prepared statement", exception);
        }finally {
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public LinkedList<Product> getProductListPage(final ProductFilter productFilter, int pageSize, int pageNumber) throws DaoException{
        Connection connection;

        PreparedStatement statement;
        LinkedList<Product> products = new LinkedList<>();
        ResultSet resultSet;

        try{
            connection = connectionPool.getConnection();
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in getProductList(ProductFilter) while getting connection from connection pool", exception);
        }
        try {
            statement = connection.prepareStatement(getSelectConditionSQL(SQL_GET_PAGE_QUANTITY, productFilter));

            setSelectParams(statement, productFilter, pageSize, pageNumber);

            resultSet = statement.executeQuery();

            while (resultSet.next()){
                products.add(new Product.Builder()
                        .setName(resultSet.getString("name"))
                        .setId(resultSet.getInt("id"))
                        .setPrice(resultSet.getFloat("price"))
                        .setQuantity(resultSet.getInt("quantity"))
                        .setProductType(ProductType.valueOf(resultSet.getString("productType")))
                        .setParams(resultSet.getString("params"))
                        .build());
            }
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in getProductList(ProductFilter) while getting prepared statement", exception);
        }finally {
            connectionPool.retrieveConnection(connection);
        }

        return products;
    }

    @Override
    public int getPageQuantity(final ProductFilter productFilter, int pageSize) throws DaoException {
        Connection connection;
        PreparedStatement statement;
        ResultSet resultSet;
        int result = 0;

        try{
            connection = connectionPool.getConnection();
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in getProductList(ProductFilter) while getting connection from connection pool", exception);
        }
        try {
            statement = connection.prepareStatement(getSelectConditionSQL(SQL_GET_PAGE_QUANTITY, productFilter));

            setFilterSelectParams(statement, productFilter);

            resultSet = statement.executeQuery();

            if(resultSet.next()){
                result = resultSet.getInt(1);
                result = result / pageSize + (result % pageSize)%1;
            }
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in getProductList(ProductFilter) while getting prepared statement", exception);
        }finally {
            connectionPool.retrieveConnection(connection);
        }

        return result;
    }

    private String getSelectConditionSQL(final String base, final ProductFilter productFilter){
        boolean isNotFirstElement = false;
        String sql = base;
        if(productFilter.isName()){
            sql += "WHERE name LIKE '%?%'";
            isNotFirstElement = true;
        }
        if(productFilter.isId()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += " id=?";
            isNotFirstElement = true;
        }
        if(productFilter.isLowPrice()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "price>=?";
            isNotFirstElement = true;
        }
        if(productFilter.isHighPrice()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "price<=?";
            isNotFirstElement = true;
        }
        if(productFilter.isProductType()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "productType='?'";
        }
        sql += SQL_SELECT_LIMIT;
        sql+=";";

        return sql;
    }

    private PreparedStatement setSelectParams(final PreparedStatement preparedStatement, final ProductFilter productFilter, int pageSize, int pageNumber)throws Exception{
        int counter = setFilterSelectParams(preparedStatement, productFilter);
        preparedStatement.setInt(counter++, pageSize);
        preparedStatement.setInt(counter++, pageSize * (pageNumber-1));
        return preparedStatement;
    }
    private int setFilterSelectParams(final PreparedStatement preparedStatement, final ProductFilter productFilter) throws SQLException {
        int counter = 1;
        if(productFilter.isName()){
            preparedStatement.setString(counter++, productFilter.getName());
        }
        if(productFilter.isId()){
            preparedStatement.setInt(counter++, productFilter.getId());
        }
        if(productFilter.isLowPrice()){
            preparedStatement.setFloat(counter++, productFilter.getLowPrice());
        }
        if(productFilter.isHighPrice()){
            preparedStatement.setFloat(counter++, productFilter.getHighPrice());
        }
        if(productFilter.isProductType()){
            preparedStatement.setString(counter++, productFilter.getProductType().name());
        }
        return counter;
    }
}
