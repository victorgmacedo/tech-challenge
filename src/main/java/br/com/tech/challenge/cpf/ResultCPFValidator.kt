package br.com.ntconsult.challenge.proxy.cpf

private const val ABLE_TO_VOTE = "ABLE_TO_VOTE"

class ResultCPFValidator(val status: String?) {

    fun isValid() = status == ABLE_TO_VOTE

    companion object {
        fun default() = ResultCPFValidator(ABLE_TO_VOTE)
    }
}