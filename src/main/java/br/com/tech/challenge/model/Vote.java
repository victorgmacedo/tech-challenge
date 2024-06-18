package br.com.tech.challenge.model;

import br.com.tech.challenge.definitions.VoteOptions;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "session_vote")
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "associate_id", nullable = false)
    private Associate associate;

    @Enumerated(EnumType.STRING)
    private VoteOptions vote;

    public Vote(Session session, Associate associate, VoteOptions vote) {
        this.session = session;
        this.associate = associate;
        this.vote = vote;
    }
}
