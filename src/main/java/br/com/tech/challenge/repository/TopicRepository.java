package br.com.tech.challenge.repository;

import br.com.tech.challenge.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface TopicRepository extends JpaRepository<Topic, UUID> {

}