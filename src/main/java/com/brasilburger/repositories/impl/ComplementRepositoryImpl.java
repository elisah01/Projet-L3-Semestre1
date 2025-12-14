package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Complement;
import com.brasilburger.repositories.IComplementRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComplementRepositoryImpl implements IComplementRepository {

    private static final String SQL_INSERT = "INSERT INTO complement(nom, prix, image_url, archive) VALUES (?, ?, ?, false) RETURNING id";
    private static final String SQL_SELECT_ALL = "SELECT id, nom, prix, image_url, archive FROM complement WHERE archive=false ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nom, prix, image_url, archive FROM complement WHERE id = ?";
    private static final String SQL_ARCHIVE = "UPDATE complement SET archive = true WHERE id = ?";

    @Override
    public Complement save(Complement complement) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, complement.getNom());
            ps.setDouble(2, complement.getPrix());
            ps.setString(3, complement.getImageUrl());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) complement.setId(rs.getLong(1));
            rs.close();
            return complement;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur insert complement", e);
        }
    }

    @Override
    public List<Complement> findAll() {
        List<Complement> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Complement c = new Complement();
                c.setId(rs.getLong("id"));
                c.setNom(rs.getString("nom"));
                c.setPrix(rs.getDouble("prix"));
                c.setImageUrl(rs.getString("image_url"));
                c.setArchive(rs.getBoolean("archive"));
                list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Complement> findById(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Complement c = new Complement();
                    c.setId(rs.getLong("id"));
                    c.setNom(rs.getString("nom"));
                    c.setPrix(rs.getDouble("prix"));
                    c.setImageUrl(rs.getString("image_url"));
                    c.setArchive(rs.getBoolean("archive"));
                    return Optional.of(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void archive(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ARCHIVE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
