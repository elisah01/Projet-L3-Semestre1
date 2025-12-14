package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Zone;
import com.brasilburger.repositories.IZoneRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ZoneRepositoryImpl implements IZoneRepository {

    private static final String SQL_INSERT = "INSERT INTO zone(nom, prix) VALUES (?, ?) RETURNING id";
    private static final String SQL_SELECT_ALL = "SELECT id, nom, prix FROM zone ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nom, prix FROM zone WHERE id = ?";

    @Override
    public Zone save(Zone zone) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, zone.getNom());
            ps.setDouble(2, zone.getPrix());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) zone.setId(rs.getLong(1));
            }
            return zone;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Zone> findAll() {
        List<Zone> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Zone z = new Zone();
                z.setId(rs.getLong("id"));
                z.setNom(rs.getString("nom"));
                z.setPrix(rs.getDouble("prix"));
                list.add(z);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Zone> findById(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Zone z = new Zone();
                    z.setId(rs.getLong("id"));
                    z.setNom(rs.getString("nom"));
                    z.setPrix(rs.getDouble("prix"));
                    return Optional.of(z);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
