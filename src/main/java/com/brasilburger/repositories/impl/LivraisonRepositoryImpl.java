package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Livraison;
import com.brasilburger.repositories.ILivraisonRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivraisonRepositoryImpl implements ILivraisonRepository {

    private static final String SQL_INSERT = "INSERT INTO livraison(date, livreur_id, zone_id) VALUES (CURRENT_TIMESTAMP, ?, ?) RETURNING id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, date, livreur_id, zone_id FROM livraison WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, date, livreur_id, zone_id FROM livraison ORDER BY id";
    private static final String SQL_ADD_COMMANDE = "INSERT INTO livraison_commande(livraison_id, commande_id) VALUES (?, ?)";

    @Override
    public Livraison save(Livraison l) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            if (l.getLivreurId() != null) ps.setLong(1, l.getLivreurId()); else ps.setNull(1, Types.BIGINT);
            if (l.getZoneId() != null) ps.setLong(2, l.getZoneId()); else ps.setNull(2, Types.BIGINT);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) l.setId(rs.getLong(1));
            }
            return l;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Livraison> findById(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Livraison l = new Livraison();
                    l.setId(rs.getLong("id"));
                    java.sql.Timestamp ts = rs.getTimestamp("date");
                    if (ts != null) l.setDate(ts.toLocalDateTime());                    
                    l.setLivreurId((Long) rs.getObject("livreur_id"));
                    l.setZoneId((Long) rs.getObject("zone_id"));
                    return Optional.of(l);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Livraison> findAll() {
        List<Livraison> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Livraison l = new Livraison();
                l.setId(rs.getLong("id"));
                java.sql.Timestamp ts = rs.getTimestamp("date");
                if (ts != null) l.setDate(ts.toLocalDateTime());
                l.setLivreurId((Long) rs.getObject("livreur_id"));
                l.setZoneId((Long) rs.getObject("zone_id"));
                list.add(l);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void addCommandeToLivraison(Long livraisonId, Long commandeId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ADD_COMMANDE)) {
            ps.setLong(1, livraisonId);
            ps.setLong(2, commandeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
