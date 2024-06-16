package br.com.ntconsult.challenge.service

import br.com.ntconsult.challenge.exception.InvalidDocumentException
import br.com.ntconsult.challenge.model.Associate
import br.com.ntconsult.challenge.proxy.cpf.CPFValidator
import br.com.ntconsult.challenge.repository.AssociateRepository
import org.springframework.stereotype.Service

@Service
class AssociateService(private val associateRepository: AssociateRepository,
                       private val validator: CPFValidator) {

    fun findAssociateByCpfOrCreateNewAssociate(cpf: String): Associate {
        return associateRepository.findByCpf(cpf).orElseGet {
            createNewAssociate(Associate(cpf = cpf))
        }
    }

    fun createNewAssociate(associate: Associate): Associate {
        if(!cpfIsValid(associate.cpf)) {
            throw InvalidDocumentException("Document {CPF} = ${associate.cpf} is not valid")
        }
        return associateRepository.save(associate)
    }

    fun cpfIsValid(cpf: String): Boolean {
        return validator.cpfIsValid(cpf).isValid()
    }

}
