package com.nca.produtoservice.domain.model;

import com.nca.produtoservice.enums.StatusRegistro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "produto")
@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "Produto", description = "Produto")
public class Produto {
    @Id
    @Schema(description = "Identificador único")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "codigo", minLength = 1, maxLength = 50, nullable = false)
    private String codigo;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "nome", minLength = 1, maxLength = 50, nullable = false)
    private String nome;

    @NotNull
    @Size(min = 1, max = 100)
    @Schema(description = "descricao", minLength = 1, maxLength = 100, nullable = false)
    private String descricao;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "descricao", minLength = 1, maxLength = 50, nullable = false)
    private String fabricante;

    @NotNull
    @Schema(description = "valor", nullable = false)
    private BigDecimal valor;

    @NotNull
    @Schema(description = "Status Registro")
    private StatusRegistro statusRegistro;
}
