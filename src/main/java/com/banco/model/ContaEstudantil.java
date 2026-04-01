package com.banco.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ContaEstudantil extends Conta {
    private double limiteEstudantil;

    public ContaEstudantil(int numero, double saldo, double limiteEstudantil) {
        super(numero, saldo);
        this.limiteEstudantil = limiteEstudantil;
    }
}
