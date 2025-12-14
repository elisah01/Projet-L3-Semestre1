package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Burger;
import com.brasilburger.repositories.IBurgerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BurgerRepositoryImpl implements IBurgerRepository {

    private static final String SQL_INSERT = "INSERT INTO burger(nom, prix, image_url, archive) VALUES (?, ?, ?, false) RETURNING id";
    private static final String SQL_SELECT_ALL = "SELECT id, nom, prix, image_url, archive, created_at FROM burger WHERE archive=false ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nom, prix, image_url, archive, created_at FROM burger WHERE id = ?";
    private static final String SQL_ARCHIVE = "UPDATE burger SET archive = true WHERE id = ?";

    @Override
    public Burger save(Burger burger) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, burger.getNom());
            ps.setDouble(2, burger.getPrix());
            ps.setString(3, burger.getImageUrl());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                burger.setId(rs.getLong(1));
            }
            rs.close();
            return burger;
        } catch (SQLException ex) {
            throw new RuntimeException("Erreur lors de l'insertion du burger", ex);
        }
    }

    @Override
    public List<Burger> findAll() {
        List<Burger> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Burger b = mapRow(rs);
                list.add(b);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erreur lors du select burgers", ex);
        }
        return list;
    }

    @Override
    public Optional<Burger> findById(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erreur lors de findById burger", ex);
        }
        return Optional.empty();
    }

    @Override
    public void archive(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ARCHIVE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erreur lors de l'archivage du burger", ex);
        }
    }

    private Burger mapRow(ResultSet rs) throws SQLException {
        Burger b = new Burger();
        b.setId(rs.getLong("id"));
        b.setNom(rs.getString("nom"));
        b.setPrix(rs.getDouble("prix"));
        b.setImageUrl(rs.getString("image_url"));
        // assume archive getter/setter exist
        b.setArchive(rs.getBoolean("archive"));
        return b;
    }
}
