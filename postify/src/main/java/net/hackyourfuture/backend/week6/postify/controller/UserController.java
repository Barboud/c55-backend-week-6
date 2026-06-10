package net.hackyourfuture.backend.week6.postify.controller;


import lombok.AllArgsConstructor;
import net.hackyourfuture.backend.week6.postify.dto.UserStatsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import net.hackyourfuture.backend.week6.postify.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}/stats")
    public ResponseEntity<?> getUserStatsById(@PathVariable Long id) {
        UserStatsResponse stats = userRepository.getUserStatsById(id);

        return stats != null ? ResponseEntity.ok(stats)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

    }


}


