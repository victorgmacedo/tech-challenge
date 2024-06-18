package br.com.tech.challenge.service;


import br.com.tech.challenge.repository.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ResultService {
    private final ResultRepository resultRepository;
    public void buildResultDetailForSessionId(UUID sessionId) {
        resultRepository.createResult(sessionId);
    }

}
