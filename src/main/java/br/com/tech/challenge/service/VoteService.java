package br.com.tech.challenge.service;


import br.com.tech.challenge.domain.VoteDetail;
import br.com.tech.challenge.model.Associate;
import br.com.tech.challenge.model.Vote;
import br.com.tech.challenge.repository.VoteRepository;
import br.com.tech.challenge.service.AssociateService;
import br.com.tech.challenge.service.validation.vote.VoteValidation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final AssociateService associateService;
    private final List<VoteValidation> voteValidation;


    public void vote(VoteDetail voteDetail) {
        Associate associate = associateService.findAssociateByCpfOrCreateNewAssociate(voteDetail.associateDetail().cpf());
        Vote vote = new Vote(
                voteDetail.session(),
                associate,
                voteDetail.vote()
        );
        for (VoteValidation validation : voteValidation) {
            validation.validate(voteDetail);
        }
        voteRepository.save(vote);
    }
}