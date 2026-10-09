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
                        + "userid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "username VARCHAR(50) NOT NULL UNIQUE, "
                        + "password VARCHAR(15) NOT NULL, "
                        + "role VARCHAR(11) NOT NULL"
                );
            }
            ResultSet survey = meta.getTables(null, null, "survey", null);
            if (!survey.next()) {
                conn.createStatement().executeUpdate(
                        "CREATE TABLE survey ("
                        + "surveyid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "title VARCHAR(50) NOT NULL, "
                        + "user_id INT NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES user(userid))"
                );
            }
            ResultSet question = meta.getTables(null, null, "question", null);
            if (!question.next()) {
                conn.createStatement().executeUpdate(
                        "CREATE TABLE question ("
                        + "questionid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "question VARCHAR(100) NOT NULL UNIQUE, "
                        + "anwser_1 VARCHAR(50) NOT NULL, "
                        + "anwser_2 VARCHAR(50) NOT NULL, "
                        + "anwser_3 VARCHAR(50) NOT NULL, "
                        + "survey_id INT NOT NULL, "
                        + "FOREIGN KEY (survey_id) REFERENCES survey(surveyid))"
                );
            }
            ResultSet respondent_question = meta.getTables(null, null, "respondent_question", null);
            if (!respondent_question.next()) {
                conn.createStatement().executeUpdate(
                        "CREATE TABLE respondent_question ("
                        + "respondent_questionid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                        + "anwser VARCHAR(50) NULL, "
                        + "user_id INT NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES user(userid))"
                        + "question_id INT NOT NULL, "
                        + "FOREIGN KEY (question_id) REFERENCES question(questionid))"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
