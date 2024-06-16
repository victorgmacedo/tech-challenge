package br.com.ntconsult.challenge.service.validation.vote

import br.com.ntconsult.challenge.domain.VoteDetail

interface VoteValidation {

    fun valid(voteDetail: VoteDetail)

}