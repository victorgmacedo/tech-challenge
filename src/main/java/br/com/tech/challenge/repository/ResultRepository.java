package br.com.tech.challenge.repository;

import br.com.ntconsult.challenge.definitions.VoteOptions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class ResultRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Long totalVotes(UUID sessionId) {
        try {
            return entityManager.createQuery("""
                                              select count(a) 
                                              from Vote a 
                                             where a.session.id = :id
                                        """.trim(), Long.class)
                    .setParameter("id", sessionId)
                    .getSingleResult();
        } catch (Exception e) {
            //TODO analisar
            return 0L;
        }
    }

    //TODO refact
    Map<VoteOptions, Long> voteDetail(UUID sessionId)  {
        return null;
    }

}