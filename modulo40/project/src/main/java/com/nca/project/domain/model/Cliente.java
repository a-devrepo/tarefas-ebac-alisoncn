package com.nca.project.domain.model;

import com.nca.project.enums.StatusCliente;
import com.nca.project.enums.StatusRegistro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "sq_cliente", allocationSize = 1)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "email", nullable = false)
    private String email;

    @Embedded
    private Endereco endereco;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCliente status = StatusCliente.ATIVO;

    @Column(name="status_registro",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusRegistro statusRegistro = StatusRegistro.ATIVO;
}
