package ao.com.mcali.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
@Schema(name = "Atalização de usuário", description = "Informações dos usuário cuja modificação é permitida")
public record UserUpdatedDTO(

        @JsonProperty
        @Size(min = 5, max = 20)
        @Schema(minLength = 5, maxLength = 20, example = "Carlos", description = "Primeiro nome do usuário", nullable = false)
        String nome,

        @JsonProperty
        @Size(min = 5, max = 20)
        @Schema(minLength = 5, maxLength = 20, example = "Matoca", description = "Sobrenome do usuário", nullable = false)
        String sobrenome,

        @JsonProperty
        @Size(min = 10)
        @Email(regexp = ".+@.+\\..+", message = "Email inválido")
        @Schema(minLength = 10, example = "example@example.example", description = "Email do usuário", nullable = false)
        String email
) {
}
