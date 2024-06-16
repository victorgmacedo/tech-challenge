package br.com.tech.challenge.controller.data;

import jakarta.validation.constraints.Min;

public record SessionDefinitionDTO(
        @Min(value = 1, message = "Time to vote cannot be less than 1") Long timeToVote
) {
    public SessionDefinitionDTO() {
        this(1L);
    }
}
