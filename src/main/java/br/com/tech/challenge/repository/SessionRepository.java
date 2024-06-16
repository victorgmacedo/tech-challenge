package br.com.tech.challenge.repository;

import br.com.tech.challenge.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    @Query("select count(1) > 0 from Session where topic.id = :topicId")
    Boolean existSessionForTopic(
            @Param("topicId") UUID topicId
    );

    Optional<Session> findByTopicId(UUID id);
}