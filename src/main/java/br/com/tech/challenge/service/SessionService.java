package br.com.tech.challenge.service;


import br.com.tech.challenge.domain.SessionDefinition;
import br.com.tech.challenge.exceptions.SessionAlreadyExistsException;
import br.com.tech.challenge.exceptions.SessionNotFoundException;
import br.com.tech.challenge.model.Session;
import br.com.tech.challenge.repository.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.tech.challenge.config.SessionRabbitMQConfiguration.DELAY_HEADER;
import static br.com.tech.challenge.config.SessionRabbitMQConfiguration.ROUTING_KEY;
import static br.com.tech.challenge.config.SessionRabbitMQConfiguration.TOPIC_EXCHANGE_NAME;

@Service
@AllArgsConstructor
@Slf4j
public class SessionService {
    private final SessionRepository sessionRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ResultService resultService;
    private final TopicService topicService;

    public Session create(SessionDefinition sessionDefinition) {
        if (sessionRepository.existSessionForTopic(sessionDefinition.topic().getId())) {
            throw new SessionAlreadyExistsException(STR."There is already a session for topic {ID} = \{sessionDefinition.topic().getId()}");
        }
        LocalDateTime now = LocalDateTime.now();
        Session session = Session.builder()
                .topic(sessionDefinition.topic())
                .startedAt(now)
                .finishAt(now.plusMinutes(sessionDefinition.timeToVote()))
                .build();

        session = save(session);
        publishSessionToClose(session);
        return session;
    }

    private void publishSessionToClose(Session session) {
        Message message = MessageBuilder.withBody(session.getId().toString().getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader(DELAY_HEADER, session.timeToClose() * 1000)
                .build();
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, ROUTING_KEY, message);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    @Transactional
    public void closeSession(String sessionId) {
        log.info(STR."Closing session for id {ID} " + sessionId);
        var session = sessionRepository.findById(UUID.fromString(sessionId)).orElseThrow(() ->
                new SessionNotFoundException(STR."There is no session open for id {ID} " + sessionId));
        resultService.buildResultDetailForSessionId(session.getId());
        var topic = session.getTopic();
        topic.setResult(session.result());
        topicService.save(topic);
    }

    @Cacheable("sessions")
    public Session findByTopicId(String id) {
        return sessionRepository.findByTopicId(UUID.fromString(id)).orElseThrow(() ->
                new SessionNotFoundException(STR."There is no session open for topic {ID} " + id));
    }

}
