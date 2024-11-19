package com.nca.project;

import com.nca.project.domain.model.Cliente;
import com.nca.project.domain.model.Endereco;
import com.nca.project.domain.repositories.IClienteRepository;
import com.nca.project.enums.StatusCliente;
import com.nca.project.enums.StatusRegistro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.nca.project.domain.repositories")
@EntityScan("com.nca.project.*")
@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ProjectApplication.class);

    @Autowired
    private IClienteRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting...");
        Cliente cliente = createCliente();
        repository.save(cliente);
    }

    private Cliente createCliente() {
        Endereco endereco = createEndereco();
        return Cliente.builder()
                .nome("Francisco")
                .cpf("111.111.111-11")
                .email("francisco@email.com")
                .endereco(endereco)
                .telefone("(21)98320913")
                .status(StatusCliente.ATIVO)
                .statusRegistro(StatusRegistro.ATIVO)
                .build();
    }

    private Endereco createEndereco() {
        return Endereco.builder()
                .logradouro("Rua A")
                .numero("1")
                .cidade("Rio de Janeiro")
                .estado("RJ")
                .build();
    }
}
