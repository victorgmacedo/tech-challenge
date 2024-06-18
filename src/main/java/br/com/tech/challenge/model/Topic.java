package br.com.tech.challenge.model;

import br.com.tech.challenge.definitions.Result;
import br.com.tech.challenge.definitions.VoteOptions;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Result result;
}
