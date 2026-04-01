package com.banco.service;

import com.banco.model.Cliente;
import com.banco.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testFindById() {
        Cliente cliente = new Cliente(1L, 1, "Eduardo", "123.456.789-00");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.findById(1L);

        assertEquals("Eduardo", result.getNome());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        Cliente cliente = new Cliente(null, 1, "Eduardo", "123.456.789-00");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.save(cliente);

        assertEquals("Eduardo", result.getNome());
        verify(clienteRepository, times(1)).save(cliente);
    }
}
