package com.banco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {
    private Long id;
    private int numero;
    private double saldo;
    private String tipo;
    private String nomeCliente;
    private double limite;
    private double taxaJuros;
    private double limiteEstudantil;
}
