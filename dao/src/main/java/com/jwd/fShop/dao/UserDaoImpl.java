package com.jwd.fShop.dao;

import com.jwd.fShop.dao.connectionPool.ConnectionPool;
import com.jwd.fShop.dao.domain.*;
import com.jwd.fShop.dao.exception.DaoException;
import com.jwd.fShop.dao.exception.FatalDaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class UserDaoImpl implements UserDao{
    Map<Integer, String> roleMap = new HashMap<>();

    private static int PRODUCT_FIELDS_QUANTITY = 5;
    private final String SQL_INSERT_SINGLE_ITEM = "INSERT INTO usersTable (userName, PasswordHash, Role_id, registrationDate, registrationTime) Values (?, ?, ?, ?, ?)";
    private static final String SQL_ITEM = "(?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_ITEM = "DELETE FROM usersTable WHERE id = ?;";
    private static final String SQL_SELECT_MULTIPLE_ITEMS = "SELECT ID, userName, PasswordHash, Role_ID, registrationDate, registrationTime FROM usersTable"; //+FILTER_STR+LIMIT_STR
    private static final String SQL_SELECT_SINGLE_SELECT = "SELECT ID, userName, PasswordHash, Role_ID, registrationDate, registrationTime FROM usersTable WHERE userName LIKE ?;";
    private static final String SQL_SELECT_LIMIT = "LIMIT ?, ?";
    private static final String SQL_UPDATE_ITEM_BY_ID = "UPDATE usersTable SET (UserName = '?', PasswordHash = ?, Role_ID = '?', registrationDate = ?, registrationTime = '?') WHERE id=?;";

    private ConnectionPool connectionPool;

    public UserDaoImpl() throws FatalDaoException {
        try {
            connectionPool = ConnectionPool.getInstance();
        }catch (Exception exception){
            throw new FatalDaoException("in ProductDaoImpl: getting connection pool fault.", exception);
        }
    }

    @Override
    public boolean setUser(User user) throws DaoException {
        Connection connection;
        PreparedStatement statement;
        boolean result;
        if(Objects.nonNull(user)) {
            try{
                connection = connectionPool.getConnection();
            }catch (Exception exception){
                throw new DaoException("in ProductDaoImpl: in setUser(User) while getting connection from connection pool", exception);
            }
            try {
                statement = connection.prepareStatement(SQL_INSERT_SINGLE_ITEM);
            }catch (Exception exception){
                throw new DaoException("in ProductDaoImpl: in setUser(User) while getting prepared statement", exception);
            }
            try {
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getHashPass());
                statement.setInt(3, user.getRole());
                statement.setDate(4, user.getRegistrationDate());
                statement.setTime(5, user.getRegistrationTime());

                result = statement.executeUpdate()== 0 ? false : true;
            }catch (Exception exception){
                throw new DaoException("in ProductDaoImpl: in setUser(User) while setting prepared statement parameters", exception);
            }finally {
                connectionPool.retrieveConnection(connection);
            }
            return  result;
        }
        return false;
    }

    @Override
    public User getUser(final String userName) throws DaoException {
        Connection connection;
        User user = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try{
            connection = connectionPool.getConnection();
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in getUser(String) while getting connection from connection pool", exception);
        }
        try {
            statement = connection.prepareStatement(SQL_SELECT_SINGLE_SELECT);

            statement.setString(1, userName);

            resultSet = statement.executeQuery();

            if(resultSet.next()){
                user = new User.Builder()
                        .setId(resultSet.getInt("ID"))
                        .setUserName(resultSet.getNString("userName"))
                        .setHashPass(resultSet.getString("PasswordHash"))
                        .setRegistrationDate(resultSet.getDate("registrationDate"))
                        .setRegistrationTime(resultSet.getTime("registrationTime"))
                        .setRole(resultSet.getInt("Role_ID"))
                        .build();
            }
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in getUser(String) while getting prepared statement", exception);
        }finally {
            connectionPool.retrieveConnection(connection);
        }

        return user;
    }

    @Override
    public LinkedList<User> getUsers(UserFilter userFilter) throws DaoException {
        Connection connection;

        PreparedStatement statement;
        LinkedList<User> users = new LinkedList<>();
        ResultSet resultSet;

        try{
            connection = connectionPool.getConnection();
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in getUsers(UserFilter) while getting connection from connection pool", exception);
        }
        try {
            statement = connection.prepareStatement(getMultipleSelectSQL(userFilter));

            setSelectParams(statement, userFilter);

            resultSet = statement.executeQuery();

            while (resultSet.next()){
                users.add(new User.Builder()
                        .setId(resultSet.getInt("ID"))
                        .setUserName(resultSet.getNString("userName"))
                        .setHashPass(resultSet.getString("PasswordHash"))
                        .setRegistrationDate(resultSet.getDate("registrationDate"))
                        .setRegistrationTime(resultSet.getTime("registrationTime"))
                        .setRole(resultSet.getInt("Role_ID"))
                        .build());
            }
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in getUsers(UserFilter) while getting prepared statement", exception);
        }finally {
            connectionPool.retrieveConnection(connection);
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws DaoException {
        Connection connection;
        PreparedStatement statement;
        boolean result;

        try{
            connection = connectionPool.getConnection();
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in deleteUser(int) while getting connection from connection pool", exception);
        }

        try {
            statement = connection.prepareStatement(SQL_DELETE_ITEM);

            statement.setInt(1, id);

            result = statement.executeUpdate()== 0 ? false : true;
        }catch (Exception exception){
            throw new DaoException("in ProductDaoImpl: in deleteUser(int) while getting prepared statement", exception);
        }finally {
            connectionPool.retrieveConnection(connection);
        }
        return  result;
    }

    @Override
    public int deleteUsers(LinkedList<Integer> listId) throws DaoException {
        int result = 0;
        if(Objects.isNull(listId)){

            for(Integer id : listId){
                if(deleteUser(id)){
                    ++result;
                }
            }
        }
        return result;
    }

    private String getMultipleSelectSQL(final UserFilter userFilter){
        boolean isNotFirstElement = false;
        String sql = SQL_SELECT_MULTIPLE_ITEMS;
        if(userFilter.isUserSubname()){
            sql += "WHERE userName LIKE '%?%'";
            isNotFirstElement = true;
        }
        if(userFilter.isId()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += " ID=?";
            isNotFirstElement = true;
        }
        if(userFilter.isSubHashPass()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "WHERE PasswordHash LIKE '%?%'";
            isNotFirstElement = true;
        }
        if(userFilter.isHighDate()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "registrationDate <= ? ";
            isNotFirstElement = true;
        }
        if(userFilter.isLowDate()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "registrationDate >= ? ";
        }
        if(userFilter.isHighTime()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "registrationTime <= ? ";
            isNotFirstElement = true;
        }
        if(userFilter.isLowTime()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "registrationTime >= ? ";
        }
        if(userFilter.isRole()){
            if(isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "Role_ID == ? ";
        }
        sql+=";";

        return sql;
    }

    private PreparedStatement setSelectParams(final PreparedStatement preparedStatement, final UserFilter userFilter)throws Exception{
        int counter = 1;
        if(userFilter.isUserSubname()){
            preparedStatement.setString(counter++, userFilter.getUserSubname());
        }
        if(userFilter.isId()){
            preparedStatement.setInt(counter++, userFilter.getId());
        }
        if(userFilter.isSubHashPass()){
            preparedStatement.setString(counter++, userFilter.getSubHashPass());
        }
        if(userFilter.isHighDate()){
            preparedStatement.setDate(counter++, userFilter.getHighDate());
        }
        if(userFilter.isLowDate()){
            preparedStatement.setDate(counter++, userFilter.getLowDate());
        }
        if(userFilter.isHighTime()){
            preparedStatement.setTime(counter++, userFilter.getHighTime());
        }
        if(userFilter.isLowTime()){
            preparedStatement.setTime(counter++, userFilter.getLowTime());
        }
        if(userFilter.isRole()){
            preparedStatement.setInt(counter++, userFilter.getRole());
        }
        return preparedStatement;
    }
}
