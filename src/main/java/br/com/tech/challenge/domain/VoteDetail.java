package br.com.tech.challenge.domain;

import br.com.tech.challenge.definitions.VoteOptions;
import br.com.tech.challenge.model.Session;

public record VoteDetail(Session session, AssociateDetail associateDetail, VoteOptions vote) {}
