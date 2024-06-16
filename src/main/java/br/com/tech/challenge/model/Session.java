package br.com.tech.challenge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    private LocalDateTime finishAt;
    private LocalDateTime startedAt;

    @OneToMany(mappedBy = "session")
    private Set<Vote> votes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id")
    private ResultDetail resultDetail;

    public boolean isOpenToVote() {
        return finishAt.isAfter(LocalDateTime.now());
    }
}
