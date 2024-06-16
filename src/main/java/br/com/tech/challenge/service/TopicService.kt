package br.com.ntconsult.challenge.service

import br.com.ntconsult.challenge.exception.TopicNotFoundException
import br.com.ntconsult.challenge.model.Topic
import br.com.ntconsult.challenge.repository.TopicRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TopicService(private val topicRepository: TopicRepository) {

    fun save(topic: Topic): Topic {
        return topicRepository.save(topic)
    }

    fun findById(id: String): Topic {
        return topicRepository.findById(UUID.fromString(id)).orElseThrow {
            TopicNotFoundException("Agenda with {id} = $id not found")
        }
    }

}
