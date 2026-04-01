package com.banco;

import com.banco.model.Cliente;
import com.banco.model.Conta;
import com.banco.model.ContaEspecial;
import com.banco.model.ContaEstudantil;
import com.banco.repository.ClienteRepository;
import com.banco.repository.ContaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SistemaBancarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaBancarioApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClienteRepository clienteRepository, ContaRepository contaRepository) {
		return args -> {
			Cliente c1 = new Cliente(null, 1, "Eduardo", "123.456.789-00");
			Cliente c2 = new Cliente(null, 2, "Maria", "987.654.321-11");
			clienteRepository.save(c1);
			clienteRepository.save(c2);

			Conta conta1 = new ContaEspecial(101, 1000.0, 500.0, 2.5);
			conta1.setCliente(c1);
			contaRepository.save(conta1);

			Conta conta2 = new ContaEstudantil(202, 200.0, 1000.0);
			conta2.setCliente(c2);
			contaRepository.save(conta2);

            Conta conta3 = new Conta(303, 1500.0);
            conta3.setCliente(c1);
            contaRepository.save(conta3);
		};
	}
}
