package br.com.tech.challenge.mapper;


import br.com.tech.challenge.controller.data.SessionDTO;
import br.com.tech.challenge.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SessionMapper {

    @Mapping(target = "isOpenToVote", expression = "java(session.isOpenToVote())")
    SessionDTO toDTO(Session session);

}