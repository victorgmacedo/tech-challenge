package br.com.tech.challenge.mapper;


import br.com.tech.challenge.controller.data.VoteDTO;
import br.com.tech.challenge.model.Vote;
import org.mapstruct.Mapper;

@Mapper
interface VoteMapper {

    VoteDTO toDTO(Vote vote);

}