package com.banco.controller;

import com.banco.dto.ContaDTO;
import com.banco.model.Conta;
import com.banco.model.ContaEspecial;
import com.banco.model.ContaEstudantil;
import com.banco.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @GetMapping
    public List<ContaDTO> getAllContas() {
        return contaService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getContaById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDTO(contaService.findById(id)));
    }

    @PostMapping("/{id}/debito")
    public ResponseEntity<Void> debitar(@PathVariable Long id, @RequestParam double valor) {
        contaService.debitar(id, valor);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/credito")
    public ResponseEntity<Void> creditar(@PathVariable Long id, @RequestParam double valor) {
        contaService.creditar(id, valor);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transferir")
    public ResponseEntity<Void> transferir(@RequestParam Long idOrigem, @RequestParam Long idDestino, @RequestParam double valor) {
        contaService.transferir(idOrigem, idDestino, valor);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/aplicarJuros")
    public ResponseEntity<Void> aplicarJuros(@PathVariable Long id) {
        contaService.aplicarJuros(id);
        return ResponseEntity.ok().build();
    }

    private ContaDTO convertToDTO(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId());
        dto.setNumero(conta.getNumero());
        dto.setSaldo(conta.getSaldo());
        dto.setNomeCliente(conta.getCliente() != null ? conta.getCliente().getNome() : "Desconhecido");

        if (conta instanceof ContaEspecial ce) {
            dto.setTipo("ESPECIAL");
            dto.setLimite(ce.getLimite());
            dto.setTaxaJuros(ce.getTaxaJuros());
        } else if (conta instanceof ContaEstudantil ces) {
            dto.setTipo("ESTUDANTIL");
            dto.setLimiteEstudantil(ces.getLimiteEstudantil());
        } else {
            dto.setTipo("COMUM");
        }
        return dto;
    }
}
