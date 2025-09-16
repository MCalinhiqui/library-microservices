package ao.com.mcali.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
@Schema(name = "Book", description = "representa um livro a ser cadastrado")
public record BookDTO(
        @NotNull
        @Positive
        @Min(1000)
        @JsonProperty
        @Schema(description = "Codigo do livro", example = "1000", nullable = false, minimum = "1000")
        Long codigo,

        @NotNull
        @NotBlank
        @Size(min = 5, max = 100)
        @JsonProperty
        @Schema(description = "Título do livro", minLength = 5, maxLength = 100, example = "Romeu e Julieta", nullable = false)
        String titulo,

        @NotNull
        @Min(1990)
        @Max(2025)
        @JsonProperty()
        @Schema(description = "Ano de lançamento do livro", minimum = "1990", maximum = "2025", example = "1995", nullable = false)
        Integer anoDePublicacao,

        @NotNull
        @NotBlank
        @JsonProperty
        @Size(min = 5, max = 50)
        @Schema(description = "Nome do autor do livro", minLength = 5, maxLength = 50, example = "Jorge P. Junior", nullable = false)
        String autor
){}
