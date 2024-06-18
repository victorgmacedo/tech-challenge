package br.com.tech.challenge.controller.data;

import br.com.tech.challenge.definitions.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicResponseDTO {
    private String id;
    private String title;
    private String description;
    private Result result;
    private SessionDTO session;
}
