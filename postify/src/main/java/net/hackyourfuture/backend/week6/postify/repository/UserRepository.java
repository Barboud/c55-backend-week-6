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

        String mainSql = "SELECT " +
                "    users.user_id AS userId, " +
                "    users.user_name AS userName, " +
                "    users.user_country AS userCountry, " +
                "    COUNT(streams.stream_id) AS totalStreams, " +
                "    COUNT(DISTINCT streams.track_id) AS uniqueTracksStreamed, " +
                "    COUNT(DISTINCT albums.artist_id) AS uniqueArtistsStreamed, " +
                "    COALESCE(SUM(tracks.track_duration_s), 0) AS totalListeningTimeSeconds " +
                "FROM users " +
                "LEFT JOIN streams ON users.user_id = streams.user_id " +
                "LEFT JOIN tracks ON streams.track_id = tracks.track_id " +
                "LEFT JOIN albums ON tracks.album_id = albums.album_id " +
                "WHERE users.user_id = ? " +
                "GROUP BY users.user_id, users.user_name, users.user_country;";

        String genreSql = "SELECT tracks.genre " +
                "FROM streams " +
                "JOIN tracks ON streams.track_id = tracks.track_id " +
                "WHERE streams.user_id = ? " +
                "GROUP BY tracks.genre " +
                "ORDER BY COUNT(streams.stream_id) DESC " +
                "LIMIT 1;";

        try {
            String favoriteGenre;
            try {
                favoriteGenre = jdbcTemplate.queryForObject(genreSql, String.class, id);
                if (favoriteGenre == null) {
                    favoriteGenre = "None";
                }
            } catch (Exception e) {
                favoriteGenre = "None";
            }

            final String finalGenre = favoriteGenre;

            return jdbcTemplate.queryForObject(mainSql, (rs, rowNum) ->
                            new UserStatsResponse(
                                    rs.getLong("userId"),
                                    rs.getString("userName"),
                                    rs.getString("userCountry"),
                                    rs.getLong("totalStreams"),
                                    rs.getLong("uniqueTracksStreamed"),
                                    rs.getLong("uniqueArtistsStreamed"),
                                    finalGenre,
                                    rs.getLong("totalListeningTimeSeconds")
                            ),
                    id
            );

        } catch (Exception e) {
            return null;
        }
    }
}