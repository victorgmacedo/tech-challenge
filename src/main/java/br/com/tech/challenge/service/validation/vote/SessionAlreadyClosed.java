package br.com.tech.challenge.service.validation.vote;


import br.com.tech.challenge.domain.VoteDetail;
import br.com.tech.challenge.exceptions.SessionAlreadyClosedException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class SessionAlreadyClosed implements VoteValidation {
    @Override
    public void validate(VoteDetail voteDetail) {
        if(!voteDetail.session().isOpenToVote()){
            throw new SessionAlreadyClosedException(STR."Session with {id} = \{voteDetail.session().getId()} closed at \{voteDetail.session().getFinishAt()}");
        }
    }
}