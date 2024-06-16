package br.com.ntconsult.challenge.service

import br.com.ntconsult.challenge.definitions.VoteOptions
import br.com.ntconsult.challenge.domain.AssociateDetail
import br.com.ntconsult.challenge.domain.SessionDefinition
import br.com.ntconsult.challenge.domain.VoteDetail
import br.com.ntconsult.challenge.model.Session
import br.com.ntconsult.challenge.model.Topic
import br.com.ntconsult.challenge.proxy.cpf.CPFValidator
import br.com.ntconsult.challenge.proxy.cpf.ResultCPFValidator
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import kotlin.random.Random

@SpringBootTest
class ResultServiceTest{

    @Autowired
    lateinit var sessionService: SessionService

    @Autowired
    lateinit var topicService: TopicService

    @Autowired
    lateinit var voteService: VoteService

    @Autowired
    lateinit var resultService: ResultService

    @MockBean
    lateinit var cpfValidator: CPFValidator

    private val random = Random(10000)

    @Test
    fun shouldCreateCorrectResultForAnSession() {
        Mockito.`when`(cpfValidator.cpfIsValid(Mockito.anyString())).thenReturn(ResultCPFValidator("ABLE_TO_VOTE"))
        val session = createTopicAndSession()
        for(i in 1..11){
            voteService.vote(createVote(session, if(i % 2 == 0) VoteOptions.YES else VoteOptions.NO))
        }
        val resultDetail = resultService.buildResultDetail(session)
        assertEquals(11, resultDetail.totalVotes)
        assertEquals(VoteOptions.NO, resultDetail.result)
        assertThat(resultDetail.voteDetail, Matchers.hasEntry(VoteOptions.YES, 5))
        assertThat(resultDetail.voteDetail, Matchers.hasEntry(VoteOptions.NO, 6))
    }

    @Test
    fun shouldCreateEmptyResultForAnSessionWhenThereAreSameVotesForYesAndNo() {
        Mockito.`when`(cpfValidator.cpfIsValid(Mockito.anyString())).thenReturn(ResultCPFValidator("ABLE_TO_VOTE"))
        val session = createTopicAndSession()
        for(i in 1..10){
            voteService.vote(createVote(session, if(i % 2 == 0) VoteOptions.YES else VoteOptions.NO))
        }
        val resultDetail = resultService.buildResultDetail(session)
        assertEquals(10, resultDetail.totalVotes)
        assertNull(resultDetail.result)
        assertThat(resultDetail.voteDetail, Matchers.hasEntry(VoteOptions.YES, 5))
        assertThat(resultDetail.voteDetail, Matchers.hasEntry(VoteOptions.NO, 5))
    }

    private fun createVote(session: Session, vote: VoteOptions): VoteDetail {
        return VoteDetail(
                session = session,
                associateDetail = AssociateDetail(random.nextLong().toString()),
                vote = vote
        )
    }

    private fun createTopicAndSession() : Session {
        val topic = createTopic()
        val sessionDefinition = SessionDefinition(
                topic = topic
        )
        return sessionService.create(sessionDefinition)
    }

    private fun createTopic() =  topicService.save(Topic(title = "Test", description = "Test"))

}