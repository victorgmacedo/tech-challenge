package br.com.tech.challenge.controller.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionDTO {
    private String id;
    private LocalDateTime finishAt;
    private Boolean isOpenToVote;
}
