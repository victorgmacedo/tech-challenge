package br.com.tech.challenge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Associate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String cpf;

    public Associate(String cpf) {
        this.cpf = cpf;
    }
}
