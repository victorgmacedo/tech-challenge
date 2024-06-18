package br.com.tech.challenge.model;

import br.com.tech.challenge.definitions.Result;
import br.com.tech.challenge.definitions.VoteOptions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "session")
    private List<ResultDetail> details;

    public boolean isOpenToVote() {
        return finishAt.isAfter(LocalDateTime.now());
    }

    public long timeToClose() {
        return finishAt.toEpochSecond(ZoneOffset.UTC) - startedAt.toEpochSecond(ZoneOffset.UTC);
    }
    public Result result() {
        var totalYes = this.details.stream().filter(vote -> VoteOptions.YES.equals(vote.getVote())).map(ResultDetail::getTotalVotes).reduce(0L, Long::sum);
        var totalNo = this.details.stream().filter(vote -> VoteOptions.NO.equals(vote.getVote())).map(ResultDetail::getTotalVotes).reduce(0L, Long::sum);
        if(totalYes.equals(totalNo)) {
            return Result.DRAW;
        } else if(totalYes > totalNo) {
            return Result.YES;
        } else {
            return Result.NO;
        }
    }
}
