package com.freimanvs.restaurants.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author Brightsunrise
 * @version 1.0
 */
public class FillingDatabase {
    private static final String URL = "jdbc:mysql://localhost/restaurants?useSSL=false";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "pass";

    public static void main(String[] args) {

        try {
            String driver = "com.mysql.jdbc.Driver";
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

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
