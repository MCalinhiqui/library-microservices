package ao.com.mcali.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "Atualização de Livro", description = "Campos usados para atualização parcial dos dados de um livro")
public record BookUpdatedDTO(
    @JsonProperty
    @Size(min = 5, max = 100)
    @Schema(description = "Título do livro", minLength = 5, maxLength = 100, example = "Romeu e Julieta", nullable = true)
    String titulo,

    @JsonProperty
    @Size(min = 5, max = 50)
    @Schema(description = "Nome do autor do livro", minLength = 5, maxLength = 50, example = "Jorge P. Junior", nullable = true)
    String autor,

    @Min(1990)
    @Max(2025)
    @JsonProperty()
    @Schema(description = "Ano de lançamento do livro", minimum = "1990", maximum = "2025", example = "1995", nullable = true)
    Integer anoDePublicacao
){}
