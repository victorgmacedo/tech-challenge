package br.com.ntconsult.challenge.service

import br.com.ntconsult.challenge.domain.VoteDetail
import br.com.ntconsult.challenge.model.Vote
import br.com.ntconsult.challenge.repository.VoteRepository
import br.com.ntconsult.challenge.service.validation.vote.VoteValidation
import org.springframework.stereotype.Service

@Service
class VoteService(private val voteRepository: VoteRepository,
                  private val associateService: AssociateService,
                  private val voteValidation: List<VoteValidation>) {

    fun vote(voteDetail: VoteDetail) {
        with(voteDetail) {
            val associate = associateService.findAssociateByCpfOrCreateNewAssociate(associateDetail.cpf)
            val vote = Vote(
                    session = session,
                    associate = associate,
                    vote = vote
            )
            voteValidation.forEach {
                it.valid(voteDetail)
            }
            voteRepository.save(vote)
        }
    }

}