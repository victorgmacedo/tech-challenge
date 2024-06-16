package br.com.tech.challenge.domain;

public record AssociateDetail(String document) {
    public String cpf() {
        return document.replaceAll("\\D+", "");
    }
}
