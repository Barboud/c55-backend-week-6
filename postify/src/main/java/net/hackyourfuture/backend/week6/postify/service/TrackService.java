package net.hackyourfuture.backend.week6.postify.service;


import net.hackyourfuture.backend.week6.postify.dto.ExternalLyricsResponse;
import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsResponse;
import net.hackyourfuture.backend.week6.postify.repository.TrackRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final RestClient restClient;

    public TrackService(TrackRepository trackRepository, RestClient restClient) {
        this.trackRepository = trackRepository;
        this.restClient = restClient;
    }

    public TrackLyricsResponse getTrackLyrics(Long trackId) {

        TrackLyricsResponse trackInfo = trackRepository.getTrackAndArtistById(trackId);

        if (trackInfo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track not found in our database");
        }


        String url = "https://api.lyrics.ovh/v1/" + trackInfo.getArtistName() + "/" + trackInfo.getTrackTitle();

        try {

            ExternalLyricsResponse externalResponse = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(ExternalLyricsResponse.class);


            if (externalResponse != null && externalResponse.getLyrics() != null) {
                return new TrackLyricsResponse(
                        trackInfo.getTrackId(),
                        trackInfo.getTrackTitle(),
                        trackInfo.getArtistName(),
                        externalResponse.getLyrics()
                );
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lyrics not found for this track");
            }

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The lyrics API has no lyrics for this track.");
        }
    }
}