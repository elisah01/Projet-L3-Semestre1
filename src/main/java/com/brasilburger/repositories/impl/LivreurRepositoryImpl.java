package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Livreur;
import com.brasilburger.repositories.ILivreurRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivreurRepositoryImpl implements ILivreurRepository {

    private static final String SQL_INSERT = "INSERT INTO livreur(nom, prenom, telephone) VALUES (?, ?, ?) RETURNING id";
    private static final String SQL_SELECT_ALL = "SELECT id, nom, prenom, telephone FROM livreur ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nom, prenom, telephone FROM livreur WHERE id = ?";

    @Override
    public Livreur save(Livreur l) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, l.getNom());
            ps.setString(2, l.getPrenom());
            ps.setString(3, l.getTelephone());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) l.setId(rs.getLong(1));
            }
            return l;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Livreur> findAll() {
        List<Livreur> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Livreur l = new Livreur();
                l.setId(rs.getLong("id"));
                l.setNom(rs.getString("nom"));
                l.setPrenom(rs.getString("prenom"));
                l.setTelephone(rs.getString("telephone"));
                list.add(l);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Livreur> findById(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Livreur l = new Livreur();
                    l.setId(rs.getLong("id"));
                    l.setNom(rs.getString("nom"));
                    l.setPrenom(rs.getString("prenom"));
                    l.setTelephone(rs.getString("telephone"));
                    return Optional.of(l);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
