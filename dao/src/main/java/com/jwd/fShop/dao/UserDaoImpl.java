package com.jwd.fShop.dao;

import com.jwd.fShop.dao.connectionPool.ConnectionPool;
import com.jwd.fShop.dao.domain.User;
import com.jwd.fShop.dao.domain.UserFilter;
import com.jwd.fShop.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class UserDaoImpl implements UserDao {
    Map<Integer, String> roleMap = new HashMap<>();

    private static final String USER_ID_DATABASE_NAME = "ID";
    private static final String USER_USERNAME_DATABASE_NAME = "userName";
    private static final String USER_PASSWORD_HASH_DATABASE_NAME = "PasswordHash";
    private static final String USER_REGISTRATION_DATE_DATABASE_NAME = "registrationDate";
    private static final String USER_REGISTRATION_TIME_DATABASE_NAME = "registrationTime";
    private static final String USER_ROLE_DATABASE_NAME = "Role_ID";

    private static int PRODUCT_FIELDS_QUANTITY = 5;
    private final String SQL_INSERT_SINGLE_ITEM = "INSERT INTO usersTable (userName, PasswordHash, Role_id, registrationDate, registrationTime) Values (?, ?, ?, ?, ?)";
    private static final String SQL_ITEM = "(?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_ITEM = "DELETE FROM usersTable WHERE id = ?;";
    private static final String SQL_SELECT_MULTIPLE_ITEMS = "SELECT ID, userName, PasswordHash, Role_ID, registrationDate, registrationTime FROM usersTable"; //+FILTER_STR+LIMIT_STR
    private static final String SQL_SELECT_SINGLE_USER = "SELECT ID, userName, Role_ID, registrationDate, registrationTime FROM usersTable WHERE "
            + USER_USERNAME_DATABASE_NAME
            + " LIKE ? AND "
            + USER_PASSWORD_HASH_DATABASE_NAME
            + " LIKE ?;";
    private static final String SQL_SELECT_LIMIT = "LIMIT ?, ?";
    private static final String SQL_UPDATE_ITEM_BY_ID = "UPDATE usersTable SET (UserName = '?', PasswordHash = ?, Role_ID = '?', registrationDate = ?, registrationTime = '?') WHERE id=?;";



    private ConnectionPool connectionPool;

    public UserDaoImpl() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public User setUser(User user) throws DaoException {
        Connection connection;
        PreparedStatement statement;
        User result = null;

        if (nonNull(user)) {
            try {
                connection = connectionPool.getConnection();

                statement = connection.prepareStatement(SQL_INSERT_SINGLE_ITEM);

                statement.setString(1, user.getUserName());
                statement.setString(2, user.getHashPass());
                statement.setInt(3, user.getRole());
                statement.setDate(4, user.getRegistrationDate());
                statement.setTime(5, user.getRegistrationTime());

                if (statement.executeUpdate() != 0) {
                    result = new User.Builder()
                            .setUserName(user.getUserName())
                            .setRole(user.getRole())
                            .build();
                }
            } catch (Exception exception) {
                throw new DaoException("in ProductDaoImpl: in setUser(User) while getting connection from connection pool", exception);
            }
        }
        return result;
    }

    @Override
    public User getUser(final User user) throws DaoException {
        Connection connection = null;
        User resultUser = null;
        PreparedStatement statement;
        ResultSet resultSet;

        validateUser(user);

        try {
            connection = connectionPool.getConnection();

            statement = connection.prepareStatement(SQL_SELECT_SINGLE_USER);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getHashPass());
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                resultUser = new User.Builder()
                        .setId(resultSet.getInt(USER_ID_DATABASE_NAME))
                        .setUserName(resultSet.getNString(USER_USERNAME_DATABASE_NAME))
                        .setRole(resultSet.getInt(USER_ROLE_DATABASE_NAME))
                        .build();
            }
        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in getUser(String) while getting prepared statement", exception);
        } finally {
            connectionPool.retrieveConnection(connection);
        }

        return resultUser;
    }

    @Override
    public LinkedList<User> getUsers(UserFilter userFilter) throws DaoException {
        Connection connection;

        PreparedStatement statement;
        LinkedList<User> users = new LinkedList<>();
        ResultSet resultSet;

        try {
            connection = connectionPool.getConnection();
        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in getUsers(UserFilter) while getting connection from connection pool", exception);
        }
        try {
            statement = connection.prepareStatement(getMultipleSelectSQL(userFilter));

            setSelectParams(statement, userFilter);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(new User.Builder()
                        .setId(resultSet.getInt(USER_ID_DATABASE_NAME))
                        .setUserName(resultSet.getNString(USER_USERNAME_DATABASE_NAME))
                        .setHashPass(resultSet.getString(USER_PASSWORD_HASH_DATABASE_NAME))
                        .setRegistrationDate(resultSet.getDate(USER_REGISTRATION_DATE_DATABASE_NAME))
                        .setRegistrationTime(resultSet.getTime(USER_REGISTRATION_TIME_DATABASE_NAME))
                        .setRole(resultSet.getInt(USER_ROLE_DATABASE_NAME))
                        .build());
            }
        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in getUsers(UserFilter) while getting prepared statement", exception);
        } finally {
            connectionPool.retrieveConnection(connection);
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_ITEM);
            statement.setInt(1, id);

            return isSuccessfulResult(statement.executeUpdate());
        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in deleteUser(int) while getting prepared statement", exception);
        } finally {
            closeAutoCloseable(statement);
            connectionPool.retrieveConnection(connection);
        }
    }

    @Override
    public int deleteUsers(List<Integer> listId) throws DaoException {
        int result = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            for (Integer id : listId) {
                statement = connection.prepareStatement(SQL_DELETE_ITEM);
                statement.setInt(1, id);
                statement.executeUpdate();
                ++result;
            }
            connection.commit();
            return result;
        } catch (Exception exception) {
            throw new DaoException("in ProductDaoImpl: in deleteUser(int) while getting prepared statement", exception);
        } finally {
            closeAutoCloseable(statement);
            connectionPool.retrieveConnection(connection);
        }
    }

    private String getMultipleSelectSQL(final UserFilter userFilter) {
        boolean isNotFirstElement = false;
        String sql = SQL_SELECT_MULTIPLE_ITEMS;
        if (userFilter.isUserSubname()) {
            sql += "WHERE userName LIKE '%?%'";
            isNotFirstElement = true;
        }
        if (userFilter.isId()) {
            if (isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += " ID=?";
            isNotFirstElement = true;
        }
        if (userFilter.isSubHashPass()) {
            if (isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "WHERE PasswordHash LIKE '%?%'";
            isNotFirstElement = true;
        }
        if (userFilter.isHighDate()) {
            if (isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "registrationDate <= ? ";
            isNotFirstElement = true;
        }
        if (userFilter.isLowDate()) {
            if (isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "registrationDate >= ? ";
        }
        if (userFilter.isHighTime()) {
            if (isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "registrationTime <= ? ";
            isNotFirstElement = true;
        }
        if (userFilter.isLowTime()) {
            if (isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "registrationTime >= ? ";
        }
        if (userFilter.isRole()) {
            if (isNotFirstElement)
                sql += " AND";
            else
                sql += " WHERE";
            sql += "Role_ID == ? ";
        }
        sql += ";";

        return sql;
    }

    private PreparedStatement setSelectParams(final PreparedStatement preparedStatement, final UserFilter userFilter) throws Exception {
        int counter = 1;
        if (userFilter.isUserSubname()) {
            preparedStatement.setString(counter++, userFilter.getUserSubname());
        }
        if (userFilter.isId()) {
            preparedStatement.setInt(counter++, userFilter.getId());
        }
        if (userFilter.isSubHashPass()) {
            preparedStatement.setString(counter++, userFilter.getSubHashPass());
        }
        if (userFilter.isHighDate()) {
            preparedStatement.setDate(counter++, userFilter.getHighDate());
        }
        if (userFilter.isLowDate()) {
            preparedStatement.setDate(counter++, userFilter.getLowDate());
        }
        if (userFilter.isHighTime()) {
            preparedStatement.setTime(counter++, userFilter.getHighTime());
        }
        if (userFilter.isLowTime()) {
            preparedStatement.setTime(counter++, userFilter.getLowTime());
        }
        if (userFilter.isRole()) {
            preparedStatement.setInt(counter++, userFilter.getRole());
        }
        return preparedStatement;
    }

    // todo export to abstract class for example

    protected void closeAutoCloseable(AutoCloseable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            if (nonNull(autoCloseable)) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isSuccessfulResult(int result) {
        return result != 0;
    }

    void validateUser(final User user) throws DaoException {
        if (isNull(user)) {
            throw new DaoException("Invalid user.");
        }
        if(isNull(user.getUserName())){
            throw new DaoException("Invalid user.");
        }
    }
}
