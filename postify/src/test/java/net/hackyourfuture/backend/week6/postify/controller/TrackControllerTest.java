package net.hackyourfuture.backend.week6.postify.controller;

import net.hackyourfuture.backend.week6.postify.dto.TrackLyricsResponse;
import net.hackyourfuture.backend.week6.postify.service.TrackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private TrackService trackService;


    @Test
    void shouldReturnLyricsForExistingTrack() throws Exception {

        TrackLyricsResponse fakeResponse = new TrackLyricsResponse(
                67L,
                "the 1",
                "Taylor Swift",
                "I'm doing good, I'm on some new shi..."
        );


        when(trackService.getTrackLyrics(67L)).thenReturn(fakeResponse);

        mockMvc.perform(get("/tracks/67/lyrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackId").value(67L))
                .andExpect(jsonPath("$.trackTitle").value("the 1"))
                .andExpect(jsonPath("$.artistName").value("Taylor Swift"))
                .andExpect(jsonPath("$.lyrics").value("I'm doing good, I'm on some new shi..."));
    }


    @Test
    void shouldReturn404WhenTrackNotFound() throws Exception {

        when(trackService.getTrackLyrics(99999L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/tracks/99999/lyrics"))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldReturn404WhenLyricsNotAvailable() throws Exception {

        when(trackService.getTrackLyrics(90L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/tracks/90/lyrics"))
                .andExpect(status().isNotFound());
    }
}