package br.com.tech.challenge.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Slf4j
public class ResultRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createResult(UUID sessionId) {
        try {
             entityManager.createNativeQuery("""
                             insert into result_detail(total_votes, vote, session_id)
                             select
                             	count(1),
                             	a.vote,
                             	a.session_id
                             from
                             	session_vote a
                             where
                             	a.session_id = :sessionId
                             group by
                             	a.vote,
                             	a.session_id""")
                    .setParameter("sessionId", sessionId)
                    .executeUpdate();
        } catch (Exception e) {
            log.info("Error creating result for session {}", sessionId, e);
        }
    }

}