package br.com.tech.challenge.repository;

import br.com.tech.challenge.model.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, UUID> {

    Optional<Associate> findByCpf(String cpf);

}