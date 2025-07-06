package com.maternacare.service;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnector {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/maternadb?useSSL=false&serverTimezone=UTC";
    private static final String dbUser = "root";
    private static final String dbPassword = "admin123";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            System.out.println("Database Connected Successfully...");
        } catch(ClassNotFoundException e){
            System.out.println("Mysql JDBC Driver not found...");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database Connection Failed...");
            e.printStackTrace();
        }
        return connection;
    }
}