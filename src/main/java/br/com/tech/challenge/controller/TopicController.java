package br.com.tech.challenge.controller;

import br.com.tech.challenge.controller.data.*;
import br.com.tech.challenge.domain.AssociateDetail;
import br.com.tech.challenge.domain.SessionDefinition;
import br.com.tech.challenge.domain.VoteDetail;
import br.com.tech.challenge.exceptions.SessionNotFoundException;
import br.com.tech.challenge.mapper.SessionMapper;
import br.com.tech.challenge.mapper.TopicMapper;
import br.com.tech.challenge.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(TopicController.PATH)
public class TopicController {

    public static final String PATH = "/topics";

    private final TopicMapper topicMapper;
    private final TopicService topicService;
    private final SessionService sessionService;
    private final VoteService voteService;
    private final SessionMapper sessionMapper;

    @Autowired
    public TopicController(TopicMapper topicMapper, TopicService topicService,
                           SessionService sessionService, VoteService voteService,
                           SessionMapper sessionMapper) {
        this.topicMapper = topicMapper;
        this.topicService = topicService;
        this.sessionService = sessionService;
        this.voteService = voteService;
        this.sessionMapper = sessionMapper;
    }

    @PostMapping
    public ResponseEntity<TopicResponseDTO> create(@RequestBody TopicRequestDTO topicDTO) {
        TopicResponseDTO topic = topicMapper.toDTO(
                topicService.save(
                        topicMapper.toModel(topicDTO)
                )
        );
        return ResponseEntity.created(URI.create(PATH + "/" + topic.id())).body(topic);
    }

    @PostMapping("/{id}/session/open")
    public ResponseEntity<SessionDTO> open(@PathVariable("id") String id,
                                           @RequestBody SessionDefinitionDTO sessionDefinitionDTO) {
        SessionDefinition sessionDefinition = new SessionDefinition(
                topicService.findById(id),
                sessionDefinitionDTO.timeToVote()
        );
        SessionDTO session = sessionMapper.toDTO(sessionService.create(sessionDefinition));
        return ResponseEntity.created(URI.create(PATH + "/" + session.getId())).body(session);
    }

    @PostMapping("{id}/session/vote")
    public ResponseEntity<Void> vote(@PathVariable("id") String id, @RequestBody VoteDTO voteDTO) {
        Session session = sessionService.findByTopicId(id);
        if (session == null) {
            throw new SessionNotFoundException("There is no session open for topic {ID} " + id);
        }
        VoteDetail voteDetail = new VoteDetail(
                session,
                new AssociateDetail(voteDTO.getAssociate().getCpf()),
                voteDTO.getVote()
        );
        voteService.vote(voteDetail);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> find(@PathVariable("id") String id) {
        TopicResponseDTO topic = topicMapper.toDTO(topicService.findById(id));
        topic.setSession(sessionService.findByTopicId(id) != null ?
                sessionMapper.toDTO(sessionService.findByTopicId(id)) : null);
        return ResponseEntity.ok(topic);
    }
}
