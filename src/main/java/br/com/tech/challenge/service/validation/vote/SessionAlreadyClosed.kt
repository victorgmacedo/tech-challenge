package br.com.ntconsult.challenge.service.validation.vote

import br.com.ntconsult.challenge.domain.VoteDetail
import br.com.ntconsult.challenge.exception.SessionAlreadyClosedException
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service

@Service
@Order(1)
class SessionAlreadyClosed: VoteValidation {
    override fun valid(voteDetail: VoteDetail) {
        if(!voteDetail.session.isOpenToVote()){
            throw SessionAlreadyClosedException("Session with {id} = ${voteDetail.session.id} closed at ${voteDetail.session.finishAt}")
        }
    }
}