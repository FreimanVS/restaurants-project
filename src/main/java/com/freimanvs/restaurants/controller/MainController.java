package com.freimanvs.restaurants.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Freiman V.S.
 * @version 1.0
 */
@RestController
public class MainController {

    @RequestMapping("/")
    public ResponseEntity<?> hello() {
        return ResponseEntity.status(200).body("Hello!");
    }
}
