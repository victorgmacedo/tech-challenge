package br.com.tech.challenge.cpf;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "rest-validator", url = "${rest-validator.url}", fallback = CPFValidatorFallback.class)
public interface CPFValidator {

    @RequestMapping(method = RequestMethod.GET, value = "/cpf/{cpf}", consumes = "application/json")
    ResultCPFValidator test(@PathVariable("cpf") String cpf);

}

