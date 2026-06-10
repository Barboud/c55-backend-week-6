package net.hackyourfuture.backend.week6.postify.repository;


import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TrackRepository {

    private final JdbcTemplate jdbcTemplate;

    public TrackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TrackLyricsResponse getTrackAndArtistById(Long trackId) {
        String sql = "SELECT " +
                "    tracks.track_id AS trackId, " +
                "    tracks.track_title AS trackTitle, " +
                "    artists.artist_name AS artistName " +
                "FROM tracks " +
                "JOIN albums ON tracks.album_id = albums.album_id " +
                "JOIN artists ON albums.artist_id = artists.artist_id " +
                "WHERE tracks.track_id = ?;";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            new TrackLyricsResponse(
                                    rs.getLong("trackId"),
                                    rs.getString("trackTitle"),
                                    rs.getString("artistName"),
                                    null // from external API
                            ),
                    trackId
            );
        } catch (Exception e) {
            return null;
        }
    }
}