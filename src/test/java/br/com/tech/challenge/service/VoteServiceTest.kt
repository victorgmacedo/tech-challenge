package br.com.ntconsult.challenge.service

import br.com.ntconsult.challenge.definitions.VoteOptions
import br.com.ntconsult.challenge.domain.AssociateDetail
import br.com.ntconsult.challenge.domain.SessionDefinition
import br.com.ntconsult.challenge.domain.VoteDetail
import br.com.ntconsult.challenge.exception.AssociateAlreadyVotedException
import br.com.ntconsult.challenge.exception.InvalidDocumentException
import br.com.ntconsult.challenge.exception.SessionAlreadyClosedException
import br.com.ntconsult.challenge.model.Session
import br.com.ntconsult.challenge.model.Topic
import br.com.ntconsult.challenge.proxy.cpf.CPFValidator
import br.com.ntconsult.challenge.proxy.cpf.ResultCPFValidator
import br.com.ntconsult.challenge.repository.VoteRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDateTime

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VoteServiceTest {

    @Autowired
    lateinit var sessionService: SessionService

    @Autowired
    lateinit var topicService: TopicService

    @Autowired
    lateinit var voteService: VoteService

    @SpyBean
    lateinit var voteRepository: VoteRepository

    @MockBean
    lateinit var cpfValidator: CPFValidator

    @Test
    fun shouldCreateAVote() {
        Mockito.`when`(cpfValidator.cpfIsValid(CPF)).thenReturn(ResultCPFValidator("ABLE_TO_VOTE"))
        val session = createTopicAndSession()
        val voteDetail = VoteDetail(
                session = session,
                associateDetail = AssociateDetail(CPF),
                vote = VoteOptions.YES
        )
        voteService.vote(voteDetail)
        val votes = voteRepository.findAll()
        Assertions.assertEquals(1, votes.size)
    }

    @Test
    fun shouldReturnAnInvalidDocumentExceptionWhenAssociateCPFisInvalid() {
        Mockito.`when`(cpfValidator.cpfIsValid(CPF)).thenReturn(ResultCPFValidator("UNABLE_TO_VOTE"))
        val session = createTopicAndSession()
        val voteDetail = VoteDetail(
                session = session,
                associateDetail = AssociateDetail(CPF),
                vote = VoteOptions.YES
        )
        Assertions.assertThrows(InvalidDocumentException::class.java) {
            voteService.vote(voteDetail)
        }
        Mockito.verify(voteRepository, Mockito.never()).save(Mockito.any())
    }

    @Test
    fun shouldReturnAnAssociateAlreadyVotedExceptionWhenAssociateTryToVoteTwice() {
        Mockito.`when`(cpfValidator.cpfIsValid(CPF)).thenReturn(ResultCPFValidator("ABLE_TO_VOTE"))
        val session = createTopicAndSession()
        val voteDetail = VoteDetail(
                session = session,
                associateDetail = AssociateDetail(CPF),
                vote = VoteOptions.YES
        )
        voteService.vote(voteDetail)
        Assertions.assertThrows(AssociateAlreadyVotedException::class.java) {
            voteService.vote(voteDetail)
        }
        Mockito.verify(voteRepository, Mockito.times(1)).save(Mockito.any())
    }

    @Test
    fun shouldReturnAnSessionAlreadyClosedExceptionWhenAssociateTryToVoteAndSessionAlreadyClosed() {
        Mockito.`when`(cpfValidator.cpfIsValid(CPF)).thenReturn(ResultCPFValidator("ABLE_TO_VOTE"))
        val startedAt = LocalDateTime.of(2023,1,1,1,1)
        val session = Session(
                startedAt = startedAt,
                finishAt = startedAt.plusMinutes(1),
                topic = createTopic()
        )
        sessionService.save(session)
        val voteDetail = VoteDetail(
                session = session,
                associateDetail = AssociateDetail(CPF),
                vote = VoteOptions.YES
        )
        Assertions.assertThrows(SessionAlreadyClosedException::class.java) {
            voteService.vote(voteDetail)
        }
        Mockito.verify(voteRepository, Mockito.never()).save(Mockito.any())
    }

    private fun createTopicAndSession() : Session {
        val topic = createTopic()
        val sessionDefinition = SessionDefinition(
                topic = topic
        )
        return sessionService.create(sessionDefinition)
    }

    private fun createTopic() =  topicService.save(Topic(title = "Test", description = "Test"))

    private val CPF = "34875062036"
}