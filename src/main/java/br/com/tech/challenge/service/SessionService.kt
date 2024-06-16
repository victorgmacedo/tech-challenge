package br.com.ntconsult.challenge.service

import br.com.ntconsult.challenge.domain.SessionDefinition
import br.com.ntconsult.challenge.exception.SessionAlreadyExistsException
import br.com.ntconsult.challenge.model.Session
import br.com.ntconsult.challenge.repository.SessionRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class SessionService(private val sessionRepository: SessionRepository) {

    fun create(sessionDefinition: SessionDefinition) : Session {
        if(sessionRepository.existSessionForTopic(sessionDefinition.topic.id!!)) {
            throw SessionAlreadyExistsException("There is already a session for topic {ID} = ${sessionDefinition.topic.id}")
        }
        return with(sessionDefinition) {
            val now = LocalDateTime.now()
            val session = Session(
                    topic = topic,
                    startedAt = now,
                    finishAt = now.plusMinutes(timeToVote)
            )
            save(session)
        }
    }

    fun save(session: Session): Session {
        return sessionRepository.save(session)
    }

    @Cacheable("sessions")
    fun findByTopicId(id: String) : Session? {
        return sessionRepository.findByTopicId(UUID.fromString(id))
    }

    fun findAllSessionsClosedWithTopicResultEmpty(date: LocalDateTime): List<Session> {
        return sessionRepository.findAllSessionsClosedWithResultDetailEmpty(date)
    }

}