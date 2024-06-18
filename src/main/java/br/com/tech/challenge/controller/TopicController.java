package br.com.tech.challenge.controller;

import br.com.tech.challenge.controller.data.SessionDTO;
import br.com.tech.challenge.controller.data.SessionDefinitionDTO;
import br.com.tech.challenge.controller.data.TopicRequestDTO;
import br.com.tech.challenge.controller.data.TopicResponseDTO;
import br.com.tech.challenge.controller.data.VoteDTO;
import br.com.tech.challenge.domain.AssociateDetail;
import br.com.tech.challenge.domain.SessionDefinition;
import br.com.tech.challenge.domain.VoteDetail;
import br.com.tech.challenge.mapper.SessionMapper;
import br.com.tech.challenge.mapper.TopicMapper;
import br.com.tech.challenge.model.Session;
import br.com.tech.challenge.service.SessionService;
import br.com.tech.challenge.service.TopicService;
import br.com.tech.challenge.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.created(URI.create(STR."\{PATH}/\{topic.getId()}")).body(topic);
    }

    @PostMapping("/{id}/session/open")
    public ResponseEntity<SessionDTO> open(@PathVariable("id") String id,
                                           @RequestBody SessionDefinitionDTO sessionDefinitionDTO) {
        SessionDefinition sessionDefinition = new SessionDefinition(
                topicService.findById(id),
                sessionDefinitionDTO.timeToVote()
        );
        SessionDTO session = sessionMapper.toDTO(sessionService.create(sessionDefinition));
        return ResponseEntity.created(URI.create(STR."\{PATH}/\{session.getId()}")).body(session);
    }

    @PostMapping("{id}/session/vote")
    public ResponseEntity<Void> vote(@PathVariable("id") String id, @RequestBody VoteDTO voteDTO) {
        Session session = sessionService.findByTopicId(id);

        VoteDetail voteDetail = new VoteDetail(
                session,
                new AssociateDetail(voteDTO.getAssociate().cpf()),
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
