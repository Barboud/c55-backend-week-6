package net.hackyourfuture.backend.week6.postify.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserStatsResponse {
    private final Long userId;
    private final String userName;

}