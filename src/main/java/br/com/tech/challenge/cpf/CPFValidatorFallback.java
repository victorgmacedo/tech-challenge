package br.com.tech.challenge.cpf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CPFValidatorFallback implements CPFValidator {

    @Override
    public ResultCPFValidator test(String cpf) {
        log.warn(STR."CPF is not verified because validator is not responding CPF = \{cpf}");
        return ResultCPFValidator.ableToVote();
    }
}