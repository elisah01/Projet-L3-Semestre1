package com.brasilburger.core.db;

import java.sql.*;

public class PostgresDB {

    private Connection conn;
    private PreparedStatement psmt;

    private final String URL =
        "jdbc:postgresql://ep-long-water-ah7g9bvn-pooler.c-3.us-east-1.aws.neon.tech/neondb?sslmode=require&channel_binding=require";

    private final String USER = "neondb_owner";
    private final String PASSWORD = "npg_qXdMT0zvZ7cg";

    public PreparedStatement getPsmt() {
        return psmt;
    }

    public void ouvrirConnexion() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Erreur connexion BD : " + e.getMessage());
        }
    }

    public void preparerRequete(String sql) {
        try {
            psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            System.out.println("Erreur préparation requête : " + e.getMessage());
        }
    }

    public int executeMaj() {
        try {
            return psmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur MAJ : " + e.getMessage());
        }
        return 0;
    }

    public ResultSet executeSelect() {
        try {
            return psmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erreur SELECT : " + e.getMessage());
        }
        return null;
    }

    public int getLastInsertedId() {
        try {
            ResultSet rs = psmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Erreur ID généré : " + e.getMessage());
        }
        return 0;
    }

    public void fermerConnexion() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("Erreur fermeture : " + e.getMessage());
        }
    }
}
