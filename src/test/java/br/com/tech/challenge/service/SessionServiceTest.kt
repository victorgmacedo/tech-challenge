package br.com.ntconsult.challenge.service

import br.com.ntconsult.challenge.domain.SessionDefinition
import br.com.ntconsult.challenge.exception.SessionAlreadyExistsException
import br.com.ntconsult.challenge.model.Session
import br.com.ntconsult.challenge.model.Topic
import br.com.ntconsult.challenge.repository.SessionRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.cache.annotation.EnableCaching
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@EnableCaching
class SessionServiceTest{

    @SpyBean
    lateinit var sessionRepository: SessionRepository

    @Autowired
    lateinit var sessionService: SessionService

    @Autowired
    lateinit var topicService: TopicService

    @Test
    fun shouldCreateOneSessionForATopic() {
        val sessionDefinition = createTopicAndSessionDefinition()
        val session = sessionService.create(sessionDefinition)
        Assertions.assertNotNull(session.id)
        Assertions.assertEquals(session.startedAt.plusMinutes(sessionDefinition.timeToVote), session.finishAt)
    }

    @Test
    fun shouldCreateOneSessionForATopicWith1MinuteDefaultWhenNotPassedValueForTimeToVote() {
        val sessionDefinition = SessionDefinition(
                topic = createTopic()
        )
        val session = sessionService.create(sessionDefinition)
        Assertions.assertNotNull(session.id)
        Assertions.assertEquals(session.startedAt.plusMinutes(1), session.finishAt)
    }

    @Test
    fun shouldThrowAnExceptionWhenTryCreateSessionForATopicWithAlreadyHasASession() {
        val sessionDefinition = createTopicAndSessionDefinition()
        sessionService.create(sessionDefinition)
        val exception = Assertions.assertThrows(SessionAlreadyExistsException::class.java) {
            sessionService.create(sessionDefinition)
        }
        Assertions.assertEquals("There is already a session for topic {ID} = ${sessionDefinition.topic.id}", exception.message)
    }

    @Test
    fun shouldNotUseRepositoryWhenSessionAlreadyWasUsed() {
        val sessionDefinition = createTopicAndSessionDefinition()
        val session = sessionService.create(sessionDefinition)
        Mockito.`when`(sessionRepository.findByTopicId(sessionDefinition.topic.id!!)).thenReturn(session)
        for (i in 1..3){
            sessionService.findByTopicId(sessionDefinition.topic.id.toString())
        }

        Mockito.verify(sessionRepository, Mockito.times(1)).findByTopicId(sessionDefinition.topic.id!!)
    }

    @Test
    @Transactional
    fun shouldReturnSessionsWithNoResultDetail() {
        val startedAt = LocalDateTime.of(2023,1,1,1,1)
        val session = Session(
                startedAt = startedAt,
                finishAt = startedAt.plusMinutes(1),
                topic = createTopic()
        )
        sessionService.save(session)

        val sessions = sessionService.findAllSessionsClosedWithTopicResultEmpty(startedAt.plusMinutes(3))

        Assertions.assertEquals(1, sessions.size)
    }

    @Test
    fun shouldReturnIllegalArgumentExceptionWhenTryToGetANonexistentSession() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            SessionDefinition(
                    topic = createTopic(), timeToVote = -1
            )
        }
    }

    private fun createTopicAndSessionDefinition() : SessionDefinition {
        val topic = createTopic()
        return SessionDefinition(
                topic = topic, timeToVote = 5
        )
    }

    private fun createTopic() =  topicService.save(Topic(title = "Test", description = "Test"))

}