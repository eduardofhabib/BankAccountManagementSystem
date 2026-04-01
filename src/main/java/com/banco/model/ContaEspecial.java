package com.banco.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ContaEspecial extends Conta {
    private double limite;
    private double taxaJuros;

    public ContaEspecial(int numero, double saldo, double limite, double taxaJuros) {
        super(numero, saldo);
        this.limite = limite;
        this.taxaJuros = taxaJuros;
    }
}
