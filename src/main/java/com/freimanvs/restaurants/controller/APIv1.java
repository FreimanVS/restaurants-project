package com.freimanvs.restaurants.controller;

import com.freimanvs.restaurants.entity.Menu;
import com.freimanvs.restaurants.entity.Restaurant;
import com.freimanvs.restaurants.entity.Role;
import com.freimanvs.restaurants.entity.User;
import com.freimanvs.restaurants.service.TheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Freiman V.S.
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/v1")
public class APIv1 {

    @Autowired
    private TheService<Restaurant> restService;

    @Autowired
    private TheService<Menu> menuService;

    @Autowired
    private TheService<User> userService;

    @Autowired
    private TheService<Role> roleService;


    //get all restaurants
    @RequestMapping(value = "/restaurants", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public ResponseEntity<?> listRests() {
        List<Restaurant> rests = restService.getList();
        return ResponseEntity.status(200).body(rests);
    }

    //update a restaurant
    @PutMapping(value = "/restaurants/{id}", consumes = "application/json")
    public ResponseEntity<?> updateRest(@PathVariable("id") long rest_id,
                                        @RequestBody Restaurant rest,
                                        HttpServletRequest request) {

        String auth = request.getHeader("Authorization");
        if (auth == null) {
            return ResponseEntity.status(404).body("You have to login by Basic Auth");
        }

        String json = request.getHeader("Content-Type");
        if (json == null || !json.equals("application/json")) {
            return ResponseEntity.status(404).body("You have to send JSON");
        }

        boolean idRestExist = restService.getList().stream().anyMatch(r -> r.getId() == rest_id);
        if (!idRestExist) {
            return ResponseEntity.status(404).body("There is no a restaurant with this ID");
        }

        if (!rest.getUsers().isEmpty()) {
            return ResponseEntity.status(404).body("You can only add a restaurant from a user, not vice versa");
        }

        boolean idExist = rest.getMenu().stream().allMatch(m -> m.getId() != 0L);
        if (!idExist) {
            return ResponseEntity.status(404).body("You must specify ID of each menu");
        }

        //checking if all of the menu from order are exist in database
        List<Menu> menuListFromDB = menuService.getList();
        Map<Long, Menu> mapFromDB = new HashMap<>();
        menuListFromDB.stream().forEach(m -> mapFromDB.put(m.getId(), m));


        Set<Long> longs = rest.getMenu().stream().map(Menu::getId).collect(Collectors.toSet());
        boolean ok = longs.stream().allMatch(mapFromDB::containsKey);
        if (!ok) {
            return ResponseEntity.status(404).body("Not all of the dishes exist in database");
        }

        Set<Menu> requestSetMenu = longs.stream().map(mapFromDB::get).collect(Collectors.toSet());
        rest.setMenu(requestSetMenu);


        restService.updateById(rest_id, rest);

        return ResponseEntity.status(200).body("Done!");
    }

    //update menu
    @PutMapping(value = "/menu/{id}", consumes = "application/json")
    public ResponseEntity<?> updateMenu(@PathVariable("id") long menu_id,
                                        @RequestBody Menu menu,
                                        HttpServletRequest request) {

        String auth = request.getHeader("Authorization");
        if (auth == null) {
            return ResponseEntity.status(404).body("You have to login by Basic Auth");
        }

        String json = request.getHeader("Content-Type");
        if (json == null || !json.equals("application/json")) {
            return ResponseEntity.status(404).body("You have to send JSON");
        }

        if (menu.getDish() == null || menu.getPrice() == 0.0) {
            return ResponseEntity.status(404).body("You need to fill 'dish' and 'price'");
        }

        boolean idExist = menuService.getList().stream().anyMatch(m -> m.getId() == menu_id);
        if (!idExist) {
            return ResponseEntity.status(404).body("There is no menu with this ID");
        }

        if (!menu.getRests().isEmpty()) {
            return ResponseEntity.status(404).body("You can add menu only from a restaurant, not vice versa");
        }

        boolean exists = menuService.getList().stream().anyMatch(m -> m.getDish().equals(menu.getDish())
                && m.getPrice() == menu.getPrice());
        if (exists) {
            return ResponseEntity.status(404).body("Such dish with the same price already exists");
        }

        menuService.updateById(menu_id, menu);

        return ResponseEntity.status(200).body("Done!");
    }

    //add a new restaurant
    @RequestMapping(value = "/restaurants", method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> saveRest(@RequestBody Restaurant restaurant,
                                      HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null) {
            return ResponseEntity.status(404).body("You have to login by Basic Auth");
        }

        String json = request.getHeader("Content-Type");
        if (json == null || !json.equals("application/json")) {
            return ResponseEntity.status(404).body("You have to send JSON");
        }

        if (!restaurant.getMenu().isEmpty()) {
            return ResponseEntity.status(404).body("You need to create a restaurant and only then choose menu");
        }

        if (restaurant.getName() == null) {
            return ResponseEntity.status(404).body("You need to fill 'name'");
        }

        boolean exists = restService.getList().stream().anyMatch(r -> r.getName().equals(restaurant.getName()));
        if (exists) {
            return ResponseEntity.status(404).body("Such restaurant already exists");
        }

        long id = restService.add(restaurant);

        return ResponseEntity.status(201).body("The restaurant has been added");
    }

    //add new menu
    @RequestMapping(value = "/menu", method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> saveMenu(@RequestBody Menu menu,
                                      HttpServletRequest request) {

        String auth = request.getHeader("Authorization");
        if (auth == null) {
            return ResponseEntity.status(404).body("You have to login by Basic Auth");
        }

        String json = request.getHeader("Content-Type");
        if (json == null || !json.equals("application/json")) {
            return ResponseEntity.status(404).body("You have to send JSON");
        }

        if (menu.getDish() == null || menu.getPrice() == 0.0) {
            return ResponseEntity.status(404).body("You need to fill 'dish' and 'price'");
        }

        boolean exists = menuService.getList().stream().anyMatch(m -> m.getDish().equals(menu.getDish())
                && m.getPrice() == menu.getPrice());
        if (exists) {
            return ResponseEntity.status(404).body("Such dish with the same price already exists");
        }

        menuService.add(menu);

        return ResponseEntity.status(201).body("The entity has been added");
    }

    //voting
    @PutMapping(value = "/vote/{id}")
    public ResponseEntity<?> vote(@PathVariable("id") long rest_id,
                                  HttpServletRequest request) {

        String auth = request.getHeader("Authorization");

        if (auth == null) {
            return ResponseEntity.status(404).body("You have to login by Basic Auth");
        }

        final String encodedUserPassword = auth.replaceFirst("Basic"
                + " ", "");
        String usernameAndPassword = null;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(
                    encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final StringTokenizer tokenizer = new StringTokenizer(
                usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        User user = userService.getList().stream().filter(us -> us.getUsername().equals(username)
                && us.getPassword().equals(password)).findFirst().orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body("you need to register first");
        }

        Restaurant restFromDB = restService.getById(rest_id);


        if (restFromDB == null) {
            return ResponseEntity.status(404).body("There is no restaurants by the ID");
        }

        Restaurant restFromUser = user.getRest();

        Timestamp now = Timestamp.from(Calendar.getInstance().toInstant());

        if (restFromUser != null) {

            Timestamp tsFromDb = user.getLast_vote();

            //if it's not first vote && it's the same day and time is after 11
            if ((tsFromDb != null)
                    && (tsFromDb.toString().substring(0, 11).equals(now.toString().substring(0, 11))
                    && (Integer.valueOf(now.toString().substring(11, 13)) >= 11))) {

                return ResponseEntity.status(200).body("You can't change your order after 11");
            }
        }

        user.setLast_vote(now);
        user.setRest(restFromDB);
        userService.updateById(user.getId(), user);
        return ResponseEntity.status(200).body("Done!");
    }

    //registration
    @PostMapping(value = "/users")
    @ResponseBody
    public ResponseEntity<?> saveUser(HttpServletRequest request) {

        String auth = request.getHeader("Authorization");

        if (auth == null) {
            return ResponseEntity.status(404).body("You have to login by Basic Auth");
        }

        final String encodedUserPassword = auth.replaceFirst("Basic"
                + " ", "");
        String usernameAndPassword = null;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(
                    encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final StringTokenizer tokenizer = new StringTokenizer(
                usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();


        List<User> users = userService.getList();
        if (users != null && !users.isEmpty()) {
            boolean exists = users.stream().anyMatch(us -> us.getUsername().equals(username));
            if (exists) {
                return ResponseEntity.status(404).body("Such user already exists");
            }
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        Role roleUser = roleService.getById(1L);
        if (roleUser == null) {
            return ResponseEntity.status(404).body("There is no roles in the database");
        }
        newUser.getRoles().add(roleUser);

        userService.add(newUser);

        return ResponseEntity.status(201).body("New user has been added");
    }
}