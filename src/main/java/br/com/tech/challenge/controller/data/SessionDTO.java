package br.com.tech.challenge.controller.data;

import java.time.LocalDateTime;

public record SessionDTO(
        String id,
        LocalDateTime finishAt,
        Boolean isOpenToVote
) {}
