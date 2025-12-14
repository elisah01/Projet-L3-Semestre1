package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Client;
import com.brasilburger.repositories.IClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryImpl implements IClientRepository {

    private static final String SQL_INSERT = "INSERT INTO client(nom, prenom, telephone, email, password) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String SQL_SELECT_ALL = "SELECT id, nom, prenom, telephone, email, created_at FROM client ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nom, prenom, telephone, email, created_at FROM client WHERE id = ?";
    private static final String SQL_SELECT_BY_TEL = "SELECT id, nom, prenom, telephone, email, created_at FROM client WHERE telephone = ?";

    @Override
    public Client save(Client client) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getTelephone());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getPassword());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) client.setId(rs.getLong(1));
            }
            return client;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur insert client", e);
        }
    }

    @Override
    public List<Client> findAll() {
        List<Client> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getLong("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setTelephone(rs.getString("telephone"));
                c.setEmail(rs.getString("email"));
                list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Client> findById(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Client c = new Client();
                    c.setId(rs.getLong("id"));
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString("prenom"));
                    c.setTelephone(rs.getString("telephone"));
                    c.setEmail(rs.getString("email"));
                    return Optional.of(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> findByTelephone(String telephone) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_TEL)) {
            ps.setString(1, telephone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Client c = new Client();
                    c.setId(rs.getLong("id"));
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString("prenom"));
                    c.setTelephone(rs.getString("telephone"));
                    c.setEmail(rs.getString("email"));
                    return Optional.of(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
