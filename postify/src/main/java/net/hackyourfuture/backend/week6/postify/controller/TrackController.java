package net.hackyourfuture.backend.week6.postify.controller;


import lombok.AllArgsConstructor;
import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsResponse;
import net.hackyourfuture.backend.week6.postify.service.TrackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;

    @GetMapping("/{id}/lyrics")
    public TrackLyricsResponse getTrackLyrics(@PathVariable Long id) {
        return trackService.getTrackLyrics(id);
    }
}