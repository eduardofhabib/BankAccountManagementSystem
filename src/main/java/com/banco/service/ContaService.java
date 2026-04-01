package com.banco.service;

import com.banco.model.Conta;
import com.banco.model.ContaEspecial;
import com.banco.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio das contas bancárias.
 */
@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    /**
     * Retorna todas as contas cadastradas.
     * @return Lista de contas.
     */
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    /**
     * Busca uma conta pelo seu ID.
     * @param id Identificador único da conta.
     * @return A conta encontrada.
     * @throws RuntimeException se a conta não for encontrada.
     */
    public Conta findById(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    /**
     * Realiza um débito em uma conta.
     * @param id ID da conta.
     * @param valor Valor a ser debitado.
     */
    @Transactional
    public void debitar(Long id, double valor) {
        Conta conta = findById(id);
        if (valor > 0 && valor <= conta.getSaldo()) {
            conta.setSaldo(conta.getSaldo() - valor);
            contaRepository.save(conta);
        } else {
            throw new RuntimeException("Valor de débito inválido ou saldo insuficiente.");
        }
    }

    /**
     * Realiza um crédito em uma conta.
     * @param id ID da conta.
     * @param valor Valor a ser creditado.
     */
    @Transactional
    public void creditar(Long id, double valor) {
        Conta conta = findById(id);
        if (valor > 0) {
            conta.setSaldo(conta.getSaldo() + valor);
            contaRepository.save(conta);
        } else {
            throw new RuntimeException("Valor de crédito inválido.");
        }
    }

    /**
     * Realiza uma transferência entre contas.
     * @param idOrigem ID da conta de origem.
     * @param idDestino ID da conta de destino.
     * @param valor Valor a ser transferido.
     */
    @Transactional
    public void transferir(Long idOrigem, Long idDestino, double valor) {
        Conta origem = findById(idOrigem);
        Conta destino = findById(idDestino);

        if (valor > 0 && valor <= origem.getSaldo()) {
            origem.setSaldo(origem.getSaldo() - valor);
            destino.setSaldo(destino.getSaldo() + valor);
            contaRepository.save(origem);
            contaRepository.save(destino);
        } else {
            throw new RuntimeException("Transferência inválida: saldo insuficiente ou valor negativo.");
        }
    }

    /**
     * Aplica juros em contas do tipo Especial.
     * @param id ID da conta.
     */
    @Transactional
    public void aplicarJuros(Long id) {
        Conta conta = findById(id);
        if (conta instanceof ContaEspecial) {
            ContaEspecial ce = (ContaEspecial) conta;
            double juros = ce.getSaldo() * (ce.getTaxaJuros() / 100);
            ce.setSaldo(ce.getSaldo() + juros);
            contaRepository.save(ce);
        } else {
            throw new RuntimeException("A conta não é do tipo Especial para aplicar juros.");
        }
    }
}
