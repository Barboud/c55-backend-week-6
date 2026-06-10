package net.hackyourfuture.backend.week6.postify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrackLyricsResponse {
    private Long trackId;
    private String trackTitle;
    private String artistName;
    private String lyrics;
}