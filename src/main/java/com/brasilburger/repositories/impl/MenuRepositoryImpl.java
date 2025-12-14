package com.brasilburger.repositories.impl;

import com.brasilburger.config.DBConnection;
import com.brasilburger.entities.Menu;
import com.brasilburger.repositories.IMenuRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuRepositoryImpl implements IMenuRepository {

    private static final String SQL_INSERT = "INSERT INTO menu(nom, image_url, archive) VALUES (?, ?, false) RETURNING id";
    private static final String SQL_SELECT_ALL = "SELECT id, nom, image_url, archive FROM menu WHERE archive=false ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT id, nom, image_url, archive FROM menu WHERE id = ?";
    private static final String SQL_ARCHIVE = "UPDATE menu SET archive = true WHERE id = ?";

    private static final String SQL_ADD_BURGER = "INSERT INTO menu_burger(menu_id, burger_id) VALUES (?, ?)";
    private static final String SQL_REMOVE_BURGER = "DELETE FROM menu_burger WHERE menu_id = ? AND burger_id = ?";
    private static final String SQL_ADD_COMPLEMENT = "INSERT INTO menu_complement(menu_id, complement_id) VALUES (?, ?)";
    private static final String SQL_REMOVE_COMPLEMENT = "DELETE FROM menu_complement WHERE menu_id = ? AND complement_id = ?";

    // somme des prix des burgers et complements d'un menu
    private static final String SQL_CALC_MENU_PRICE =
        "SELECT COALESCE(SUM(b.prix * mbcnt.q),0) + COALESCE(SUM(c.prix * mccnt.q),0) AS total " +
        "FROM menu m " +
        "LEFT JOIN (SELECT burger_id, COUNT(*) as q FROM menu_burger WHERE menu_id = ? GROUP BY burger_id) mbcnt ON true " +
        "LEFT JOIN burger b ON b.id = mbcnt.burger_id " +
        "LEFT JOIN (SELECT complement_id, COUNT(*) as q FROM menu_complement WHERE menu_id = ? GROUP BY complement_id) mccnt ON true " +
        "LEFT JOIN complement c ON c.id = mccnt.complement_id " +
        "WHERE m.id = ?";

    @Override
    public Menu save(Menu menu) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, menu.getNom());
            ps.setString(2, menu.getImageUrl());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) menu.setId(rs.getLong(1));
            }
            return menu;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Menu m = new Menu();
                m.setId(rs.getLong("id"));
                m.setNom(rs.getString("nom"));
                m.setImageUrl(rs.getString("image_url"));
                m.setArchive(rs.getBoolean("archive"));
                list.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Menu> findById(Long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Menu m = new Menu();
                    m.setId(rs.getLong("id"));
                    m.setNom(rs.getString("nom"));
                    m.setImageUrl(rs.getString("image_url"));
                    m.setArchive(rs.getBoolean("archive"));
                    return Optional.of(m);
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

    @Override
    public void addBurgerToMenu(Long menuId, Long burgerId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ADD_BURGER)) {
            ps.setLong(1, menuId);
            ps.setLong(2, burgerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeBurgerFromMenu(Long menuId, Long burgerId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_REMOVE_BURGER)) {
            ps.setLong(1, menuId);
            ps.setLong(2, burgerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addComplementToMenu(Long menuId, Long complementId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ADD_COMPLEMENT)) {
            ps.setLong(1, menuId);
            ps.setLong(2, complementId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeComplementFromMenu(Long menuId, Long complementId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_REMOVE_COMPLEMENT)) {
            ps.setLong(1, menuId);
            ps.setLong(2, complementId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double calculateMenuPrice(Long menuId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CALC_MENU_PRICE)) {
            ps.setLong(1, menuId);
            ps.setLong(2, menuId);
            ps.setLong(3, menuId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calcul prix menu", e);
        }
        return 0.0;
    }
}
