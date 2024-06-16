package br.com.ntconsult.challenge.service.validation.vote

import br.com.ntconsult.challenge.domain.VoteDetail
import br.com.ntconsult.challenge.exception.AssociateAlreadyVotedException
import br.com.ntconsult.challenge.repository.VoteRepository
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service

@Service
@Order(2)
class AssociateAlreadyVoted(private val voteRepository: VoteRepository): VoteValidation {
    override fun valid(voteDetail: VoteDetail) {
        val result = voteRepository.existsVoteFromAssociateAndSession(
                voteDetail.session.id!!, voteDetail.associateDetail.cpf)

        if(result){
            throw AssociateAlreadyVotedException("Associate with {CPF} = ${voteDetail.associateDetail.cpf} already vote")
        }
    }
}