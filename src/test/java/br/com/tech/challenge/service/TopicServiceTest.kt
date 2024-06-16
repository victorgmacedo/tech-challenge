package br.com.ntconsult.challenge.service

import br.com.ntconsult.challenge.exception.TopicNotFoundException
import br.com.ntconsult.challenge.model.Topic
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class TopicServiceTest {

    @Autowired
    lateinit var topicService: TopicService

    @Test
    fun shouldSaveNewTopic() {
        val topic = topicService.save(Topic(title = "Test", description = "Test"))
        Assertions.assertNotNull(topic.id)
    }

    @Test
    fun shouldReturnASavedTopic() {
        val topic = topicService.save(Topic(title = "Test", description = "Test"))
        Assertions.assertNotNull(topicService.findById(topic.id.toString()))
    }

    @Test
    fun shouldReturnNotFoundExceptionWhenTryToGetANonexistentTopic() {
        Assertions.assertThrows(TopicNotFoundException::class.java) {
            topicService.findById(UUID.randomUUID().toString())
        }
    }
}