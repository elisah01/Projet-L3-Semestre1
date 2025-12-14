package com.brasilburger.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        "jdbc:postgresql://ep-long-water-ah7g9bvn-pooler.c-3.us-east-1.aws.neon.tech/neondb";

    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_qXdMT0zvZ7cg";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (Exception e) {
            System.out.println("Erreur connexion BD : " + e.getMessage());
        }
        return connection;
    }
}
