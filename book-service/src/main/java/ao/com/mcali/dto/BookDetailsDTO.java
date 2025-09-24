package ao.com.mcali.dto;

import ao.com.mcali.domain.BookStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

@Builder
@Schema(name = "Book Details", description = "representa os atributos retornados na consulta de um livro")
public record BookDetailsDTO(
        @JsonProperty
        @Schema(description = "Codigo do livro", example = "1000")
        Long codigo,

        @JsonProperty
        @Schema(description = "Título do livro", example = "Romeu e Julieta")
        String titulo,

        @JsonProperty()
        @Schema(description = "Ano de lançamento do livro", example = "1995")
        Integer anoDePublicacao,

        @JsonProperty
        @Schema(description = "Nome do autor do livro", example = "Jorge P. Junior")
        String autor,

        @JsonProperty
        @Enumerated(EnumType.STRING)
        @Schema(description = "Status atual do livro", example = "DISPONIVEL")
        BookStatus status
) {
}
