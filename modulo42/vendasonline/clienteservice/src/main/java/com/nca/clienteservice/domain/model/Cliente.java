package com.nca.clienteservice.domain.model;

import com.nca.clienteservice.enums.StatusCliente;
import com.nca.clienteservice.enums.StatusRegistro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cliente")
@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "Cliente", description = "Cliente")
public class Cliente {
    @Id
    @Schema(description = "Identificador único")
    private Long id;

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

    @NotNull
    @Schema(description = "Status Cliente")
    private StatusCliente status;

    @NotNull
    @Schema(description = "Status Registro")
    private StatusRegistro statusRegistro;

    public Cliente() {
        this.status = StatusCliente.ATIVO;
        this.statusRegistro = StatusRegistro.ATIVO;
    }
}
