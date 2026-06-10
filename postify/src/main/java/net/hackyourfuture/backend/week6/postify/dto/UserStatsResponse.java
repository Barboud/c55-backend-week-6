package net.hackyourfuture.backend.week6.postify.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserStatsResponse {
    private final Long userId;
    private final String userName;
    private final String userCountry;
    private final Long totalStreams;
    private final Long uniqueTracksStreamed;
    private final Long uniqueArtistsStreamed;
    private final String favoriteGenre;
    private final Long totalListeningTimeSeconds;
}