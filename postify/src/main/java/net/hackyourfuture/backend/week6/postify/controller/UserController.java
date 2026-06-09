package net.hackyourfuture.backend.week6.postify.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @GetMapping("/users/{id}/stats")
    public String getUserStatsById(@PathVariable Long id) {
        return "Hello!" + id;
    }


}


