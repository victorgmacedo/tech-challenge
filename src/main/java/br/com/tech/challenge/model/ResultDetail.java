package br.com.tech.challenge.model;

import br.com.tech.challenge.definitions.VoteOptions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.jdbc.JsonJdbcType;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "result_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Long totalVotes;

    @Enumerated(EnumType.STRING)
    private VoteOptions vote;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    public ResultDetail(Long totalVotes, VoteOptions vote) {
        this.totalVotes = totalVotes;
        this.vote = vote;
    }
}

