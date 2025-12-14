package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Quartier;
import com.brasilburger.repositories.IQuartierRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuartierRepositoryImpl implements IQuartierRepository {

    private static final String SQL_INSERT = "INSERT INTO quartier(nom, zone_id) VALUES (?, ?) RETURNING id";
    private static final String SQL_SELECT_BY_ZONE = "SELECT id, nom, zone_id FROM quartier WHERE zone_id = ? ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nom, zone_id FROM quartier WHERE id = ?";

    @Override
    public Quartier save(Quartier q) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, q.getNom());
            ps.setLong(2, q.getZoneId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) q.setId(rs.getLong(1));
            }
            return q;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Quartier> findAllByZone(Long zoneId) {
        List<Quartier> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ZONE)) {
            ps.setLong(1, zoneId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Quartier q = new Quartier();
                    q.setId(rs.getLong("id"));
                    q.setNom(rs.getString("nom"));
                    q.setZoneId(rs.getLong("zone_id"));
                    list.add(q);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Quartier> findById(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Quartier q = new Quartier();
                    q.setId(rs.getLong("id"));
                    q.setNom(rs.getString("nom"));
                    q.setZoneId(rs.getLong("zone_id"));
                    return Optional.of(q);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
