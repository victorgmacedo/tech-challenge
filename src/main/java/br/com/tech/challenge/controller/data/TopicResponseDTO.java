package br.com.tech.challenge.controller.data;

public record TopicResponseDTO(
        String id,
        String title,
        String description,
        SessionDTO session
) {}
