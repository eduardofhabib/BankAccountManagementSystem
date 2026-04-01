package com.banco.service;

import com.banco.model.Conta;
import com.banco.model.ContaEspecial;
import com.banco.model.ContaEstudantil;
import com.banco.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    private Conta contaComum;
    private ContaEspecial contaEspecial;

    @BeforeEach
    void setUp() {
        contaComum = new Conta(1, 1000.0);
        contaComum.setId(1L);

        contaEspecial = new ContaEspecial(2, 500.0, 1000.0, 10.0);
        contaEspecial.setId(2L);
    }

    @Test
    void testDebitarComSucesso() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaComum));
        
        contaService.debitar(1L, 200.0);
        
        assertEquals(800.0, contaComum.getSaldo());
        verify(contaRepository, times(1)).save(contaComum);
    }

    @Test
    void testDebitarSaldoInsuficiente() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaComum));
        
        assertThrows(RuntimeException.class, () -> contaService.debitar(1L, 1200.0));
    }

    @Test
    void testCreditarComSucesso() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaComum));
        
        contaService.creditar(1L, 300.0);
        
        assertEquals(1300.0, contaComum.getSaldo());
        verify(contaRepository, times(1)).save(contaComum);
    }

    @Test
    void testTransferirComSucesso() {
        Conta destino = new Conta(3, 100.0);
        destino.setId(3L);

        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaComum));
        when(contaRepository.findById(3L)).thenReturn(Optional.of(destino));

        contaService.transferir(1L, 3L, 400.0);

        assertEquals(600.0, contaComum.getSaldo());
        assertEquals(500.0, destino.getSaldo());
        verify(contaRepository, times(1)).save(contaComum);
        verify(contaRepository, times(1)).save(destino);
    }

    @Test
    void testAplicarJurosContaEspecial() {
        when(contaRepository.findById(2L)).thenReturn(Optional.of(contaEspecial));

        contaService.aplicarJuros(2L);

        // 500 + (500 * 0.1) = 550
        assertEquals(550.0, contaEspecial.getSaldo());
        verify(contaRepository, times(1)).save(contaEspecial);
    }

    @Test
    void testAplicarJurosContaNaoEspecial() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaComum));

        assertThrows(RuntimeException.class, () -> contaService.aplicarJuros(1L));
    }
}
