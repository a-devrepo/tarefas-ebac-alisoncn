package com.sudy.projeto.config;

import com.sudy.projeto.exceptions.DAOException;

import java.sql.*;

public class ConnectionFactory {
    private static Connection connection;

    private ConnectionFactory(Connection connection){}

    public static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = initConnection();
    } else if (connection.isClosed()) {
            connection = initConnection();
        }
        return connection;
    }

    private static Connection initConnection(){
        try{
            return DriverManager.getConnection(
                  "jdbc:postgresql://localhost:15432/vendasonline","postgres","postgres2024"
            );
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection connection, PreparedStatement statement,
                                        ResultSet resultSet)  throws SQLException {

            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
            if(statement != null && !statement.isClosed()){
                statement.close();
            }
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
    }
}
