package com.sudy.projeto.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
