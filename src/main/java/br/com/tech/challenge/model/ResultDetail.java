package br.com.tech.challenge.model;

import br.com.ntconsult.challenge.definitions.VoteOptions;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "result_detail")
@Data
public class ResultDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Long totalVotes;

    @Enumerated(EnumType.STRING)
    private VoteOptions result;

    //@Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<VoteOptions, Long> voteDetail;

}

