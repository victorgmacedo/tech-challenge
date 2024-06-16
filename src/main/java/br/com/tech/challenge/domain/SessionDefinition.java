package br.com.tech.challenge.domain;

import br.com.tech.challenge.model.Topic;

public record SessionDefinition(Topic topic, long timeToVote) {
    public SessionDefinition {
        if (timeToVote < 1) {
            throw new IllegalArgumentException("Time to vote cannot be less than 1");
        }
    }
}
