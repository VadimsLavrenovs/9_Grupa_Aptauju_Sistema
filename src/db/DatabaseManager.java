/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.*;

/**
 *
 * @author Maksims Pļehanovs, Vadims Lavrenovs
 */
public class DatabaseManager {

    private static final String URL = "jdbc:derby:database/appdb;create=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void init() {
        try (Connection conn = getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet user = meta.getTables(null, null, "USER", null);
            if (!user.next()) {
                conn.createStatement().executeUpdate(
                        "CREATE TABLE user ("
                        + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "username VARCHAR(50) NOT NULL UNIQUE, "
                        + "password_hash VARCHAR(255) NOT NULL, "
                        + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
                );
            }
            ResultSet survey = meta.getTables(null, null, "survey", null);
            if (!survey.next()) {
                conn.createStatement().executeUpdate(
                        "CREATE TABLE survey ("
                        + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "title VARCHAR(100) NOT NULL, "
                        + "description VARCHAR(255), "
                        + "user_id INT NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES users(id))"
                );
            }
            ResultSet question = meta.getTables(null, null, "question", null);
            if (!question.next()) {
                conn.createStatement().executeUpdate(
                        "CREATE TABLE users ("
                        + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "username VARCHAR(50) NOT NULL UNIQUE, "
                        + "password_hash VARCHAR(255) NOT NULL, "
                        + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
                );
            }
            ResultSet respondent_question = meta.getTables(null, null, "respondent_question", null);
            if (!respondent_question.next()) {
                conn.createStatement().executeUpdate(
                        "CREATE TABLE hobbies ("
                        + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "title VARCHAR(100) NOT NULL, "
                        + "description VARCHAR(255), "
                        + "user_id INT NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES users(id))"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
