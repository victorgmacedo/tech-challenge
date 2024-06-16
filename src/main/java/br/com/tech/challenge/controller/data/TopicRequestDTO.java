package br.com.tech.challenge.controller.data;

import jakarta.validation.constraints.Max;

public record TopicRequestDTO(
        @Max(value = 100) String title,
        @Max(value = 500) String description
) {}
