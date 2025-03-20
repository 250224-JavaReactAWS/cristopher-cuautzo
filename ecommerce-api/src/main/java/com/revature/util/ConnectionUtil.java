package com.revature.util;

import io.javalin.http.NotFoundResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static Connection conn = null;
    private ConnectionUtil() {}

    public static Connection getConnection() {
        try{
            if (conn != null && !conn.isClosed()) return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        String url = "";
        String username = "";
        String password = "";

        Properties props = new Properties();

        try{
            props.load(new FileReader("src/main/resources/application.properties"));

            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");

            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not establish connection!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Could not find the file!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not establish connection!");
        }

        return conn;
    }
}
