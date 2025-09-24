package ao.com.mcali.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
@Schema(name = "Dados de usuário", description = "Informações de cadastro de usuário")
public record UserDTO(

        @NotNull
        @Min(1000)
        @JsonProperty
        @Schema(minLength = 1000, example = "1111", description = "Codigo de usuário", nullable = false)
        Long codigo,

        @NotNull
        @NotBlank
        @JsonProperty
        @Size(min = 5, max = 20)
        @Schema(minLength = 5, maxLength = 20, example = "Carlos", description = "Primeiro nome do usuário", nullable = false)
        String nome,

        @NotNull
        @NotBlank
        @JsonProperty
        @Size(min = 5, max = 20)
        @Schema(minLength = 5, maxLength = 20, example = "Matoca", description = "Sobrenome do usuário", nullable = false)
        String sobrenome,

        @NotNull
        @NotBlank
        @JsonProperty
        @Size(min = 10)
        @Email(regexp = ".+@.+\\..+", message = "Email inválido")
        @Schema(minLength = 10, example = "example@example.example", description = "Email do usuário", nullable = false)
        String email,

        @NotNull
        @NotBlank
        @JsonProperty
        @Size(min = 14, max = 14)
        @Pattern(regexp = "^\\d{9}[A-Z]{2}\\d{3}$", message = "Numero de BI inválido")
        @Schema(minLength = 12, maxLength = 12, example = "001569842MO521", description = "Número de bilhete de identidade angolano", nullable = false)
        String numBI
) {
}
