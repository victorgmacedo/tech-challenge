package br.com.tech.challenge.service;


import br.com.tech.challenge.cpf.CPFValidator;
import br.com.tech.challenge.exceptions.InvalidDocumentException;
import br.com.tech.challenge.model.Associate;
import br.com.tech.challenge.repository.AssociateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssociateService {
    private final AssociateRepository associateRepository;
    private final CPFValidator validator;

    public Associate findAssociateByCpfOrCreateNewAssociate(String cpf) {
        return associateRepository.findByCpf(cpf).orElseGet(() -> createNewAssociate(new Associate(cpf)));
    }

    public Associate createNewAssociate(Associate associate) {
        if (!cpfIsValid(associate.getCpf())) {
            throw new InvalidDocumentException(STR."Document {CPF} = \{associate.getCpf()} is not valid");
        }
        return associateRepository.save(associate);
    }

    public boolean cpfIsValid(String cpf) {
        return validator.test(cpf).isValid();
    }
}
