package com.freimanvs.restaurants.utils;

import com.freimanvs.restaurants.entity.Menu;
import com.freimanvs.restaurants.entity.Restaurant;
import com.freimanvs.restaurants.entity.Role;
import com.freimanvs.restaurants.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class RestoreData {
    //    private static final String URL = "jdbc:mysql://localhost/restaurants?useSSL=false";
    private static final String URL = "jdbc:h2:~/test";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "pass";
    private static final File TABLES = new File("./pre_sql.txt");
    private static final File FILESQL_JPA = new File("./jpa.txt");

    private void initFiles() {

        FileManager.writeToFile(
                "CREATE SCHEMA IF NOT EXISTS restaurants;\n" +
                        "\n" +
                        "DROP TABLE IF EXISTS restaurants.user_rest;\n" +
                        "DROP TABLE IF EXISTS restaurants.user_role;\n" +
                        "DROP TABLE IF EXISTS restaurants.rest_menu;\n" +
                        "DROP TABLE IF EXISTS restaurants.user;\n" +
                        "DROP TABLE IF EXISTS restaurants.menu;\n" +
                        "DROP TABLE IF EXISTS restaurants.rest;\n" +
                        "DROP TABLE IF EXISTS restaurants.role;\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS restaurants.rest (\n" +
                        "    \n" +
                        "id BIGINT NOT NULL auto_increment,\n" +
                        "    \n" +
                        "name VARCHAR(255) NOT NULL,\n" +
                        "   \n" +
                        " PRIMARY KEY (id)\n" +
                        "\n" +
                        ")" +
//                        "\n" +
//                        "\n" +
//                        "ENGINE = InnoDB\n" +
//                        "DEFAULT \n" +
//                        "CHARACTER SET = utf8\n" +
//                        "\n" +
//                        "COLLATE = utf8_general_ci" +
                        ";\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS restaurants.user (\n" +
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
                        ")" +
//                        "\n" +
//                        "\n" +
//                        "ENGINE = InnoDB\n" +
//                        "\n" +
//                        "DEFAULT CHARACTER SET = utf8\n" +
//                        "\n" +
//                        "COLLATE = utf8_general_ci" +
                        ";\n" +
                        "\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS restaurants.role (\n" +
                        "    \n" +
                        "id BIGINT NOT NULL auto_increment,\n" +
                        "    \n" +
                        "name VARCHAR(255) NOT NULL,\n" +
                        "    \n" +
                        "PRIMARY KEY (id),\n" +
                        "  \n" +
                        "CONSTRAINT uniq_name_role UNIQUE (name)\n" +
                        "\n" +
                        ")" +
//                        "\n" +
//                        "\n" +
//                        "ENGINE = InnoDB\n" +
//                        "\n" +
//                        "DEFAULT CHARACTER SET = utf8\n" +
//                        "\n" +
//                        "COLLATE = utf8_general_ci" +
                        ";\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS restaurants.user_role (\n" +
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
                        ")" +
//                        "\n" +
//                        "\n" +
//                        "ENGINE = InnoDB\n" +
//                        "\n" +
//                        "DEFAULT CHARACTER SET = utf8\n" +
//                        "\n" +
//                        "COLLATE = utf8_general_ci" +
                        ";\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS restaurants.menu (\n" +
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
                        ")" +
//                        "\n" +
//                        "\n" +
//                        "ENGINE = InnoDB\n" +
//                        "\n" +
//                        "DEFAULT CHARACTER SET = utf8\n" +
//                        "\n" +
//                        "COLLATE = utf8_general_ci" +
                        ";\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS restaurants.rest_menu (\n" +
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
                        ")" +
//                        "\n" +
//                        "\n" +
//                        "ENGINE = InnoDB\n" +
//                        "\n" +
//                        "DEFAULT CHARACTER SET = utf8\n" +
//                        "\n" +
//                        "COLLATE = utf8_general_ci" +
                        ";", TABLES);
        FileManager.writeToFile("Role user\n" +
                "Role admin\n" +
                "User user user\n" +
                "User admin admin\n" +
                "User user2 password2\n" +
                "User user3 password3\n" +
                "User user4 password4\n" +
                "User user5 password5\n" +
                "User user6 password6\n" +
                "User user7 password7\n" +
                "User user8 password8\n" +
                "User user9 password9\n" +
                "Menu dish1 1,5\n" +
                "Menu dish2 2,5\n" +
                "Menu dish3 3,5\n" +
                "Menu dish4 4,5\n" +
                "Menu dish5 5,5\n" +
                "Menu dish6 6,5\n" +
                "Menu dish7 7,5\n" +
                "Menu dish8 8,5\n" +
                "Menu dish9 9,5\n" +
                "Restaurant rest1\n" +
                "Restaurant rest2\n" +
                "Restaurant rest3\n" +
                "Restaurant rest4\n" +
                "Restaurant rest5\n" +
                "Restaurant rest6\n" +
                "Restaurant rest7\n" +
                "Restaurant rest8\n" +
                "Restaurant rest9", FILESQL_JPA);
    }

    public void restoreJPA() {
        initFiles();
        System.out.println("FILLING DATABASE WITH DATA FROM pre_sql.txt," +
                "sql.txt and jpa.txt files USING JDBC AND JPA...\n");

        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session currentSession = sessionFactory.openSession();


        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement();
             Scanner data = new Scanner(FILESQL_JPA).useDelimiter("[\\s]+");
             Scanner tables = new Scanner(TABLES).useDelimiter(";[\\s]*")) {

            System.out.println("CREATING TABLES BY JDPC...\n");
            while (tables.hasNext()) {
                String sql = tables.next();
                System.out.println(sql);
                statement.executeUpdate(sql);
            }
            System.out.println("========== THE TABLES ARE CREATED SUCCESSFULLY! ============\r\n");

            System.out.println("\r\nFILLING TABLES WITH DATA BY JPA...\r\n");

            while (data.hasNext()) {
                String entity = data.next();

                switch (entity) {
                    case "Role": {
                        String name = data.next();
                        Role role = new Role();
                        role.setName(name);

                        Transaction transaction = currentSession.beginTransaction();
                        currentSession.save(role);
                        transaction.commit();
                        System.out.println(entity + " has been added");
                        break;
                    }
                    case "User": {
                        String username = data.next();
                        String password = data.next();
                        User user = new User();
                        user.setUsername(username);
                        user.setPassword(password);

                        Role role = username.equals("admin")
                                ? currentSession.get(Role.class, 2L)
                                : currentSession.get(Role.class, 1L);
                        Transaction transaction = currentSession.beginTransaction();
                        user.getRoles().add(role);

                        currentSession.save(user);
                        transaction.commit();
                        System.out.println(entity + " has been added");
                        break;
                    }
                    case "Menu": {
                        String dish = data.next();
                        double price = data.nextDouble();
                        Menu menu = new Menu();
                        menu.setDish(dish);
                        menu.setPrice(price);

                        Transaction transaction = currentSession.beginTransaction();
                        currentSession.save(menu);
                        transaction.commit();
                        System.out.println(entity + " has been added");
                        break;
                    }
                    case "Restaurant": {
                        String name = data.next();
                        Restaurant restaurant = new Restaurant();
                        restaurant.setName(name);

                        Transaction transaction = currentSession.beginTransaction();
                        currentSession.save(restaurant);
                        transaction.commit();
                        System.out.println(entity + " has been added");
                        break;
                    }
                    default:
                        System.out.println("A wrong entity: " + entity);
                        break;
                }
            }
            System.out.println("========== TABLES ARE FILLED SUCCESSFULLY! ============\r\n");
            System.out.println("THE SCHEMA 'restaurants' IS READY TO USE!\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            currentSession.close();
            sessionFactory.close();
        }
    }

    public static void main(String[] args) {
        RestoreData restoreData = new RestoreData();

        restoreData.restoreJPA();
    }
}
