package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Paiement;
import com.brasilburger.repositories.IPaiementRepository;

import java.sql.*;
import java.util.Optional;

public class PaiementRepositoryImpl implements IPaiementRepository {

    private static final String SQL_INSERT = "INSERT INTO paiement(commande_id, montant, date_paiement, mode) VALUES (?, ?, CURRENT_TIMESTAMP, ?) RETURNING id";
    private static final String SQL_SELECT_BY_COMMANDE = "SELECT id, commande_id, montant, date_paiement, mode FROM paiement WHERE commande_id = ?";

    @Override
    public Paiement save(Paiement paiement) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setLong(1, paiement.getCommandeId());
            ps.setDouble(2, paiement.getMontant());
            ps.setString(3, paiement.getMode().name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) paiement.setId(rs.getLong(1));
            }
            return paiement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Paiement> findByCommandeId(Long commandeId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_COMMANDE)) {
            ps.setLong(1, commandeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paiement p = new Paiement();
                    p.setId(rs.getLong("id"));
                    p.setCommandeId(rs.getLong("commande_id"));
                    p.setMontant(rs.getDouble("montant"));
                    java.sql.Timestamp ts = rs.getTimestamp("date_paiement");
                    if (ts != null) p.setDatePaiement(ts.toLocalDateTime());
                    p.setMode(com.brasilburger.entities.enums.ModePaiement.valueOf(rs.getString("mode")));
                    return Optional.of(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
