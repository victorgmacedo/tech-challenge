package br.com.tech.challenge.repository;

import br.com.tech.challenge.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    @Query("select count(1) > 0 from Vote where session.id = :sessionId and associate.cpf = :associateDocument")
    Boolean existsVoteFromAssociateAndSession(
            @Param("sessionId") UUID sessionId,
            @Param("associateDocument") String associateDocument
    );

}