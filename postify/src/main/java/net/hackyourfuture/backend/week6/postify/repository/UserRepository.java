package net.hackyourfuture.backend.week6.postify.repository;

import net.hackyourfuture.backend.week6.postify.dto.UserStatsResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserStatsResponse getUserStatsById(Long id) {
        String sql = "SELECT user_id, user_name FROM users WHERE user_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            new UserStatsResponse(
                                    rs.getLong("user_id"),
                                    rs.getString("user_name")
                            ),
                    id
            );
        } catch (Exception e) {
            return null;
        }
    }
}