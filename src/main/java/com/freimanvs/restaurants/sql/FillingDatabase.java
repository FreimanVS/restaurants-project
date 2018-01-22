package com.freimanvs.restaurants.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Brightsunrise
 * @version 1.0
 */
public class FillingDatabase {
//    private static final String URL = "jdbc:mysql://localhost/restaurants?useSSL=false";
    private static final String URL = "jdbc:h2:~/test";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "pass";

    public static void main(String[] args) {

        try {
//            String driver = "com.mysql.jdbc.Driver";
            String driver = "org.h2.Driver";
            Class.forName(driver);
        } catch (Exception e) {
            System.out.println("An error of a driver");
            return;
        }

        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {

                //CLEAR the SCHEMA
                statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS restaurants;");
                statement.executeUpdate("DROP TABLE IF EXISTS restaurants.user_role;");
                statement.executeUpdate("DROP TABLE IF EXISTS restaurants.user_rest;");
                statement.executeUpdate("DROP TABLE IF EXISTS restaurants.rest_menu;");
                statement.executeUpdate("DROP TABLE IF EXISTS restaurants.user;");
                statement.executeUpdate("DROP TABLE IF EXISTS restaurants.menu;");
                statement.executeUpdate("DROP TABLE IF EXISTS restaurants.rest;");
                statement.executeUpdate("DROP TABLE IF EXISTS restaurants.role;");

                //CREATE TABLES
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS restaurants.rest (\n" +
                        "    \n" +
                        "id BIGINT NOT NULL auto_increment,\n" +
                        "    \n" +
                        "name VARCHAR(255) NOT NULL,\n" +
                        "   \n" +
                        " PRIMARY KEY (id)\n" +
                        "\n" +
                        ");");

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS restaurants.user (\n" +
                        "    \n" +
                        "id BIGINT NOT NULL auto_increment,\n" +
                        "    \n" +
                        "username VARCHAR(255) NOT NULL,\n" +
                        "    \n" +
                        "password VARCHAR(255) NOT NULL,\n" +
                        "\n" +
                        "rest_id BIGINT NULL,\n" +
                        "\n" +
                        "last_vote DATETIME NULL,\n" +
                        "    \n" +
                        "PRIMARY KEY (id),\n" +
                        "  \n" +
                        "CONSTRAINT uniq_user_name_user UNIQUE (username),\n" +
                        "\n" +
                        "CONSTRAINT user_restid_rest_id FOREIGN KEY (rest_id) REFERENCES restaurants.rest(id)\n" +
                        ");");

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS restaurants.role (\n" +
                        "    \n" +
                        "id BIGINT NOT NULL auto_increment,\n" +
                        "    \n" +
                        "name VARCHAR(255) NOT NULL,\n" +
                        "    \n" +
                        "PRIMARY KEY (id),\n" +
                        "  \n" +
                        "CONSTRAINT uniq_name_role UNIQUE (name)\n" +
                        "\n" +
                        ");");

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS restaurants.user_role (\n" +
                        "    \n" +
                        "user_id BIGINT NOT NULL,\n" +
                        "    \n" +
                        "role_id BIGINT NOT NULL,\n" +
                        "    \n" +
                        "PRIMARY KEY (user_id, role_id),\n" +
                        "  \n" +
                        "CONSTRAINT fk_user_name_user_role FOREIGN KEY (user_id) REFERENCES restaurants.user(id),\n" +
                        "  \n" +
                        "CONSTRAINT fk_role_name_user_role FOREIGN KEY (role_id) REFERENCES restaurants.role(id)\n" +
                        "\n" +
                        ");");

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS restaurants.menu (\n" +
                        "    \n" +
                        "id BIGINT NOT NULL auto_increment,\n" +
                        "    \n" +
                        "dish VARCHAR(255) NOT NULL,\n" +
                        "\n" +
                        "price DOUBLE NOT NULL,\n" +
                        "    \n" +
                        "PRIMARY KEY (id),\n" +
                        "  \n" +
                        "CONSTRAINT uniq_dish_price_menu UNIQUE (dish, price)\n" +
                        "\n" +
                        ");");

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS restaurants.rest_menu (\n" +
                        "    \n" +
                        "rest_id BIGINT NOT NULL,\n" +
                        "    \n" +
                        "menu_id BIGINT NOT NULL,\n" +
                        "    \n" +
                        "PRIMARY KEY (rest_id, menu_id),\n" +
                        "  \n" +
                        "CONSTRAINT fk_rest_id_menu FOREIGN KEY (rest_id) REFERENCES restaurants.rest(id),\n" +
                        "  \n" +
                        "CONSTRAINT fk_menu_id_menu FOREIGN KEY (menu_id) REFERENCES restaurants.menu(id)\n" +
                        "\n" +
                        ");");

                statement.executeUpdate("INSERT INTO restaurants.role (name) VALUES ('user');");
                statement.executeUpdate("INSERT INTO restaurants.role (name) VALUES ('admin');");

                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('user', 'user');");
                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('admin', 'admin');");
                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('user2', 'user2');");
                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('user3', 'user3');");
                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('user4', 'user4');");
                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('user5', 'user5');");
                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('user6', 'user6');");
                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('user7', 'user7');");
                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('user8', 'user8');");
                statement.executeUpdate("INSERT INTO restaurants.user (username, password) VALUES ('user9', 'user9');");

                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (1, 1);");
                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (2, 2);");
                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (3, 1);");
                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (4, 1);");
                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (5, 1);");
                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (6, 1);");
                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (7, 1);");
                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (8, 1);");
                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (9, 1);");
                statement.executeUpdate("INSERT INTO restaurants.user_role (user_id, role_id) VALUES (10, 1);");

                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish1', 1);");
                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish2', 2);");
                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish3', 3);");
                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish4', 4);");
                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish5', 5);");
                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish6', 6);");
                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish7', 7);");
                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish8', 8);");
                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish9', 9);");
                statement.executeUpdate("INSERT INTO restaurants.menu (dish, price) VALUES ('dish10', 10);");

                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest1');");
                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest2');");
                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest3');");
                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest4');");
                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest5');");
                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest6');");
                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest7');");
                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest8');");
                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest9');");
                statement.executeUpdate("INSERT INTO restaurants.rest (name) VALUES ('rest10');");



                //GET ALL
                System.out.println("===================== GETTING ALL USERS =======================");
                ResultSet resultSet = statement.executeQuery("SELECT * FROM restaurants.user");
                while (resultSet.next()) {
                    long id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    System.out.println("id = " + id + ", username = " + username + ", password = " + password);
                }

                System.out.println("===================== GETTING ALL ROLES =======================");
                resultSet = statement.executeQuery("SELECT * FROM restaurants.role");
                while (resultSet.next()) {
                    long id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    System.out.println("id = " + id + ", name = " + name);
                }

                System.out.println("===================== GETTING ALL RESTAURANTS =======================");
                resultSet = statement.executeQuery("SELECT * FROM restaurants.rest");
                while (resultSet.next()) {
                    long id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    System.out.println("id = " + id + ", name = " + name);
                }

                System.out.println("===================== GETTING ALL DISHES =======================");
                resultSet = statement.executeQuery("SELECT * FROM restaurants.menu");
                while (resultSet.next()) {
                    long id = resultSet.getInt("id");
                    String dish = resultSet.getString("dish");
                    Double price = resultSet.getDouble("price");
                    System.out.println("id = " + id + ", dish = " + dish + ", price = " + price);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
