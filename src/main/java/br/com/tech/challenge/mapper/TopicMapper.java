package br.com.tech.challenge.mapper;


import br.com.tech.challenge.controller.data.TopicRequestDTO;
import br.com.tech.challenge.controller.data.TopicResponseDTO;
import br.com.tech.challenge.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TopicMapper {

    @Mapping(target = "id", ignore = true)
    Topic toModel(TopicRequestDTO topicDTO);

    @Mapping(target = "session", ignore = true)
    TopicResponseDTO toDTO(Topic topic);

}