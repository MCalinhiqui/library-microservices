package ao.com.mcali.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "Loan", description = "Dados de cadastro do emprestimo")
public record LoanDTO(

        @NotNull
        @Min(1000)
        @JsonProperty
        @Schema(description = "Codigo do emprestimo", minimum = "1000", example = "1111")
        Long codigo,

        @NotNull
        @Min(1000)
        @JsonProperty
        @Schema(description = "Codigo do usuário", minimum = "1000", example = "1111")
        Long codigoUsuario,

        @NotNull
        @Min(1000)
        @JsonProperty
        @Schema(description = "Codigo do Livro", minimum = "1000", example = "1000")
        Long codigoLivro)
{
}
