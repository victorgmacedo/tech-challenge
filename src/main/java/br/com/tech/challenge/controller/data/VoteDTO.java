package br.com.tech.challenge.controller.data;

import br.com.tech.challenge.definitions.VoteOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteDTO {
    VoteOptions vote;
    AssociateDTO associate;
}