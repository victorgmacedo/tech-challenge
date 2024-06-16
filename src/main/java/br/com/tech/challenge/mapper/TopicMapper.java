package br.com.tech.challenge.mapper;


import br.com.tech.challenge.controller.data.TopicRequestDTO;
import br.com.tech.challenge.controller.data.TopicResponseDTO;
import br.com.tech.challenge.model.Topic;
import org.mapstruct.Mapper;

@Mapper
public interface TopicMapper {

    Topic toModel(TopicRequestDTO topicDTO);
    TopicResponseDTO toDTO(Topic topic);

}