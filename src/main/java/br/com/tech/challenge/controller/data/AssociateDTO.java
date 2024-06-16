package br.com.tech.challenge.controller.data;

import jakarta.validation.constraints.Size;

public record AssociateDTO(
        @Size(min = 11, max = 11, message = "Document must have only numbers") String cpf
) {}
