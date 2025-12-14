package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Commande;
import com.brasilburger.entities.CommandeBurger;
import com.brasilburger.entities.CommandeMenu;
import com.brasilburger.entities.CommandeComplement;
import com.brasilburger.repositories.ICommandeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandeRepositoryImpl implements ICommandeRepository {

    private static final String SQL_INSERT_COMMANDE = "INSERT INTO commande(client_id, zone_id, mode_consommation, etat, total) VALUES (?, ?, ?, ?, ?) RETURNING id, date_commande";
    private static final String SQL_INSERT_COMMANDE_BURGER = "INSERT INTO commande_burger(commande_id, burger_id, quantite) VALUES (?, ?, ?)";
    private static final String SQL_INSERT_COMMANDE_MENU = "INSERT INTO commande_menu(commande_id, menu_id, quantite) VALUES (?, ?, ?)";
    private static final String SQL_INSERT_COMMANDE_COMPLEMENT = "INSERT INTO commande_complement(commande_id, complement_id, quantite) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT id, client_id, zone_id, mode_consommation, etat, total, date_commande FROM commande ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, client_id, zone_id, mode_consommation, etat, total, date_commande FROM commande WHERE id = ?";
    private static final String SQL_SELECT_BY_CLIENT = "SELECT id, client_id, zone_id, mode_consommation, etat, total, date_commande FROM commande WHERE client_id = ? ORDER BY date_commande DESC";
    private static final String SQL_UPDATE_ETAT = "UPDATE commande SET etat = ? WHERE id = ?";

    @Override
    public Commande save(Commande commande) {
        // Save commande and items in a single transaction
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_COMMANDE)) {
                ps.setObject(1, commande.getClientId());
                if (commande.getZoneId() != null) ps.setObject(2, commande.getZoneId()); else ps.setNull(2, Types.BIGINT);
                ps.setString(3, commande.getModeConsommation().name());
                ps.setString(4, commande.getEtat().name());
                ps.setDouble(5, commande.getTotal());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        commande.setId(rs.getLong("id"));
                        java.sql.Timestamp ts = rs.getTimestamp("date_commande");
                        if (ts != null) commande.setDateCommande(ts.toLocalDateTime());
                    }
                }
            }

            // insert burgers
            if (commande.getBurgers() != null) {
                try (PreparedStatement psBurger = conn.prepareStatement(SQL_INSERT_COMMANDE_BURGER)) {
                    for (CommandeBurger cb : commande.getBurgers()) {
                        psBurger.setLong(1, commande.getId());
                        psBurger.setLong(2, cb.getBurgerId());
                        psBurger.setInt(3, cb.getQuantite());
                        psBurger.addBatch();
                    }
                    psBurger.executeBatch();
                }
            }

            // insert menus
            if (commande.getMenus() != null) {
                try (PreparedStatement psMenu = conn.prepareStatement(SQL_INSERT_COMMANDE_MENU)) {
                    for (CommandeMenu cm : commande.getMenus()) {
                        psMenu.setLong(1, commande.getId());
                        psMenu.setLong(2, cm.getMenuId());
                        psMenu.setInt(3, cm.getQuantite());
                        psMenu.addBatch();
                    }
                    psMenu.executeBatch();
                }
            }

            // insert complements
            if (commande.getComplements() != null) {
                try (PreparedStatement psComp = conn.prepareStatement(SQL_INSERT_COMMANDE_COMPLEMENT)) {
                    for (CommandeComplement cc : commande.getComplements()) {
                        psComp.setLong(1, commande.getId());
                        psComp.setLong(2, cc.getComplementId());
                        psComp.setInt(3, cc.getQuantite());
                        psComp.addBatch();
                    }
                    psComp.executeBatch();
                }
            }

            conn.commit();
            conn.setAutoCommit(true);
            return commande;
        } catch (SQLException ex) {
            throw new RuntimeException("Erreur lors de la création de la commande", ex);
        }
    }

    @Override
    public List<Commande> findAll() {
        List<Commande> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Commande c = mapRow(rs);
                list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Commande> findById(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Commande> findByClientId(Long clientId) {
        List<Commande> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_CLIENT)) {
            ps.setLong(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void updateEtat(Long commandeId, String etat) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_ETAT)) {
            ps.setString(1, etat);
            ps.setLong(2, commandeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Commande mapRow(ResultSet rs) throws SQLException {
    Commande c = new Commande();
    c.setId(rs.getLong("id"));
    c.setClientId((Long) rs.getObject("client_id"));
    c.setZoneId((Long) rs.getObject("zone_id"));
    c.setModeConsommation(com.brasilburger.entities.enums.ModeConsommation.valueOf(rs.getString("mode_consommation")));
    c.setEtat(com.brasilburger.entities.enums.EtatCommande.valueOf(rs.getString("etat")));
    c.setTotal(rs.getDouble("total"));

    java.sql.Timestamp ts = rs.getTimestamp("date_commande");
    if (ts != null) {
        c.setDateCommande(ts.toLocalDateTime());
    }
    return c;
    }
}
