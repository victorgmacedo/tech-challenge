package br.com.tech.challenge.service.validation.vote;


import br.com.tech.challenge.domain.VoteDetail;
import br.com.tech.challenge.exceptions.AssociateAlreadyVotedException;
import br.com.tech.challenge.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(2)
@AllArgsConstructor
public class AssociateAlreadyVoted implements VoteValidation {
    private VoteRepository voteRepository;
    @Override
    public void validate(VoteDetail voteDetail) {
        var result = voteRepository.existsVoteFromAssociateAndSession(
                voteDetail.session().getId(), voteDetail.associateDetail().cpf());

        if(result){
            throw new AssociateAlreadyVotedException(STR."Associate with {CPF} = \{voteDetail.associateDetail().cpf()} already vote");
        }
    }
}