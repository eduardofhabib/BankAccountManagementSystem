package com.banco.service;

import com.banco.model.Cliente;
import com.banco.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pela gestão de clientes.
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Retorna todos os clientes.
     * @return Lista de clientes.
     */
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    /**
     * Salva um novo cliente.
     * @param cliente Objeto cliente.
     * @return Cliente salvo.
     */
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Busca um cliente pelo ID.
     * @param id ID do cliente.
     * @return Cliente encontrado.
     * @throws RuntimeException se não encontrado.
     */
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
}
