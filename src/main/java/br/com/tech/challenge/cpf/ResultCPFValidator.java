package br.com.tech.challenge.cpf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultCPFValidator {

    private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
    private String status;
    public static ResultCPFValidator ableToVote() {
        return new ResultCPFValidator(ABLE_TO_VOTE);
    }
    public Boolean isValid() {
        return ABLE_TO_VOTE.equals(status);
    }
}