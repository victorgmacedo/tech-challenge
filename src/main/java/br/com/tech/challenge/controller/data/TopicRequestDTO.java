package br.com.tech.challenge.controller.data;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopicRequestDTO {
    private @Max(value = 100) String title;
    private @Max(value = 500) String description;
}
