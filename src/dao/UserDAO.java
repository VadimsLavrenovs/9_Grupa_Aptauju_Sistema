/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DatabaseManager;
import java.sql.*;
import util.passwordUtil;
/**
 *
 * @author Maksims Plehanovs, Vadims Lavrenovs
 */
public class UserDAO {

    public boolean register(String username, String password) {
        String sql
                = "INSERT INTO users(username, password_hash) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordUtil.hash(password));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public int login(String username, String password) {
        String sql
                = "SELECT id FROM users "
                + "WHERE username=? AND password_hash=?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordUtil.hash(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement ps
                = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
