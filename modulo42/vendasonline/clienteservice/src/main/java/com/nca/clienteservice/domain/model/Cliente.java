package com.nca.clienteservice.domain.model;

import com.nca.clienteservice.enums.StatusCliente;
import com.nca.clienteservice.enums.StatusRegistro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "cliente")
@Getter
@Setter
@Builder
@Schema(name = "Cliente", description = "Cliente")
public class Cliente {

    @Transient
    public static final String SEQUENCE_NAME = "cliente_sequence";

    @Id
    @Schema(description = "Identificador único")
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "nome", minLength = 1, maxLength = 50, nullable = false)
    private String nome;

    @NotNull
    @Indexed(unique = true, background = true)
    @Schema(description = "Cpf", nullable = false)
    private String cpf;

    @NotNull
    @Schema(description = "Telefone", nullable = false)
    private String telefone;

    @NotNull
    @Size(min = 1, max = 50)
    @Indexed(unique = true, background = true)
    @Schema(description = "Email", minLength = 1, maxLength = 50, nullable = false)
    @Pattern(regexp = ".+@.+\\..+", message = "Email inválido")
    private String email;

    @NotNull
    @Schema(description = "Endereço")
    private Endereco endereco;

    @Schema(description = "Status Cliente")
    @Field(name = "status")
    private StatusCliente status;

    @Schema(description = "Status Registro")
    @Field(name = "status_registro")
    private StatusRegistro statusRegistro;

    public Cliente() {
        this.status = StatusCliente.ATIVO;
        this.statusRegistro = StatusRegistro.ATIVO;
    }

    public Cliente(String id, String nome, String cpf, String telefone, String email,
                   Endereco endereco, StatusCliente status, StatusRegistro statusRegistro) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.status = status;
        this.statusRegistro = statusRegistro;
    }
}
