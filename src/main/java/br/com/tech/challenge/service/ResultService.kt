package br.com.ntconsult.challenge.service

import br.com.ntconsult.challenge.definitions.VoteOptions
import br.com.ntconsult.challenge.model.ResultDetail
import br.com.ntconsult.challenge.model.Session
import br.com.ntconsult.challenge.repository.ResultRepository
import org.springframework.stereotype.Service

@Service
class ResultService(private val resultRepository: ResultRepository) {

    fun buildResultDetail(session: Session) : ResultDetail {
        val voteDetail = resultRepository.voteDetail(session.id!!)
        return ResultDetail(
                totalVotes = resultRepository.totalVotes(session.id),
                result = getResultFromVoteDetail(voteDetail),
                voteDetail = voteDetail
        )
    }

    fun getResultFromVoteDetail(voteDetail: Map<VoteOptions, Long>) : VoteOptions? {
        if(voteDetail.getOrDefault(VoteOptions.YES, 0) ==
                voteDetail.getOrDefault(VoteOptions.NO, 0)) {
            return null
        }
        return if(voteDetail.getOrDefault(VoteOptions.YES, 0)
                > voteDetail.getOrDefault(VoteOptions.NO, 0)){
            VoteOptions.YES
        } else VoteOptions.NO
    }

}