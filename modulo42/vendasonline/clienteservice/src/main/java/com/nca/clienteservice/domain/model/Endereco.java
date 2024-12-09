package com.nca.clienteservice.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco {
    @NotNull
    @Size(max = 100)
    private String logradouro;

    @NotNull
    private String numero;

    @NotNull
    private String cidade;

    @NotNull
    private String estado;
}
