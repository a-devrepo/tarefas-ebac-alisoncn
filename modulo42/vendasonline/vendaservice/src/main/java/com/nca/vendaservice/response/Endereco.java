package com.nca.vendaservice.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco {

    private String logradouro;

    private String numero;

    private String cidade;

    private String estado;
}
