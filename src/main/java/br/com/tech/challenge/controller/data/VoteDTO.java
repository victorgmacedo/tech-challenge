package br.com.tech.challenge.controller.data;

import br.com.ntconsult.challenge.definitions.VoteOptions;

public record VoteDTO(
        VoteOptions vote,
        AssociateDTO associate
) {}