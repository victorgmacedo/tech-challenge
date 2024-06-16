package br.com.ntconsult.challenge.proxy.cpf

import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "\${rest-cpf-validator.name}", url = "\${rest-cpf-validator.url}", fallback = CPFValidatorFallback::class)
interface CPFValidator {

    @RequestMapping(method = [RequestMethod.GET], value = ["/cpf/{cpf}"], consumes = ["application/json"])
    fun cpfIsValid(@PathVariable("cpf") cpf: String) : ResultCPFValidator
}

@Component
internal class CPFValidatorFallback : CPFValidator {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun cpfIsValid(cpf: String) : ResultCPFValidator {
        logger.warn("CPF is not verified because validator is not responding {CPF} = $cpf")
        return ResultCPFValidator.default()
    }

}