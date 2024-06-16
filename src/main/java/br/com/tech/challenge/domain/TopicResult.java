package br.com.tech.challenge.domain;

import br.com.ntconsult.challenge.definitions.VoteOptions;

import java.util.Map;
import java.util.UUID;

public record TopicResult(UUID topicId, VoteOptions result, long totalVotes, Map<VoteOptions, Long> voteDetail) {}
