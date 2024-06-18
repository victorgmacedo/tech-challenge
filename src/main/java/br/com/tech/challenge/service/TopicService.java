package br.com.tech.challenge.service;


import br.com.tech.challenge.definitions.Result;
import br.com.tech.challenge.exceptions.TopicNotFoundException;
import br.com.tech.challenge.model.Topic;
import br.com.tech.challenge.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic findById(String id) {
        return topicRepository.findById(UUID.fromString(id)).orElseThrow(() ->
        new TopicNotFoundException(STR."Agenda with {id} = \{id} not found"));
    }

}